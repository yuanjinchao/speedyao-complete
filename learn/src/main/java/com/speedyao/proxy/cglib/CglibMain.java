package com.speedyao.proxy.cglib;

import com.speedyao.proxy.Teacher;
import net.sf.cglib.proxy.Callback;import net.sf.cglib.proxy.Enhancer;


import java.io.IOException;

public class CglibMain {
    public static void main(String[] args) throws IOException {
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(Teacher.class);
        enhancer.setCallbacks(new Callback[]{new DefaultProxy(),new CglibProxy()});
        enhancer.setCallbackFilter(new MethodFilter());
        Teacher proxy= (Teacher) enhancer.create();
        System.out.println(proxy.toString());
        proxy.say();
        System.in.read();
    }
}
