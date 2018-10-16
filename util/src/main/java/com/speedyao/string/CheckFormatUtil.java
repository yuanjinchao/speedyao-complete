package com.speedyao.string;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * Created by gaoang on 2017/4/10.
 */
public class CheckFormatUtil {

    /**
     * 验证时间格式为"yyyy-MM-dd HH:mm:ss"
     *
     * @param date
     * @return
     */
    public static boolean checkDateFormat(String date, String dateFormat) {
        if(StringUtils.isBlank(date) || StringUtils.isBlank(dateFormat)){
            return false;
        }
//        boolean convertSuccess = false;
////        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
//        try {
//            format.setLenient(false);
//            format.parse(date);
//            convertSuccess = true;
//        } catch (ParseException e) {
//            // e.printStackTrace();
//            // 如果抛出ParseException或者NullPointerException，说明格式不对
//            convertSuccess = false;
//        }
//        return convertSuccess;
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            Date d = formatter.parse(date);
            return date.equals(formatter.format(d));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证邮箱格式
     *
     * @param email
     * @return
     */
    public static boolean checkEmailFormat(String email) {
        if(StringUtils.isBlank(email)){
            return false;
        }
        boolean tag = true;
        final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }

    /**
     * 验证手机号码
     *
     * @param phoneNo
     * @return
     */
    public static boolean phoneNoCheck(String phoneNo) {
        if(StringUtils.isBlank(phoneNo)){
            return false;
        }
        Pattern p = Pattern.compile("1\\d{10}");
        Matcher m = p.matcher(phoneNo);
        return m.matches();
    }

    /**
     * 精确判断手机号码，如发现新增号码段，请自行添加
     *
     * @param phoneNo
     * @return
     */
    public static boolean phoneCheck(String phoneNo) {
        if(StringUtils.isBlank(phoneNo)){
            return false;
        }
        Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]{9}$");
        Matcher m = p.matcher(phoneNo);
        return m.matches();
    }


    /**
     * 验证座机
     *
     * @param telNo
     * @return
     */
    public static boolean telNoCheck(String telNo) {
        if(StringUtils.isBlank(telNo)){
            return false;
        }
        Pattern p = Pattern.compile("0[0-9]{2,3}-?[0-9]{7,8}");
        Matcher m = p.matcher(telNo);
        return m.matches();
    }

    /**
     * 验证时间戳
     */
    public static boolean timestampCheck(String timestamp) {
        if(StringUtils.isBlank(timestamp)){
            return false;
        }
        Pattern p = Pattern.compile("\\d{13}");
        Matcher m = p.matcher(timestamp);
        return m.matches();
    }

    /**
     * 验证是否为有效身份证
     */
    public static boolean isIDCard(String IDStr) {
        if(StringUtils.isBlank(IDStr)){
            return false;
        }
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return false;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return false;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "身份证生日不在有效范围。";
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return false;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return false;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equalsIgnoreCase(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return false;
            }
        } else {
            return true;
        }
        // =====================(end)=====================
        return true;
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if(StringUtils.isBlank(str)){
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate
     * @return
     */
    private static boolean isDate(String strDate) {
        if(StringUtils.isBlank(strDate)){
            return false;
        }
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings("unchecked")
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /*
    校验过程：
    1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
    2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
    3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
    */



    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeBankCard
     * @return
     */
    private static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 判断 object 是否是array中的值
     *
     * @param object
     * @param array
     * @param <T>
     * @return
     */
    public static <T> boolean checkParamIsCorrect(T object, T[] array) {
        boolean flag = false;
        for (T a : array) {
            if (a.equals(object)) {
                return true;
            }
        }
        return flag;

    }

    public static boolean checkIsMac(String mac) {
        if(StringUtils.isBlank(mac)){
            return false;
        }
        String patternMac1 = "^[a-fA-F0-9]{2}+-[a-fA-F0-9]{2}+-[a-fA-F0-9]{2}+-[a-fA-F0-9]{2}+-[a-fA-F0-9]{2}+-[a-fA-F0-9]{2}$";
        String patternMac2 = "^[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}$";
        Pattern pa1 = Pattern.compile(patternMac1);
        Pattern pa2 = Pattern.compile(patternMac2);
        return pa1.matcher(mac).find() || pa2.matcher(mac).find();
    }

    /**
     * @param ip
     * @return Title: isIP<／p> Description: judge whether is an ip address<／p>
     */
    public static boolean isIP(String ip) {
        if(StringUtils.isBlank(ip)){
            return false;
        }
        boolean flag = false;
        ip = ip.trim();
        if (ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] s = ip.split("\\.");
            if (Integer.parseInt(s[0]) < 255)
                if (Integer.parseInt(s[1]) < 255)
                    if (Integer.parseInt(s[2]) < 255)
                        if (Integer.parseInt(s[3]) < 255)
                            flag = true;
        }
        return flag;
    }

    public static boolean isImei(String imei) {
        Pattern p = Pattern.compile("\\d{15}");
        Matcher m = p.matcher(imei);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(phoneCheck("12522201007"));
    }
}
