package com.speedyao.net.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 邮件发送单例
 * Created by speedyao on 2018/8/17.
 */
public class MailSender {
    private static final MailSender instance = new MailSender();

    private MailConfig mailConfig;

    private MailSender() {

    }

    public static MailSender getInstance() {
        return instance;
    }

    /**
     * 发送邮件
     *
     * @param subject
     * @param message
     * @param filePaths
     * @throws Exception
     */
    public void sendData(MailConfig mailConfig, String subject, String message, String[] filePaths) throws Exception {
        this.mailConfig = mailConfig;
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", mailConfig.getSmtpHost());
        //创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来,默认打印
        session.setDebug(mailConfig.getShowDebug());
        //创建邮件的实例对象
        Message msg = getMimeMessage(session, subject, message, filePaths);
        //根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(mailConfig.getSenderAccount(), mailConfig.getSenderPassword());
        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg, msg.getAllRecipients());
        //5、关闭邮件连接
        transport.close();
    }

    /**
     * 生成带表格的正文
     *
     * @param content 正文
     * @param table   表格数据 行(列)
     * @return
     */
    public String getTable(String content, List<List<String>> table) {
        StringBuffer textMsg = new StringBuffer("<p style=\"text-indent:1cm;\">" + content + "</p>")
                .append("<br>")
                .append("<table border=1 cellspacing=0  cellpadding=5>");
        table.forEach(row -> {
            textMsg.append("<tr>");
            row.forEach(str -> textMsg.append("<td>").append(str).append("</td>"));
            textMsg.append("</tr>");
        });
        textMsg.append("</table>");
        return textMsg.toString();
    }


    /**
     * 获取邮箱实体
     *
     * @param session
     * @param subject
     * @param message
     * @param filePaths
     * @return
     * @throws Exception
     */
    private MimeMessage getMimeMessage(Session session, String subject, String message, String[] filePaths) throws Exception {
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(mailConfig.getSenderAddress()));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        //设置发送
        String recipientAddress_to = mailConfig.getRecipientAddress_to();
        String[] toArr = recipientAddress_to.split(",");
        InternetAddress[] toAddress = new InternetAddress[toArr.length];
        for (int i = 0; i < toArr.length; i++) {
            toAddress[i] = new InternetAddress(toArr[i]);
        }
        msg.setRecipients(MimeMessage.RecipientType.TO, toAddress);
        //设置抄送
        String[] ccArr;
        if (mailConfig.getRecipientAddress_cc() != null && (ccArr = mailConfig.getRecipientAddress_cc().split(",")).length > 0) {
            InternetAddress[] ccAddress = new InternetAddress[ccArr.length];
            for (int i = 0; i < ccArr.length; i++) {
                ccAddress[i] = new InternetAddress(ccArr[i]);
            }
            msg.setRecipients(MimeMessage.RecipientType.CC, ccAddress);
        }
        //设置邮件主题
        msg.setSubject(subject, "UTF-8");

        // 创建文本节点
        MimeBodyPart text = new MimeBodyPart();
        // 添加文字
        if (message == null) {
            message = "";
        }
        text.setContent(message, "text/html;charset=UTF-8");

        // 文本和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        //添加文本节点
        mm.addBodyPart(text);
        //添加附件
        if (filePaths != null && filePaths.length > 0) {
            for (String filePath : filePaths) {
                // 创建附件"节点"
                MimeBodyPart attachment = new MimeBodyPart();
                // 读取本地文件
                DataHandler dh2 = new DataHandler(new FileDataSource(filePath));
                // 将附件数据添加到"节点"
                attachment.setDataHandler(dh2);
                // 设置附件的文件名（需要编码）
                attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
                mm.addBodyPart(attachment);
            }
        }
        mm.setSubType("mixed");         // 混合关系
        msg.setContent(mm);
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }

    public static class MailConfig {
        //发件人地址
        String senderAddress;
        //收件人地址
        String recipientAddress_to;
        //抄送人地址
        String recipientAddress_cc;
        //发件人账户名
        String senderAccount;
        //发件人账户密码
        String senderPassword;
        //发件服务器
        String smtpHost;
        boolean showDebug = true;

        public boolean getShowDebug() {
            return showDebug;
        }

        public void setShowDebug(boolean showDebug) {
            this.showDebug = showDebug;
        }

        public String getSenderAddress() {
            return senderAddress;
        }

        public void setSenderAddress(String senderAddress) {
            this.senderAddress = senderAddress;
        }

        public String getRecipientAddress_to() {
            return recipientAddress_to;
        }

        public void setRecipientAddress_to(String recipientAddress_to) {
            this.recipientAddress_to = recipientAddress_to;
        }

        public String getRecipientAddress_cc() {
            return recipientAddress_cc;
        }

        public void setRecipientAddress_cc(String recipientAddress_cc) {
            this.recipientAddress_cc = recipientAddress_cc;
        }

        public String getSenderAccount() {
            return senderAccount;
        }

        public void setSenderAccount(String senderAccount) {
            this.senderAccount = senderAccount;
        }

        public String getSenderPassword() {
            return senderPassword;
        }

        public void setSenderPassword(String senderPassword) {
            this.senderPassword = senderPassword;
        }

        public String getSmtpHost() {
            return smtpHost;
        }

        public void setSmtpHost(String smtpHost) {
            this.smtpHost = smtpHost;
        }

        public boolean isShowDebug() {
            return showDebug;
        }
    }

}
