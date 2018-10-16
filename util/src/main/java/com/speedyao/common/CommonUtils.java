package com.speedyao.common;

import java.lang.reflect.Field;

/**
 * Created by speedyao on 2018/6/22.
 */
public class CommonUtils {
    /**
     * 将o1相同的字段赋值给o2
     *
     * @param source
     * @param dest
     */
    public static void  copyDiffObject(Object source,Object dest){

        Field[] fields2 = dest.getClass().getDeclaredFields();
        for(Field field:fields2){
            try {
                String _name=new StringBuffer(field.getName().substring(0,1).toUpperCase()).append(field.getName().substring(1)).toString();
                String getName=new StringBuffer("get").append(_name).toString();
                String setName=new StringBuffer("set").append(_name).toString();
                Object value = source.getClass().getDeclaredMethod(getName, null).invoke(source);
                if(value!=null&&value.getClass()==field.getType()){
                    dest.getClass().getDeclaredMethod(setName,field.getType()).invoke(dest,value);
                }
            } catch (Exception e) {
                continue;
            }
        }
    }

}
