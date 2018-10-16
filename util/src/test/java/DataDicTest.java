import org.junit.Test;
import com.speedyao.string.DataDic;

import java.util.Arrays;

/**
 * Created by speedyao on 2018/5/18.
 */
public class DataDicTest {
    @Test
    public void test1(){
        String[] arr = DataDic.getProvinceCityCountyByID("410181198807156531");
        System.out.println(Arrays.toString(arr));
    }
}
