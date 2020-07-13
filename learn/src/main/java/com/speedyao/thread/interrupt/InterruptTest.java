package com.speedyao.thread.interrupt;

import org.junit.Test;

import java.io.IOException;

public class InterruptTest {
    @Test
    public void test () throws InterruptedException, IOException {
        MyThread thread=new MyThread("myThread");
        thread.start();
        System.out.println("MyThread是否停止1:"+thread.getState()+",中断状态："+thread.isInterrupted());
        thread.interrupt();
        System.out.println("MyThread是否停止2:"+thread.getState()+",中断状态："+thread.isInterrupted());
        System.out.println("MyThread是否停止3:"+thread.getState()+",中断状态："+thread.isInterrupted());
        thread.join();
    }
}
