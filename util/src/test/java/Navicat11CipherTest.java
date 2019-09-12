import com.speedyao.database.Navicat11Cipher;
import org.junit.Test;

public class Navicat11CipherTest {
    @Test
    public void testDec(){
        Navicat11Cipher cipher=new Navicat11Cipher();
        System.out.println(cipher.DecryptString("7130DA2E824FE124BE56F9F599BAF6"));
    }
}
