package com.cup.ycode.web.slideshow.admin.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class WebSlideshowAdminSwaggerConfiguration {
    @Bean
    public Docket createWebSlideshowAdminRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cup.ycode.web.slideshow.admin"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Ycode API 文档")
                .description("Ycode API 网关接口，http://www.ycode9.com")
                .termsOfServiceUrl("http://www.ycode9.com")
                .version("1.0.0")
                .build();
    }


}