package com.speedyao.bill.mail;

/**
 * Created by speedyao on 2019/5/16.
 */
public class MailReader {
    private MailReader() {

    }
    static class MailReaderFactory {
        static MailReader mailReader = new MailReader();

    }
     public static MailReader getInstance(){
        return MailReaderFactory.mailReader;
     }
}
