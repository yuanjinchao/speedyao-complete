import com.speedyao.media.MediaUtil;
import com.speedyao.net.http.HttpClientUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by speedyao on 2018/5/18.
 */
public class HttpTest {
    @Test
    public void test()  {
        HttpClientUtil clientUtil = HttpClientUtil.getInstance();
        long count=0L;
        while (true) {
            try {
                String s = clientUtil.doGet("http://wx.tjgaj.gov.cn/WeixinWebapp/Index/user.aspx", null);
                System.out.println(s);
                if (!s.contains("404 Not Found")) {
                    int i = 5;
                    while (i > 0) {
                        MediaUtil.ring();
                        i--;
                    }
                    return;
                }
                Thread.sleep(10*1000);
                count++;
                if(count%100==0){
                    System.out.println("查询"+count+"次");
                }
            } catch (Exception e) {
                System.out.println("网站垃圾");
                System.out.println(e.getMessage());
            }
        }
    }
    @Test
    public void test1() throws IOException, InterruptedException {
        HttpClientUtil clientUtil = HttpClientUtil.getInstance();
        for(int i=0;i<1000;i++){
            try {
                clientUtil.doGet("https://market.m.taobao.com/app/idleFish-F2e/widle-taobao-rax/page-detail?wh_weex=true&wx_navbar_transparent=true&id=593584048233&ut_sk=1.XCBTu5P64qsDAFmfTaFJ2MDg_12431167_1557367586819.Copy.detail.593584048233.256616410&forceFlush=1",null);
//                clientUtil.doGet("https://blog.csdn.net/u010430495/article/details/87376333",null);
//                clientUtil.doGet("https://blog.csdn.net/u010430495/article/details/88872098",null);
            } catch (IOException e) {
                continue;
            }
            if(i%10==0){
                System.out.println("已请求："+i);
            }
            Thread.sleep(60*1000);
        }

    }
}
