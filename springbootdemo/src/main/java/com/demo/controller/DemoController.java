package com.demo.controller;

import com.alibaba.fastjson.JSON;
import com.demo.dao.UserDao;
import com.demo.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by speedyao on 2019/3/28.
 */
@RestController
@RequestMapping("/v1/demo/")
public class DemoController {
    @Autowired
    UserDao userDao;
    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public Object test1(@PathVariable long id){
        Optional<User> optional = this.userDao.findById(id);
        if(optional.isPresent()){
            return JSON.toJSONString(optional.get());

        }
        return "null";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(@RequestBody User user){
        userDao.save(user);
    }
}
