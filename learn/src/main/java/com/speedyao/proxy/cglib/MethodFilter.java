package com.speedyao.proxy.cglib;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class MethodFilter implements CallbackFilter {
    @Override
    public int accept(Method method) {
        if(method.getDeclaringClass()==Object.class){
            //使用第一个代理 DefaultProxy
            return 0;
        }else{
            //使用第二个代理 CglibProxy
            return 1;
        }
    }
}
