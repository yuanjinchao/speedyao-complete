package com.speedyao;

/**
 * Created by speedyao on 2018/11/8.
 */
public class ReflectModel {
    String a;

    public int add1(int num) {
        return num+1;
    }

    public ReflectModel setA(String a) {
        this.a = a;
        return this;
    }
}
