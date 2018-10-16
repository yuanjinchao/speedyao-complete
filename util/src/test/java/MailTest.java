import com.speedyao.net.mail.MailSender;
import com.speedyao.office.ExcelUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by speedyao on 2018/8/16.
 */
public class MailTest {
    @Test
    public void test1() throws Exception {

        List<List<String>> list=new ArrayList<>();
        for(int i=0;i<30;i++){
            List<String> list1=new ArrayList<>();
            list1.add("第" + i + "接口");
            list1.add((i * 786) +"");
            list.add(list1);
        }
        String path1="C:\\Users\\Administrator\\Desktop\\data1.xlsx";
        String path2="C:\\Users\\Administrator\\Desktop\\data2.xlsx";

        ExcelUtil.write2007(list,path1);
        ExcelUtil.write2007(list,path2);
        MailSender mailSender= MailSender.getInstance();
        String subject="主题示例";
        String message=mailSender.getTable("正文，表格如下",list);
        mailSender.sendData(getMailConfig(),subject,message,new String[]{path1,path2});
    }


    /**
     *
     *
     * @return
     */
    private  MailSender.MailConfig getMailConfig() {
        MailSender.MailConfig mailConfig = new MailSender.MailConfig();
        mailConfig.setSmtpHost("smtp.163.com");
        //发件人地址
        mailConfig.setSenderAddress("***@163.com");
        //发件用户
        mailConfig.setSenderAccount("***");
        //发件密码
        mailConfig.setSenderPassword("***");
        //接收地址
        mailConfig.setRecipientAddress_to("*****@163.com,*****@163.com");
        //抄送地址
        mailConfig.setRecipientAddress_cc("*****@163.com,*****@163.com");

        return mailConfig;
    }

}
