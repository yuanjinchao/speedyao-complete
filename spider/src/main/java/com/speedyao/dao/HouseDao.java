package com.speedyao.dao;

import com.alibaba.fastjson.JSONObject;
import com.speedyao.dao.mapper.HouseMapper;
import com.speedyao.dao.model.House;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by speedyao on 2018/10/16.
 */
@Repository
public class HouseDao {
    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    private LinkedBlockingQueue<House> queue = new LinkedBlockingQueue<>(2000);
    Logger logger=LoggerFactory.getLogger("market.mqMsgMonitor");
    @Autowired
    private HouseMapper houseMapper;
    @PostConstruct
    public void init() {
        executorService.execute(() -> {
            while (true){
                try {
                    House house = queue.poll(1000L, TimeUnit.MILLISECONDS);
                    if(house!=null){
                        houseMapper.insert(house);
                    }
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });

    }
    /**
     * 异步保存数据
     *
     * @param house
     */
    public void asyncSave(House house) {
        if (!queue.offer(house)) {
            logger.info( JSONObject.toJSONString(house));
        }
    }
}
