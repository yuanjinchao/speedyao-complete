package com.speedyao.spider.lianjia;

import com.speedyao.spider.lianjia.vo.HouseVo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by speedyao on 2018/10/12.
 */
public class LianjiaSpider {
    static Logger logger = LoggerFactory.getLogger(LianjiaSpider.class);

    /**
     * 获取链家的房产信息
     *
     * @param content
     * @throws IOException
     */
    public static List<HouseVo> getLianjiaInfo(String content) throws IOException {
        List<HouseVo> list = new ArrayList<>();
        String baseUrl = "https://tj.lianjia.com/ershoufang";
        String url = baseUrl + "/rs" + URLEncoder.encode(content, "UTF-8") + "/";
        logger.info(content + ">>开始请求：" + url);
        Document document = Jsoup.parse(new URL(url), 30 * 1000);
        logger.info(content + ">>请求成功：" + url);
        parseSellList(list, document);
        //判断是否有分页数据
        Elements pages = document.getElementsByAttributeValue("comp-module", "page");
        if (pages.size() <= 0) {
            return list;
        }
        Element page = pages.get(0);
        Elements children = page.children();
        logger.info(content + ">>有分页数据，共有" + children.size() + "页");
        if (children.size() > 0) {
            logger.info(content + ">>有分页数据，共有" + children.size() + "页");
            children.forEach(a -> {
                if (!"1".equals(a.text())) {
                    String pageUrl = baseUrl + a.attr("href");
                    Document pageDocument;
                    try {
                        logger.info(content + ">>开始请求：" + url);
                        pageDocument = Jsoup.parse(new URL(pageUrl), 30 * 1000);
                        logger.info(content + ">>请求第" + a.text() + "页数据成功");
                        parseSellList(list, pageDocument);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return list;
    }

    private static void parseSellList(List<HouseVo> list, Document document) {
        Elements sellList = document.getElementsByAttributeValueContaining("class", "sellListContent");
        if (sellList.size() > 0) {
            Elements children = sellList.get(0).children();
            logger.info("共有" + children.size() + "条数据");
            children.forEach(element -> {
                try {
                    HouseVo vo = new HouseVo();
                    Element info = element.getElementsByClass("info clear").get(0);
                    Element title = info.getElementsByClass("title").get(0);
                    vo.setUrl(title.getElementsByTag("a").get(0).attr("href"));
                    vo.setTitle(title.text());
                    //小区、概况
                    Element houseInfo = info.getElementsByClass("houseInfo").get(0);
                    Elements xiaoqu = houseInfo.getElementsByTag("a");
                    vo.setXiaoqu(xiaoqu.text());
                    vo.setXiaoquUrl(xiaoqu.attr("href"));
                    vo.setInfo(houseInfo.text());
                    //价格
                    Element priceInfo = element.getElementsByClass("priceInfo").get(0);
                    String totalPrice = priceInfo.getElementsByClass("totalPrice").get(0).getElementsByTag("span").text();
                    vo.setTotalPrice(Double.parseDouble(totalPrice));
                    String unitPrice = priceInfo.getElementsByClass("unitPrice").get(0).getElementsByTag("span").text();
                    vo.setUnitPrice(unitPrice);
                    //positionInfo
                    Element positionInfo = element.getElementsByClass("positionInfo").get(0);
                    vo.setFlood(positionInfo.text());
                    vo.setPositionUrl(positionInfo.getElementsByTag("a").attr("href"));
                    vo.setPosition(positionInfo.getElementsByTag("a").text());
                    //followInfo
                    vo.setFollowInfo(element.getElementsByClass("followInfo").text());
                    //tag
                    Elements tag = element.getElementsByClass("tag");
                    StringBuilder tagSb = new StringBuilder();
                    tag.get(0).children().forEach(span -> tagSb.append(span.text()).append("-"));
                    vo.setTag(tagSb.toString());
                    element.getElementsByClass("positionInfo");
                    list.add(vo);
                } catch (Exception e) {
                    logger.error(element.toString());
                    logger.error(e.getMessage(), e);
                }
            });

        }
    }
}
