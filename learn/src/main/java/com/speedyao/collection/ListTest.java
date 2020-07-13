package com.speedyao.collection;

import org.junit.Test;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

public class ListTest {
    class Father{

    }
    class Child extends Father{

    }
    @Test
    public void test1(){
        Child[] children={new Child(),new Child()};
        System.out.println(children.getClass());
        Father[] fathers=children;
        System.out.println(fathers.getClass());
        fathers[0]=new Father();
    }
    @Test
    public void test2(){
        List<String> list = Arrays.asList("JavaEdge","JavaApp");
        String[] arr = list.toArray(new String[4]);
        System.out.println(arr.getClass());
        System.out.println(arr.length);
    }
}
