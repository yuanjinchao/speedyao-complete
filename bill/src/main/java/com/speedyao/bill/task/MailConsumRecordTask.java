package com.speedyao.bill.task;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by speedyao on 2019/5/16.
 */
public class MailConsumRecordTask {




    @Scheduled(cron="0 0 0/1 * * ?")
    public void task(){

    }
}
