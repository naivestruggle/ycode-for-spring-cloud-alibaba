package com.cup.ycode.web.slideshow.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.cup.ycode")
@EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients(basePackages = "com.cup.ycode")
public class YcodeWebSlideshowAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(YcodeWebSlideshowAdminApplication.class, args);
    }
}
