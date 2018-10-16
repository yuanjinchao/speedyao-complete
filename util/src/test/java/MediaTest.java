import com.speedyao.media.MediaUtil;
import org.junit.Test;

/**
 * Created by speedyao on 2018/5/18.
 */
public class MediaTest {
    @Test
    public void testRing() throws InterruptedException {
//        Toolkit.getDefaultToolkit().beep();
        for ( int i=0;i<10;i++){

            MediaUtil.ring();
        }
        Thread.sleep(100000);
    }
}
