package com.speedyao.bill.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by speedyao on 2019/5/16.
 */
@Data
@ConfigurationProperties("mail")
public class MailProperties {
    private String host;
    private String port;
    private String user;
    private String password;
}
