package com.speedyao;

import com.alibaba.fastjson.JSONObject;
import com.speedyao.dao.mapper.HouseMapper;
import com.speedyao.dao.mapper.XiaoquMapper;
import com.speedyao.dao.model.House;
import com.speedyao.dao.model.Xiaoqu;
import com.speedyao.office.ExcelUtil;
import com.speedyao.spider.lianjia.LianjiaSpider;
import com.speedyao.spider.lianjia.vo.HouseVo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seimicrawler.xpath.JXDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by speedyao on 2018/10/16.
 */
@SpringBootTest(classes = SpiderApplication.class)
@RunWith(SpringRunner.class)
public class XiaoquTest  {
    Logger logger= LoggerFactory.getLogger(XiaoquTest.class);

    @Autowired
    private XiaoquMapper xiaoquMapper;

    @Test
    public void importData(){
        String path="d:\\天津小学学区分片.xlsx";
        Map<String, List<JSONObject>> map = ExcelUtil.read2007(path, 0, new String[]{"sheet4"});
        map.get("sheet4").forEach(json->{
            String school = json.getString("school");
            String eduArea = json.getString("eduArea");
            String names = json.getString("names");
            String[] split = names.split("、");
            for(String str:split){
                System.out.println(str+"："+school+"："+eduArea);
            }
        });
    }
    @Test
    public void insertXiaoqu() throws IOException {
        String path="d:\\天津小学学区分片.xlsx";
        Map<String, List<JSONObject>> map = ExcelUtil.read2007(path, 0, new String[]{"sheet5"});
        map.get("sheet5").forEach(json->{
            Xiaoqu xiaoqu = json.toJavaObject(Xiaoqu.class);
            xiaoquMapper.insert(xiaoqu);
        });
    }

    /**
     * 获取南开片区的小区
     */
    @Test
    public void getNankaiXuequ() throws IOException {
        Document document = Jsoup.parse(new File("d:\\南开学区划片.txt"),"UTF-8");
        Element bo = document.getElementById("bo");
        Elements tables = bo.getElementsByTag("table");
        tables.forEach(element -> element.getElementsByTag("tbody").forEach(table->{
            Elements trs = table.children();
            for(int i=0;i<trs.size();i++){
                Element tr = trs.get(i);
                if(i==0){
                    System.out.println(tr.getElementsByTag("strong").text());
                }else{
                    Elements tds = tr.getElementsByTag("td");
                    tds.forEach(td->{
                        System.out.print(td.getElementsByTag("p").text());
                        System.out.printf("：");
                    });
                    System.out.println();
                }
            }
        }));
    }
}
