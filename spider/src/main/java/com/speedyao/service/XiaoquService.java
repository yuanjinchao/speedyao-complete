package com.speedyao.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by speedyao on 2018/10/15.
 */
@Service
public class XiaoquService {

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;
    public void testOauth(){
        Map<String, Object> map = new HashMap<>();
        map.put("queryType", 3);
        map.put("phoneNum", "13421535350");

        JSONObject jsonObject = oAuth2RestTemplate
                .postForObject("http://10.152.16.12:9801/analysis-web/v1/api/analysis",
                        JSONObject.toJSON(map), JSONObject.class);
        System.out.println(jsonObject);
    }
}
