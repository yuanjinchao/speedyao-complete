package com.speedyao.bill;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by speedyao on 2019/5/16.
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.speedyao.bill.**")
@EnableJpaRepositories(basePackages = "com.speedyao.bill.dao")
@EnableScheduling
public class Application {
    static ConfigurableApplicationContext context;
    public static void main(String[] args) {
        context = SpringApplication.run(Application.class);
    }
}
