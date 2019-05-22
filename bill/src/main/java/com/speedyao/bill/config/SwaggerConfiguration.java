package com.speedyao.bill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("叫叫账单").genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false).forCodeGeneration(true).pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select().paths((PathSelectors.regex("/speedyao/bill/.*")))// 过滤的接口
                .build().apiInfo(crmApiInfo());
    }

    private ApiInfo crmApiInfo() {
        ApiInfo apiInfo = new ApiInfo("叫叫账单Api接口",//大标题
                "为生活带来便利",//小标题
                "1.0",//版本
                null,
                (Contact)null,//作者
                null,//链接显示文字
                null//网站链接
        );
        return apiInfo;
    }
}
