package com.speedyao.net.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 单例模式
 * Created by speedyao on 2017/5/31.
 */

public class HttpClientUtil {

    Log logger = LogFactory.getLog(this.getClass());

    private final static String DEFAULT_CHARSET = "UTF-8";
    private final static int  MAX_TOTAL_CONN=30;
    private final static int  MAX_PER_ROUTE_CONN=5;
    private final static int  CONN_TIMEOUT=5*1000;
    private final static int  SO_TIMEOUT=5*1000;

    /*
    httpClient缓冲池
     */
    private  HttpClientBuilder httpClientBuilder;

    /**
     * 私有化构造函数
     */
    private HttpClientUtil() {
        PoolingHttpClientConnectionManager commManager = new PoolingHttpClientConnectionManager();
        commManager.setMaxTotal(MAX_TOTAL_CONN);
        commManager.setDefaultMaxPerRoute(MAX_PER_ROUTE_CONN);

        httpClientBuilder = HttpClients.custom().setDefaultRequestConfig(
                RequestConfig.custom().setSocketTimeout(SO_TIMEOUT).setConnectTimeout(CONN_TIMEOUT).build())
                .setConnectionManager(commManager)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
    }

    /**
     * 匿名内部类用于保证懒加载单例的线程安全
     */
    private static class HttpClientUtilHandler {
        private static HttpClientUtil instance=new HttpClientUtil();
    }

    public static HttpClientUtil getInstance(){
        return HttpClientUtilHandler.instance;
    }

    /**
     * 获取可缓存的httpclient
     * @return
     */
    public CloseableHttpClient getClient() {

        return httpClientBuilder.build();
    }

    public String sendPost(String url, Map<String, String> reqParams) throws IOException {
        return sendPostWithCharset(url, reqParams, null);
    }

    public String sendPostWithCharset(String url, Map<String, String> reqParams, String charset) throws IOException {
        CloseableHttpClient client = this.getClient();
        HttpPost post = new HttpPost(url);
        List<BasicNameValuePair> reqParamList = new ArrayList();
        for (Map.Entry<String, String> entry : reqParams.entrySet()) {
            reqParamList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        if (reqParamList.size() > 0) {
            post.setEntity(new UrlEncodedFormEntity(reqParamList, DEFAULT_CHARSET));
        }

        CloseableHttpResponse resp = null;
        try {
            resp = client.execute(post);
            if (charset != null) {
                return EntityUtils.toString(resp.getEntity(), charset);
            } else {
                return EntityUtils.toString(resp.getEntity());
            }
        } finally {
            if (resp != null) {
                resp.close();
            }
        }
    }

    public String doPost(String url, String jsonStrData) throws Exception {
        return doPostWithCharset(url, jsonStrData, null);
    }


    public String doGet(String url, Map<String, String> headerParm, int... args) throws IOException {
        CloseableHttpClient client = this.getClient();
        CloseableHttpResponse resp = null;
        HttpGet get = new HttpGet(url);
        if (args!= null && args.length > 0) {
            get.setConfig(RequestConfig.custom().setSocketTimeout(args[0]).build());
        }
        if (headerParm != null && headerParm.size() > 0) {
            for (Map.Entry<String, String> entry : headerParm.entrySet()) {
                get.addHeader(entry.getKey(), entry.getValue());
            }
        }
        try {
            resp = client.execute(get);
            return EntityUtils.toString(resp.getEntity());
        } finally {
            if (resp != null)
                resp.close();
        }
    }


    /**
     * 指定返回体字符编码
     * @param url
     * @param jsonStrData
     * @param charset
     * @return
     * @throws IOException
     */
    public String doPostWithCharset(String url, String jsonStrData, String charset) throws Exception {
        String result = "";
        CloseableHttpClient client = this.getClient();
        CloseableHttpResponse response = null;
        HttpPost post = new HttpPost(url);
        try {

            HttpEntity param = new StringEntity(jsonStrData, "UTF-8");
            post.setEntity(param);
            post.setHeader("Content-type", "application/json");
            response = client.execute(post);
            //int httpCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (charset != null) {
                result = EntityUtils.toString(entity, charset);
            } else {
                result = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            logger.error("doPost request exception :", e);
            throw e;
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return result;
    }
}
