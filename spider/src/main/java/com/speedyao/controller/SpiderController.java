package com.speedyao.controller;

import com.alibaba.fastjson.JSONObject;
import com.speedyao.dao.mapper.XiaoquMapper;
import com.speedyao.dao.model.Xiaoqu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by speedyao on 2018/10/15.
 */
@Controller
@RequestMapping(value = "/spider")
public class SpiderController {


    @Autowired
    private XiaoquMapper xiaoquMapper;

    @RequestMapping(value = "/xiaoqu/add",produces = {"application/json;charset=UTF-8","text/plain;charset=UTF-8"})
    @ResponseBody
    public Object addXiaoqu(@RequestBody Xiaoqu vo){
        JSONObject json=new JSONObject();
        xiaoquMapper.insert(vo);
        json.put("msg","添加成功");
        return json;
    }
}
