package com.speedyao;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages = {"com.speedyao.**"})
@MapperScan(basePackages = {"com.speedyao.dao.mapper"})
public class SpiderApplication {
	static Logger logger= LoggerFactory.getLogger(SpiderApplication.class);
	public static void main(String[] args) {
		logger.info("开始启动");
		ConfigurableApplicationContext context = SpringApplication.run(SpiderApplication.class, args);
		String[] activeProfiles = context.getEnvironment().getActiveProfiles();
		logger.info(Arrays.toString(activeProfiles));
		logger.info("启动成功");
	}
}
