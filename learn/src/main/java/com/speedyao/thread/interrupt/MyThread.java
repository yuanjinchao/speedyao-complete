package com.speedyao.thread.interrupt;

public class MyThread  extends Thread{
    public MyThread(String name) {
        super(name);
    }

    public MyThread() {
    }

    @Override
    public void run() {
        System.out.println(this.getName()+" 开始执行");
        while(!this.isInterrupted()){

        }
    }
}
