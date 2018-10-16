import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.speedyao.spider.lianjia.LianjiaSpider;
import com.speedyao.spider.lianjia.vo.HouseVo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by speedyao on 2018/10/12.
 */
public class HtmlTest {
    Logger logger= LoggerFactory.getLogger(HtmlTest.class);

    @Test
    public void test1() throws IOException {
        Document document = Jsoup.parse(new File("E:\\001.txt"), "UTF-8");
        Elements trs = document.getElementsByTag("tr");
        List<String> list = new ArrayList<>();
        trs.forEach(tr -> {
            Elements tds = tr.getElementsByTag("td");
            StringBuilder sb = new StringBuilder();
            tds.forEach(td -> {
                Elements ps = td.getElementsByTag("p");
                if (ps.size() == 1) {
                    String text = ps.get(0).text();
                    sb.append(text).append(":");
                }
            });
            sb.deleteCharAt(sb.lastIndexOf(":"));
            list.add(sb.toString());
        });

        list.forEach(line -> System.out.println(line));

    }


    @Test
    public void test2() throws IOException {
//        String _3 = "城市之光别馆公寓、城市之光月光园、城市之光星光园、春华里、城市之星大厦、秋实园、万春花园、新博园、汇和家园、久福园、美福园、明和里、裕阳花园、惠森花园、巨福新园、华莹里、金康园、盛华园、裕阳大厦、聚安东园、聚安西园、英禧里、巨福园、城市星座大厦、东景大厦、姚台大街、华馨公寓、新天地家园、瑞金里、华光里、华庆里、鑫泰家园、瑞光里、惠康家园、华康里、翰林园、华泰里、欣荣馨苑、新开路355号、祥悦新居、弘轩公寓、华康西里、华腾里、日月家园、美景花园、华昌南里、华郡新苑、华旺里、康馨里、欣荣嘉园、再兴里、中北里、东屏园、卫国道、木材楼、福旺花园、海兰公寓、华旭里、金盾里、华鹏里、恋日花园、泉星楼、弘怡里、华亭里、华建里、外贸楼、嘉华里、嘉华新苑、顺泰公寓、红星路178号、晓德里、建宁里、金色家园、卫国道、环卫楼、韶山道、工业大学家属院、北海里、润晖公寓、来安里、金湾花园、懿德园、唐口新村六段、晶品轩、逸品轩、金堂南里、静墅里、东尚名居、尚东馨园、唐口新村八段、唐口新村九段、金堂家园、";
        String _3 = "嘉华里、嘉华新苑、顺泰公寓、红星路178号、晓德里、建宁里、金色家园、卫国道、环卫楼、韶山道、工业大学家属院、北海里、润晖公寓";
        String[] split = _3.split("、");
        List<HouseVo> list = new ArrayList<>();
        LianjiaSpider lianjiaSpider = new LianjiaSpider();
        for (String content : split) {
            lianjiaSpider.getLianjiaInfo(content,list);
        }
        list.sort((a, b) -> {
            if (a.getTotalPrice() > b.getTotalPrice()) {
                return 1;
            } else if (a.getTotalPrice() == b.getTotalPrice()) {
                return 0;
            } else {
                return -1;
            }
        });
        System.out.println(list.size()+"");
        list.forEach(vo -> System.out.println(vo.toString()));
    }


    @Test
    public void testLogger(){
        logger.info("a");
    }


}
