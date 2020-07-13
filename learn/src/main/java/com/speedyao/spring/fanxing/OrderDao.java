package com.speedyao.spring.fanxing;

import org.springframework.stereotype.Component;

@Component
public class OrderDao implements IDao<OrderModel> {
    @Override
    public void save(OrderModel orderModel) {
        System.out.println("order save");
    }
}
