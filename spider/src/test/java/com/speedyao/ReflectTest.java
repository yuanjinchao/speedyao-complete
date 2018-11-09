package com.speedyao;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by speedyao on 2018/11/8.
 */
public class ReflectTest {
    public static final int count = 10000000;

    /**
     * 测试对象反射性能差距
     */
    @Test
    public void testInstance() throws IllegalAccessException, InstantiationException {
        ReflectModel[] arr=new ReflectModel[count];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            arr[i]= new ReflectModel();
        }
        System.out.println(arr[count-1]);
        System.out.println(System.currentTimeMillis()-startTime);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            ReflectModel a = ReflectModel.class.newInstance();
            arr[i]=a;
        }
        System.out.println(arr[count-1]);
        System.out.println(System.currentTimeMillis()-startTime);
    }

    /**
     * 测试执行性能差距
     */
    @Test
    public void testExecute() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int a=1;
        ReflectModel model=new ReflectModel();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            a=model.add1(a);
        }
        System.out.println(System.currentTimeMillis()-startTime);
        System.out.println(a);
        a=1;
        startTime = System.currentTimeMillis();
        Method method = ReflectModel.class.getMethod("add1", int.class);
        for (int i = 0; i < count; i++) {
            a= (int) method.invoke(model,a);
        }
        System.out.println(System.currentTimeMillis()-startTime);
        System.out.println(a);

    }

    /**
     * 测试方法通过反射获得并执行的差距
     */
    @Test
    public void testMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int a=1;
        ReflectModel model=new ReflectModel();
        long startTime = System.currentTimeMillis();
        Method method = ReflectModel.class.getMethod("add1", int.class);
        for (int i = 0; i < count; i++) {
            a= (int) method.invoke(model,a);
        }
        System.out.println(System.currentTimeMillis()-startTime);
        System.out.println(a);
        a=1;
        startTime = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Method method1 = ReflectModel.class.getMethod("add1", int.class);
            a= (int) method1.invoke(model,a);
        }
        System.out.println(System.currentTimeMillis()-startTime);
        System.out.println(a);
    }



}
