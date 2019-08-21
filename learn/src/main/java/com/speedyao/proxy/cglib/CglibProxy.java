package com.speedyao.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
        Object result=null;
        before();
        result= proxy.invokeSuper(o,objects);
        after();
        return result;
    }
    public Object before(){
        System.out.println("before invoke ");
        return null;
    }
    public Object after(){
        System.out.println("after invoke");
        return null;
    }
}
