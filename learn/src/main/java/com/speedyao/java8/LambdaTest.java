package com.speedyao.java8;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

@Slf4j
public class LambdaTest {

    interface MathOperation{
        //default修饰的方法可以作为后期接口的扩充使用
        default int sub(int a,int b){
          return  a-b;
        }
        int add(int a,int b);
    }

    interface Logger{
        void info(String message);
    }
    //这个注解是约束"只包含一个抽象方法的接口类型"，这样才能够使用lambda表达式
    @FunctionalInterface
    interface Converter<F,T>{
        T convert(F f);

    }
    class MyConvert{
        Integer convert(String a){
            System.out.println("MyConvert parseInt");
            return Integer.parseInt(a);
        }
    }
    @Test
    public void test1(){
        MathOperation mathOperation=(a,b)->a+b;
        Logger logger=msg-> System.out.println("say:"+msg);
        System.out.println(mathOperation.add(1,2));
        System.out.println(mathOperation.sub(1,2));
        logger.info("I'm stronger");

        //静态方法引用
        Converter<String,Integer> converter1=Integer::parseInt;
        System.out.println(converter1.convert("2"));
        //对象方法引用
        Converter<String,Integer> convert2=new MyConvert()::convert;
        System.out.println(convert2.convert("2"));

        int num=2;
        Converter<Integer,String> convert3=a->String.valueOf(a+num);
        System.out.println(convert3.convert(1));


    }
    @Test
    public void testMap(){
        Map<String,String> map=new HashMap<>();
        map.putIfAbsent("1","1");
        //只有不存在才put
        map.putIfAbsent("1","2");
        map.put("2","2");
        //只有key value都匹配才替换
        map.replace("1","1","3");
        //如果有值才计算
        map.computeIfPresent("1",(k,v)->String.join("-",k,v));
        map.forEach((k,v)->{
            map.computeIfPresent(k,(k1,v1)->k1+"-"+v1);
        });
        map.compute("3",(k,v)->String.join(k,v));
        System.out.println(map);
    }
}