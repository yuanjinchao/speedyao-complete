package com.speedyao.ringbuffer;

import sun.misc.Contended;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by speedyao on 2019/6/4.
 */
public class RingBuffer {
    private int bufferSize=1024;
    @Contended //避免伪共享
    private volatile long in;
    @Contended //避免伪共享
    private volatile long out;
    Object[]buffer;
    private static final Unsafe unsafe=getUnsafe();
    private static final long outOffset;
    private static final long inOffset;
    static{
        try {
            outOffset = unsafe.objectFieldOffset(RingBuffer.class.getDeclaredField("out"));
            inOffset = unsafe.objectFieldOffset(RingBuffer.class.getDeclaredField("in"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public RingBuffer(int bufferSize) {
        this.bufferSize = bufferSize;
        buffer=new Object[bufferSize];
    }

    public boolean isFull(){
        return in+1-out>=bufferSize;
    }
    public boolean isEmpty(){
        return in==out;
    }

    public long getIn(){
        return this.in;
    }

    public long getOut(){
        return  this.out;
    }

    public boolean put(Object object){
        if(isFull()){
            return false;
        }
        long currentIn;
        do{
            currentIn=in;
            int putIndex=(int)((currentIn)%bufferSize);
            buffer[putIndex]=object;
        }while (!unsafe.compareAndSwapLong(this,inOffset,currentIn,currentIn+1L));
        return true;
    }

    public Object take(){
        if(isEmpty()){
            return null;
        }
        Object value;
        long currentOut;

        do{
            currentOut=out;
            int getIndex= (int) (currentOut%bufferSize);
            value=buffer[getIndex];
        }while (!unsafe.compareAndSwapLong(this,outOffset,currentOut,currentOut+1));
        return value;

    }


    public static Unsafe getUnsafe(){
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe= (Unsafe) field.get(null);
            return unsafe;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
