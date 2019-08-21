package com.speedyao.thread;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadMain {
    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 5,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(5));
        for(int i=0;i<10;i++){
            poolExecutor.execute(()->{
                int count=1;
                while (count<=2){
                    try {
                        System.out.println(Thread.currentThread().getId()+"等待："+count);
                        Thread.sleep(100000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        count++;
                    }
                }
            });
        }

        System.in.read();
    }
}
