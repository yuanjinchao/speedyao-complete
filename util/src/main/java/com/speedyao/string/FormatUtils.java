package com.speedyao.string;

import org.apache.commons.lang.StringUtils;

/**
 * 数据格式工具类
 * Created by speedyao on 2017/12/14.
 */
public class FormatUtils {
    /**
     * 过滤手机号码中的乱码字符
     *
     * @param phoneNum
     *
     * @return
     */
    public static String formatPhoneNum(String phoneNum) {
        if (StringUtils.isNotBlank(phoneNum)) {
            phoneNum = phoneNum.replaceAll("\\+0086|\\+0085|\\+086|\\+085\\+0|\\+86|\\+85|\\D", "");
            phoneNum = phoneNum.replaceFirst("^86|^12593|^17951", "");
            return phoneNum;
        } else {
            return null;
        }
    }

    /**
     * 格式化姓名
     * @param name
     * @return
     */
    public static String formatName(String name) {
        if (StringUtils.isNotBlank(name)) {
            name = name.replaceAll("x", "*");
        }else{
            name=null;
        }
        return name;
    }

    /**
     * 格式化学历
     * @param education
     * @return
     */
    public static String formatEducation(String education){
        if(StringUtils.isNotBlank(education)){
            if(education.contains("本科")){
                education="本科";
            }else if(education.contains("大专")){
                education="大专";
            }
            return education;
        }else{
            return  null;
        }

    }
}
