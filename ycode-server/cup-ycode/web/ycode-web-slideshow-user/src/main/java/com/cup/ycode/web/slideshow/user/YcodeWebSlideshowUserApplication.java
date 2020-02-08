package com.cup.ycode.web.slideshow.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.cup.ycode")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.cup.ycode")
@EnableSwagger2
public class YcodeWebSlideshowUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(YcodeWebSlideshowUserApplication.class, args);
    }
}
