package com.speedyao.proxy.java;

import com.speedyao.proxy.Person;
import com.speedyao.proxy.Teacher;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

public class ProxyMain {
    public static void main(String[] args)  {
        ClassLoader classLoader = Teacher.class.getClassLoader();
        TeacherInvocationHandler teacherProxy = new TeacherInvocationHandler(new Teacher());
        Object proxyTeacher = Proxy.newProxyInstance(classLoader, new Class[]{Person.class}, teacherProxy);
        Person teacher= (Person) proxyTeacher;
        teacher.say();
        System.out.println(teacher);
        generateProxyClass("$Proxy0",Teacher.class.getSuperclass());
    }

    /**
     * 生成动态地理类的字节码
     */
    public static void generateProxyClass(String proxyClass,Class... clazz) {
        byte[] byteClass = ProxyGenerator.generateProxyClass(proxyClass, clazz);
        File file=new File("/Users/jinchao/proxy.class");
        System.out.println(file.getPath());
        FileOutputStream fos=null;
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            fos=new FileOutputStream(file);
            fos.write(byteClass);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
