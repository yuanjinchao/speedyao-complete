package com.speedyao;

import com.alibaba.fastjson.JSONObject;
import com.speedyao.dao.mapper.XiaoquMapper;
import com.speedyao.dao.model.Xiaoqu;
import com.speedyao.office.ExcelUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpiderApplication.class)// 指定spring-boot的启动类
public class SpiderApplicationTests {


    @Autowired
    XiaoquMapper xiaoquMapper;

    @Test
    public void importData() {
        String path = "C:\\Users\\Administrator\\Desktop\\河东区小学学区分片.xlsx";
        Map<String, List<JSONObject>> map = ExcelUtil.read2007(path, 0, new String[]{"sheet2"});
        List<JSONObject> list = map.get("sheet2");
        list.forEach(json -> {
            String school = json.getString("school");
            String eduArea = json.getString("eduArea");
            String names = json.getString("names");
            String[] split = names.split("、");
            for (String name : split) {
                Xiaoqu xiaoqu = new Xiaoqu();
                xiaoqu.setName(name.toString());
                xiaoqu.setSchool(school);
                xiaoqu.setEduArea(eduArea);
                xiaoquMapper.insert(xiaoqu);
            }
        });
    }

}
