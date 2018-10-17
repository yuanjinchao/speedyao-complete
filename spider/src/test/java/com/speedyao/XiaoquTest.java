package com.speedyao;

import com.alibaba.fastjson.JSONObject;
import com.speedyao.office.ExcelUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by speedyao on 2018/10/16.
 */
public class XiaoquTest  {

    @Test
    public void importData(){
        String path="C:\\Users\\Administrator\\Desktop\\河东区小学学区分片.xlsx";
        Map<String, List<JSONObject>> map = ExcelUtil.read2007(path, 0, new String[]{"sheet2"});

    }
}
