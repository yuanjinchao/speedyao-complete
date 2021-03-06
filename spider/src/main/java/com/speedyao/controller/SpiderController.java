package com.speedyao.controller;

import com.alibaba.fastjson.JSONObject;
import com.speedyao.dao.mapper.XiaoquMapper;
import com.speedyao.dao.model.Xiaoqu;
import com.speedyao.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by speedyao on 2018/10/15.
 */
@Controller
@RequestMapping(value = "/spider")
public class SpiderController {


    @Autowired
    private XiaoquMapper xiaoquMapper;
    @Autowired
    private HouseService houseService;

    @RequestMapping(value = "/xiaoqu/add",produces = {"application/json;charset=UTF-8","text/plain;charset=UTF-8"})
    @ResponseBody
    public Object addXiaoqu(@RequestBody Xiaoqu vo){
        JSONObject json=new JSONObject();
        xiaoquMapper.insert(vo);
        json.put("msg","添加成功");
        return json;
    }
    @RequestMapping(value = "/house/list/start",method = RequestMethod.GET)
    @ResponseBody
    public Object startHouseList(){
        JSONObject json=new JSONObject();
        houseService.getLianjiaData();
        json.put("msg","开始爬取房产列表信息");
        return json;
    }
    @RequestMapping(value = "/house/detail/start",method = RequestMethod.GET)
    @ResponseBody
    public Object startHouseDetail(){
        JSONObject json=new JSONObject();
        houseService.fillHouseDetail();
        json.put("msg","开始爬取房产详细信息");
        return json;
    }
}
