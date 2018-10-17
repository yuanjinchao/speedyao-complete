package com.speedyao.service;

import com.speedyao.common.CommonUtils;
import com.speedyao.dao.HouseDao;
import com.speedyao.dao.mapper.XiaoquMapper;
import com.speedyao.dao.model.House;
import com.speedyao.dao.model.Xiaoqu;
import com.speedyao.date.DateUtils;
import com.speedyao.spider.lianjia.LianjiaSpider;
import com.speedyao.spider.lianjia.vo.HouseVo;
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

    @Scheduled(cron = "0 0 12 * * ? *")
    @Async
    public void getLianjiaData() {
        List<Xiaoqu> xiaoquList = xiaoquMapper.selectAll();
        logger.info("查询小区共:"+xiaoquList.size());
        LinkedBlockingQueue<Xiaoqu> queue = new LinkedBlockingQueue<>(xiaoquList.size());
        xiaoquList.forEach(xiaoqu -> queue.offer(xiaoqu));
        logger.info("小区信息放入queue完成，并启动"+THREAD_SIZE+"个爬虫线程获取数据");
        for (int i = 0; i < THREAD_SIZE; i++) {
            executorService.execute(() -> {
                Xiaoqu xiaoqu;
                while ((xiaoqu = queue.poll()) != null) {
                    try {
                        logger.info("开始查询[" + xiaoqu.getName() + "]");
                        List<HouseVo> list = LianjiaSpider.getLianjiaInfo(xiaoqu.getName());
                        logger.info("[" + xiaoqu.getName() + "]共查询出" + list.size() + "条");
                        for (HouseVo vo : list) {
                            House house = new House();
                            CommonUtils.copyDiffObject(vo, house);
                            house.setDate(DateUtils.currentDateStr());
                            house.setInsertTime(new Date());
                            house.setSchool(xiaoqu.getSchool());
                            house.setEduArea(xiaoqu.getEduArea());
                            this.houseDao.asyncSave(house);
                        }
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }
            });
        }
    }
}

