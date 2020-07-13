package com.speedyao.spring;

import com.speedyao.spring.autowired.MainConfig0;
import com.speedyao.spring.bean.MainConfig1;
import com.speedyao.spring.fanxing.*;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoWiredTest {
    @Test
    public void test0(){
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(MainConfig0.class);
        for(String name:context.getBeanDefinitionNames()){
            System.out.println(String.format("%s->%s",name,context.getBean(name)));
        }
    }
    @Test
    public void test1(){
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(MainConfig1.class);
        for(String name:context.getBeanDefinitionNames()){
            System.out.println(String.format("%s->%s",name,context.getBean(name)));
        }
    }
    @Test
    public void test2(){
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(MainConfig2.class);

        System.out.println(context.getBean(UserDao.class));
        System.out.println(context.getBean(OrderDao.class));
        UserService userService = context.getBean(UserService.class);
        OrderService orderService = context.getBean(OrderService.class);
        System.out.println(userService.getDao());
        System.out.println(orderService.getDao());
        userService.save(new UserModel());
        orderService.save(new OrderModel());

    }
}
