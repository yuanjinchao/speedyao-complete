package com.speedyao;

import com.speedyao.service.XiaoquService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=SpiderApplication.class)// 指定spring-boot的启动类
public class SpiderApplicationTests {

	@Autowired
	private XiaoquService xiaoquService;

	@Test
	public void contextLoads() {
		xiaoquService.testOauth();
	}

}
