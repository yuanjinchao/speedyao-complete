package com.speedyao.spring.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class Service2 {
    @Autowired//优先按照类型找,再按名称
    private List<IService> serviceList;
    @Autowired
    private Map<String,IService> serviceMap;
    @Autowired
    @Qualifier("service0")
    private IService service;

    @Resource//优先按照名称找
    private IService service1;


    @Override
    public String toString() {
        return "Service2{" +
                "serviceList=" + serviceList +
                ", serviceMap=" + serviceMap +
                ", service=" + service +
                ", service1=" + service1 +
                '}';
    }
}
