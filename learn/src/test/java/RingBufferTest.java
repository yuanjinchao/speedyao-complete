import com.speedyao.ringbuffer.RingBuffer;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by speedyao on 2019/6/5.
 */
public class RingBufferTest {


    private static final int putSize=100_0000;
    private static final int threadSize=5;


    @Test
    public void main() throws InterruptedException, IOException {

        System.out.println(putSize*5);

        RingBuffer ringBuffer=new RingBuffer(100);
        for (int i=0;i<threadSize;i++){
            new Thread(() -> {
                for (int j = 0;j < putSize; j++) {
                    while (!ringBuffer.put(j)){

                    }
                }
            }).start();
        }


        Thread.sleep(1000);

//        new Thread(() -> {
//            while (true){
//                Object take = ringBuffer.take();
//                if(ringBuffer.getOut()%10000==0){
//                    System.out.println(ringBuffer.getIn()+","+ringBuffer.getOut());
//                }
//                if(take!=null){
//
//                }
//
//            }
//
//        }).start();


        int count=0;
        while (true){
            Object take = ringBuffer.take();
            if (take!=null){
                count++;
            }
            if(ringBuffer.getOut()%10000==0){
                System.out.println(count);
                System.out.println(ringBuffer.getIn()+","+ringBuffer.getOut());
            }
        }
    }
}
