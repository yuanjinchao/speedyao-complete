package com.speedyao.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by speedyao on 2018/6/25.
 */
public class DateUtils {

    /**
     * 根据出生时间获取年龄
     * @param birthDay
     * @return
     */
    public static Integer getAge(Date birthDay){
        if(birthDay==null){
            return null;
        }
        Calendar now = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthDay);
        if(now.compareTo(birth)!=1){
            return null;
        }
        int age=now.get(Calendar.YEAR)-birth.get(Calendar.YEAR);
        if(now.get(Calendar.MONTH)<birth.get(Calendar.MONTH)){
            age--;
        }else if(now.get(Calendar.MONTH)==birth.get(Calendar.MONTH)){
            if(now.get(Calendar.DAY_OF_MONTH)<birth.get(Calendar.DAY_OF_MONTH)){
                age--;
            }
        }
        return age;
    }
    public static String formatDate(String str,Date date){
        SimpleDateFormat format=new SimpleDateFormat(str);
        return format.format(date);
    }
    public static String currentDateStr(){
        return formatDate("yyyyMMdd",new Date());
    }
}
