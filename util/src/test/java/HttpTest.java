import com.speedyao.media.MediaUtil;
import com.speedyao.net.http.HttpClientUtil;
import org.junit.Test;

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
}
