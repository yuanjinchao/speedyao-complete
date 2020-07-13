package com.speedyao.spring.fanxing;

import org.springframework.stereotype.Component;

@Component
public class UserDao implements IDao<UserModel> {
    @Override
    public void save(UserModel userModel) {
        System.out.println("user save");
    }
}
