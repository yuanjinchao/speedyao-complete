package com.speedyao.service;

import com.alibaba.fastjson.JSONObject;
import com.speedyao.common.CommonUtils;
import com.speedyao.dao.HouseDao;
import com.speedyao.dao.mapper.HouseMapper;
import com.speedyao.dao.mapper.XiaoquMapper;
import com.speedyao.dao.model.House;
import com.speedyao.dao.model.Xiaoqu;
import com.speedyao.date.DateUtils;
import com.speedyao.spider.lianjia.LianjiaSpider;
import com.speedyao.spider.lianjia.vo.HouseVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/**
 * Created by speedyao on 2018/10/16.
 */
@Service
public class HouseService {

    Logger logger = LoggerFactory.getLogger(HouseService.class);

    public static final int THREAD_SIZE = 2;

    ExecutorService executorService = Executors.newFixedThreadPool(THREAD_SIZE);
    @Autowired
    private HouseDao houseDao;
    @Autowired
    private XiaoquMapper xiaoquMapper;
    @Autowired
    private HouseMapper houseMapper;

    private volatile int restCount = 0;

    /**
     * 每周一12点执行一次
     */
//    @Scheduled(cron = "0 0 12 ? * WEN")
    @Scheduled(initialDelay = 1000*30,fixedDelay=1000*60*60*24*7)
    @Async
    public void getLianjiaData() {
        List<Xiaoqu> xiaoquList = xiaoquMapper.selectAll();
        logger.info("查询小区共:" + xiaoquList.size());
        LinkedBlockingQueue<Xiaoqu> queue = new LinkedBlockingQueue<>(xiaoquList.size());
        xiaoquList.forEach(xiaoqu -> queue.offer(xiaoqu));
        logger.info("小区信息放入queue完成，并启动" + THREAD_SIZE + "个爬虫线程获取数据");
        for (int i = 0; i < THREAD_SIZE; i++) {
            executorService.execute(() -> {
                Xiaoqu xiaoqu;
                while ((xiaoqu = queue.poll()) != null) {
                    try {
                        logger.info("开始查询[" + xiaoqu.getName() + "]");
                        List<HouseVo> list = LianjiaSpider.getLianjiaByContent(xiaoqu.getName());
                        logger.info("[" + xiaoqu.getName() + "]共查询出" + list.size() + "条");
                        for (HouseVo vo : list) {
                            House house = new House();
                            CommonUtils.copyDiffObject(vo, house);
                            house.setDate(DateUtils.currentDateStr());
                            house.setInsertTime(new Date());
                            house.setSchool(xiaoqu.getSchool());
                            house.setEduArea(xiaoqu.getEduArea());
                            if (StringUtils.isNotBlank(vo.getInfo())) {
                                String[] split = vo.getInfo().split("\\|");
                                if (split.length < 3) {
                                    logger.error("[{}]info格式有误：{}", vo.getInfo(), vo.getUrl());
                                } else {
                                    house.setType(split[1]);
                                    house.setArea(split[2]);
                                }
                            }
                            if (StringUtils.isNotBlank(vo.getFollowInfo())) {
                                String[] split = vo.getFollowInfo().split("/");
                                if (split.length != 3) {
                                    logger.error("[{}]followInfo格式有误：{}", vo.getFollowInfo(), vo.getUrl());
                                } else {
                                    house.setFocusCount(Integer.parseInt(split[0].replaceAll("人关注", "").replaceAll(" ", "")));
                                    house.setFollowCount(Integer.parseInt(split[1].replaceAll("共", "").replaceAll("次带看", "").replaceAll(" ", "")));
                                    house.setPubdate(split[2]);
                                }
                            }

                            this.houseDao.asyncSave(house);
                        }
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }
                logger.info("所有小区已查询完成");
            });
        }
    }

    /**
     * 填充房屋基本信息和交易信息
     */
//    @Scheduled(cron = "0 0 13 ? * WEN")
    @Scheduled(initialDelay = 1000*60*30,fixedDelay=1000*60*60*24*7)
    @Async
    public void fillHouseDetail() {
        String date = DateUtils.currentDateStr();
        House record = new House();
        record.setDate(date);
//        record.setId(11600);
        List<House> houses = houseMapper.selectSelective(record);
        logger.info("{}共房屋{}条", date, houses.size());
        restCount = houses.size();
        LinkedBlockingQueue<House> queue = new LinkedBlockingQueue<>(houses.size());
        houses.forEach(house -> queue.offer(house));
        for (int i = 0; i < THREAD_SIZE; i++) {
            executorService.execute(() -> {
                while (!queue.isEmpty()) {
                    House house = queue.poll();
                    try {
                        JSONObject houseDetail = LianjiaSpider.getHouseDetail(house.getUrl());
                        if (houseDetail != null) {
                            JSONObject baseInfo = houseDetail.getJSONObject("baseInfo");
                            if (baseInfo != null) {
                                String age = baseInfo.getString("产权年限");
                                age = age != null ? age.replaceAll(" ", "").replaceAll("年", "") : null;
                                if (age != null && Pattern.matches("\\d+", age)) {
                                    house.setAge(Integer.parseInt(age));
                                }
                            }
                            JSONObject dealInfo = houseDetail.getJSONObject("dealInfo");
                            if (dealInfo != null) {
                                house.setPubdate(dealInfo.getString("挂牌时间"));
                                house.setLimitYear(dealInfo.getString("房屋年限"));
                            }
                            this.houseMapper.updateByPrimaryKeySelective(house);
                            restCount--;
                            if (restCount % 10 == 0) {
                                logger.info("剩余{}条", restCount);
                            }
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
                logger.info("执行完成");
            });
        }

    }
}

