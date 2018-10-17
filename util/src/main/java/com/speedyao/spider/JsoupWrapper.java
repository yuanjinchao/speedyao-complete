package com.speedyao.spider;

import com.speedyao.date.DateUtils;
import com.speedyao.encrypt.MD5;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * jsoup包装类，将请求结果都记录到文件中
 * Created by speedyao on 2018/10/17.
 */
public class JsoupWrapper {

    static Logger logger = LoggerFactory.getLogger(JsoupWrapper.class);
    static ExecutorService executorService = Executors.newFixedThreadPool(2);
    final static String LOG_DIR = "E:\\spider\\";
    static boolean enableLog=true;

    /**
     *
     * @param url
     * @param timeout
     * @return
     * @throws IOException
     */
    public static Document parse(URL url, int timeout) throws IOException {
        Document document = Jsoup.parse(url, timeout);
        if(enableLog){
            executorService.execute(() -> {
                StringBuilder sb = new StringBuilder(LOG_DIR)
                        .append(DateUtils.currentDateStr())
                        .append("\\")
                        .append(MD5.md5(url.toString()))
                        .append(".spider");
                try {
                    FileUtils.writeStringToFile(new File(sb.toString()), document.toString());
                } catch (IOException e) {
                    logger.error("{}:写入文件[{}]失败",url,sb.toString());
                }
            });
        }
        return document;
    }

}
