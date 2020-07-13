package com.speedyao.spring.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig1 {
    @Bean
    public Service3 service3(){
        Service3 service3=new Service3();
        service3.setService1(service1());
        service3.setService2(service2());
        return service3;
    }
    @Bean
    public Service1 service1(){
        return new Service1();
    }
    @Bean
    public Service2 service2(){
        return new Service2();
    }
}
