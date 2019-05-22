import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.speedyao.bill.dao.ConsumeRecordDao;
import com.speedyao.bill.entity.ConsumeRecord;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by speedyao on 2019/5/16.
 */
@Slf4j

public class MailTest extends BaseTest {
    public static final int ROW_NUM = 6;

    @Autowired
    private ConsumeRecordDao dao;

    @Test
    public void test1() throws MessagingException, IOException {

        String server = "pop3.aliyun.com";
        String user = "speedyao2003@aliyun.com";
        String password = "a5335768";

        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "pop3");
        properties.setProperty("mail.imap.host", server);
        properties.setProperty("mail.imap.port", "110");
        Session session = Session.getInstance(properties);
        Store store = session.getStore("pop3");
        store.connect(user, password);
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        Message[] messages = folder.getMessages();
        log.info("共有{}封邮件", messages.length);
        List<ConsumeRecord> list = new ArrayList<>();
        for (Message message : messages) {
            Address address = message.getFrom()[0];
            String fromName = MimeUtility.decodeText(address.toString());
//            log.info("发件人：{}，主题：{}",fromName,message.getSubject());
            if (!"信用管家消费提醒".equals(message.getSubject())) {
                continue;
            }
            MimeMultipart part = (MimeMultipart) message.getContent();
            BodyPart bodyPart = part.getBodyPart(0);
            String content = bodyPart.getContent().toString();
            Document document = Jsoup.parse(content);
            Elements fonts = document.body().children().get(1).getElementsByTag("font");
            ConsumeRecord record = null;
            for (int i = 6; i < fonts.size(); i++) {
                switch (i % ROW_NUM) {
                    case 0:
                        record = new ConsumeRecord();
                        String num = fonts.get(i).text();
                        String consumeUser = null;
                        if ("2906".equals(num)) {
                            consumeUser = "张燕云";
                        } else if ("4465".equals(num)) {
                            consumeUser = "袁金超";
                        }
                        record.setUser(consumeUser);
                        break;
                    case 1:
                        record.setDate(fonts.get(i).text());
                        break;
                    case 2:
                        record.setTime(fonts.get(i).text());
                        break;
                    case 4:
                        record.setShop(fonts.get(i).text());
                        break;
                    case 5:
                        record.setAmount(Double.parseDouble(fonts.get(i).text().replace(",", "")));
                        record.setMonth(record.getDate().substring(0, 6));
                        list.add(record);
                        break;
                }
            }
        }
        dao.saveAll(list);
        folder.close(false);
        store.close();
    }

    @Test
    public void test() throws UnsupportedEncodingException {
        JSONArray jsonArray = new JSONArray();

        Iterator<ConsumeRecord> iterator = dao.findAll().iterator();
        int i=0;
        while (iterator.hasNext()){
            jsonArray.add(iterator.next());
            if(i++==5){
                break;
            }
        }
        System.out.println(jsonArray.toString());
    }




}
