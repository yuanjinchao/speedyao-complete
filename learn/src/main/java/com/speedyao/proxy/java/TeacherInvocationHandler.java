package com.speedyao.proxy.java;

import com.speedyao.proxy.Teacher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TeacherInvocationHandler implements InvocationHandler {

    private Teacher teacher;

    public TeacherInvocationHandler(Teacher teacher) {
        this.teacher = teacher;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object invoke = method.invoke(teacher, args);
            System.out.println("执行后："+method.getName());
            return invoke;
    }
}
