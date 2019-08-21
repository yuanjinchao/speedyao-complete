package com.speedyao.proxy;

public class Teacher implements Person {
    private String name;

    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void say() {
        System.out.println(String.join(",","I'm a teacher"));
    }
}
