package com.speedyao.bill.controller;

import com.speedyao.bill.dao.ConsumeRecordDao;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by speedyao on 2019/5/22.
 */
@Controller
@RequestMapping("/speedyao/bill/consumeRecord/")
public class ConsumeRecordController  {

    @Autowired
    private ConsumeRecordDao recordDao;

    @RequestMapping
//    @ResponseBody
    @ApiOperation(httpMethod ="POST",value = "获取信用卡消费记录")
    public Object getRecordList(){
        return recordDao.findAll();
    }
}
