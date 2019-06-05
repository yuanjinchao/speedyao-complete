import com.speedyao.ringbuffer.RingBuffer;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by speedyao on 2019/6/5.
 */
public class RingBufferTest {



    @Test
    public void main() throws InterruptedException, IOException {
        RingBuffer ringBuffer=new RingBuffer(100);

        new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                while (!ringBuffer.put(i)){

                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 100000; i < 100000*2; i++) {
                while (!ringBuffer.put(i)){

                }
            }
        }).start();


        List list1=new ArrayList();
        List list2=new ArrayList();

        new Thread(() -> {
            while (true){
                Object take = ringBuffer.take();
                if(take!=null){
                    list1.add(take);
                }
                if(list1.size()+list2.size()==100000*2)break;
            }

        }).start();


        while (true){
            Object take = ringBuffer.take();

            if(take!=null){
                list2.add(take);
            }
            if(list1.size()+list2.size()==100000*2)break;
        }
        System.out.println(list1.size());
        System.out.println(list2.size());
    }
}
