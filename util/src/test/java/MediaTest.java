import com.speedyao.media.MediaUtil;
import org.junit.Test;

import java.io.IOException;

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

    @Test
    public void testCue() throws IOException {
       String  file="/Users/jinchao/Music/My/邓紫棋G.E.M - 新的心跳/CDImage.cue";
       MediaUtil.decodeCue(file);
    }
}
