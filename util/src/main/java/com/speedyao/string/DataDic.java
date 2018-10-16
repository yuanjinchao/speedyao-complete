package com.speedyao.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典类
 * Created by speedyao on 2018/4/23.
 */
public class DataDic {
    private static Logger logger = LoggerFactory.getLogger(DataDic.class);


    /**
     * 身份证地区数据字典
     * key：身份证前6位
     * value:[省份,区,县]
     */
    private static Map<String, String[]> idAreaDic = new HashMap();

    static {
        loadIdDic();
    }


    /**
     * 根据身份证获取省市县
     *
     * @param id
     * @return
     */
    public static String[] getProvinceCityCountyByID(String id) {
        return idAreaDic.get(id.substring(0, 6));
    }

    /**
     * 获取性别
     *
     * @param idCard 身份证号码
     * @return 0：男，1：女
     */
    public static int getGander(String idCard) {
        if (idCard.length() == 15) {
            idCard = idCard.substring(idCard.length() - 1, idCard.length());
        } else {
            idCard = idCard.substring(16, 17);
        }

        if (Integer.parseInt(idCard) % 2 != 0) {
            return 0;
        }
        return 1;

    }

    /**
     * 获取周岁
     *
     * @param idCard
     * @return
     */
    public static int getAge(String idCard) {
        int year, month, day, idLength = idCard.length();
        Calendar cal1 = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        if (idLength == 18) {
            year = Integer.parseInt(idCard.substring(6, 10));
            month = Integer.parseInt(idCard.substring(10, 12));
            day = Integer.parseInt(idCard.substring(12, 14));
        } else if (idLength == 15) {
            year = Integer.parseInt(idCard.substring(6, 8)) + 1900;
            month = Integer.parseInt(idCard.substring(8, 10));
            day = Integer.parseInt(idCard.substring(10, 12));
        } else {
            return -1;
        }
        cal1.set(year, month, day);
        return getYearDiff(today, cal1);
    }

    /**
     * 获取出生年月日
     * 格式:yyyy-MM-dd
     * @param idCard
     * @return
     */
    public static Date getBirthDate(String idCard) {
        int year, month, day, idLength = idCard.length();
        if (idLength == 18) {
            year = Integer.parseInt(idCard.substring(6, 10));
            month = Integer.parseInt(idCard.substring(10, 12));
            day = Integer.parseInt(idCard.substring(12, 14));
        } else if (idLength == 15) {
            year = Integer.parseInt(idCard.substring(6, 8)) + 1900;
            month = Integer.parseInt(idCard.substring(8, 10));
            day = Integer.parseInt(idCard.substring(10, 12));
        } else {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        return calendar.getTime();
    }

    static int getYearDiff(Calendar cal, Calendar cal1) {
        int m = (cal.get(cal.MONTH)) - (cal1.get(cal1.MONTH));
        int y = (cal.get(cal.YEAR)) - (cal1.get(cal1.YEAR));
        return (y * 12 + m) / 12;
    }


    /**
     * 加载身份证地区信息
     */
    private static void loadIdDic() {
        try {
            InputStream inputStream = DataDic.class.getClassLoader().getResourceAsStream("id_card_area_dic.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split("=");
                String code = split[0];
                String[] areaArr = split[1].split(",");
                if (areaArr.length == 3 && code.matches("\\d{6}")) {
                    idAreaDic.put(code, areaArr);
                } else {
                    System.out.println("身份证地区数据字典格式不正确:" + line);
                    logger.error("身份证地区数据字典格式不正确:" + line);
                }

            }
            br.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());

        }
    }
}
