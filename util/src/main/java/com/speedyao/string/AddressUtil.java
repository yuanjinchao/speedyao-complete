package com.speedyao.string;


import org.apache.commons.lang.StringUtils;

/**
 * Created by Administrator on 2017/6/22.
 */
public class AddressUtil {
    /**
     * 传入一个地址返回一个市
     *
     * @param address
     * @return
     */
    public static String getCityShort(String address) {
        String city = address;
        if (StringUtils.isNotEmpty(address)) {
            if (address.contains("省")) {
                city = address.split("省")[1];
            } else if (address.contains("自治区")) {
                city = address.split("自治区")[1];
            }
            if (StringUtils.isNotEmpty(city)) {
                if (city.contains("市")) {
                    city = city.split("市")[0];
                } else if (city.contains("自治州")) {
                    city = city.split("自治州")[0];
                    if (StringUtils.isNotEmpty(city)) {
                        city = city.replaceAll("朝鲜族", "")
                                .replaceAll("土家族", "")
                                .replaceAll("苗族", "")
                                .replaceAll("藏族", "")
                                .replaceAll("羌族", "")
                                .replaceAll("彝族", "")
                                .replaceAll("苗族", "")
                                .replaceAll("侗族", "")
                                .replaceAll("布依族", "")
                                .replaceAll("哈尼族", "")
                                .replaceAll("壮族", "")
                                .replaceAll("傣族", "")
                                .replaceAll("白族", "")
                                .replaceAll("景颇族", "")
                                .replaceAll("傈僳族", "")
                                .replaceAll("回族", "")
                                .replaceAll("蒙古族", "")
                                .replaceAll("哈萨克", "")
                                .replaceAll("蒙古", "")
                                .replaceAll("柯尔克孜", "");
                    }
                } else if (address.contains("地区")) {
                    city = city.split("地区")[0];
                    if (StringUtils.isNotEmpty(city)) {
                        city = city.replaceAll("朝鲜族", "")
                                .replaceAll("土家族", "")
                                .replaceAll("苗族", "")
                                .replaceAll("藏族", "")
                                .replaceAll("羌族", "")
                                .replaceAll("彝族", "")
                                .replaceAll("苗族", "")
                                .replaceAll("侗族", "")
                                .replaceAll("布依族", "")
                                .replaceAll("哈尼族", "")
                                .replaceAll("壮族", "")
                                .replaceAll("傣族", "")
                                .replaceAll("白族", "")
                                .replaceAll("景颇族", "")
                                .replaceAll("傈僳族", "")
                                .replaceAll("回族", "")
                                .replaceAll("蒙古族", "")
                                .replaceAll("哈萨克", "")
                                .replaceAll("蒙古", "")
                                .replaceAll("柯尔克孜", "");
                    }
                } else if (address.contains("盟")) {
                    city = city.split("盟")[0] + "盟";
                }
            }
        }
        return city;
    }

    /**
     * 传入一个地址返回一个市
     *
     * @param address
     * @return
     */
    public static String getCity(String address) {
        String city = address;
        if (StringUtils.isNotEmpty(address)) {
            if (address.contains("省")) {
                city = address.split("省")[1];
            } else if (address.contains("自治区")) {
                city = address.split("自治区")[1];
            }
            if (StringUtils.isNotEmpty(city)) {
                if (city.contains("市")) {
                    city = city.split("市")[0] + "市";
                } else if (city.contains("自治州")) {
                    city = city.split("自治州")[0] + "自治州";
                } else if (address.contains("地区")) {
                    city = city.split("地区")[0] + "地区";
                } else if (address.contains("盟")) {
                    city = city.split("盟")[0] + "盟";
                }
            }
        }
        return city;
    }

    /**
     * 传入一个地址返回一个省
     *
     * @param address
     * @return
     */
    public static String getProvinceShort(String address) {
        String province = address;
        if (StringUtils.isNotEmpty(address)) {
            if (address.contains("省")) {
                province = address.split("省")[0];
            } else if (address.contains("自治区")) {
                province = address.split("自治区")[0];
                if (StringUtils.isNotEmpty(province)) {
                    province = province.replaceAll("维吾尔", "").replaceAll("壮族", "").replaceAll("回族", "");
                }
            } else if (address.contains("市") && isMunicipality(address)) {
                province = address.split("市")[0];
            }
        }
        return province;
    }
    public static boolean isMunicipality(String city) {
        boolean check = false;
        if (StringUtils.isNotEmpty(city)) {
            if (city.startsWith("110") || city.startsWith("310") || city.startsWith("120") || city.startsWith("500")
                    || city.startsWith("北京") || city.startsWith("天津") || city.startsWith("上海") || city.startsWith("重庆")) {
                check = true;
            }
        }
        return check;
    }

    /**
     * 传入一个地址返回一个省
     *
     * @param address
     * @return
     */
    public static String getProvince(String address) {
        String province = address;
        if (StringUtils.isNotEmpty(address)) {
            if (address.contains("省")) {
                province = address.split("省")[0] + "省";
            } else if (address.contains("自治区")) {
                province = address.split("自治区")[0] + "自治区";
            } else if (address.contains("市")) {
                province = address.split("市")[0] + "省";
            }
        }
        return province;
    }

    /**
     * 传入一个地址返回一个区
     *
     * @param address
     * @return
     */
    public static String getCountyShort(String address) {
        String county = address;
        if (StringUtils.isNotEmpty(address)) {
            if (address.contains("市")) {
                county = address.split("市")[1];
            } else if (address.contains("自治州")) {
                county = address.split("自治州")[1];
            } else if (address.contains("地区")) {
                county = address.split("地区")[1];
            } else if (address.contains("盟")) {
                county = address.split("盟")[1];
            } else if (address.contains("省")) {
                county = address.split("省")[1];
            }
            if (StringUtils.isNotEmpty(county)) {
                if (county.contains("县")) {
                    county = county.split("县")[0];
                } else if (county.contains("自治县")) {
                    county = county.split("自治县")[0];
                    if (StringUtils.isNotEmpty(county)) {
                        county = county.replaceAll("满族", "")
                                .replaceAll("蒙古族", "")
                                .replaceAll("回族", "")
                                .replaceAll("朝鲜族", "")
                                .replaceAll("畲族", "")
                                .replaceAll("土家族", "")
                                .replaceAll("侗族", "")
                                .replaceAll("苗族", "")
                                .replaceAll("瑶族", "")
                                .replaceAll("壮族", "")
                                .replaceAll("毛南族", "")
                                .replaceAll("仫佬族", "")
                                .replaceAll("各族", "")
                                .replaceAll("黎族", "")
                                .replaceAll("彝族", "")
                                .replaceAll("羌族", "")
                                .replaceAll("藏族", "")
                                .replaceAll("布依族", "")
                                .replaceAll("仡佬", "")
                                .replaceAll("水族", "")
                                .replaceAll("哈尼族", "")
                                .replaceAll("傣族", "")
                                .replaceAll("佤族", "")
                                .replaceAll("拉祜族", "")
                                .replaceAll("布朗族", "")
                                .replaceAll("纳西族", "")
                                .replaceAll("拉祜族", "")
                                .replaceAll("族傣族", "")
                                .replaceAll("白族", "")
                                .replaceAll("普米族", "")
                                .replaceAll("独龙族", "")
                                .replaceAll("怒族", "")
                                .replaceAll("傈僳族", "")
                                .replaceAll("哈萨克族", "")
                                .replaceAll("裕固族", "")
                                .replaceAll("东乡族", "")
                                .replaceAll("保安族", "")
                                .replaceAll("撒拉族", "")
                                .replaceAll("哈萨克", "")
                                .replaceAll("蒙古", "")
                                .replaceAll("达斡尔族", "");
                        if (StringUtils.isEmpty(county)) {
                            county = "东乡族";
                        }
                    }
                } else if (county.contains("自治旗")) {
                    county = county.split("自治旗")[0];
                    if (StringUtils.isNotEmpty(county)) {
                        county = county.replaceAll("达斡尔族", "");
                    }
                } else if (county.contains("区")) {
                    county = county.split("区")[0];
                } else if (county.contains("旗")) {
                    county = county.split("旗")[0] + "旗";
                } else if (county.contains("市")) {
                    county = county.split("市")[0];
                }
            }
        }
        return county;
    }

    /**
     * 传入一个地址返回一个市
     *
     * @param address
     * @return
     */
    public static String getCounty(String address) {
        String county = address;
        if (StringUtils.isNotEmpty(address)) {
            if (address.contains("市")) {
                county = address.split("市")[1];
            } else if (address.contains("自治州")) {
                county = address.split("自治州")[1];
            } else if (address.contains("地区")) {
                county = address.split("地区")[1];
            } else if (address.contains("盟")) {
                county = address.split("盟")[1];
            } else if (address.contains("省")) {
                county = address.split("省")[1];
            }
            if (StringUtils.isNotBlank(county)) {
                if (county.contains("县")) {
                    county = county.split("县")[0] + "县";
                } else if (county.contains("自治县")) {
                    county = county.split("自治县")[0] + "自治县";
                } else if (county.contains("自治旗")) {
                    county = county.split("自治旗")[0] + "自治旗";
                } else if (county.contains("区")) {
                    county = county.split("区")[0] + "区";
                } else if (county.contains("旗")) {
                    county = county.split("旗")[0] + "旗";
                } else if (county.contains("市")) {
                    county = county.split("市")[0] + "市";
                }
            }
        }
        return county;
    }


}
