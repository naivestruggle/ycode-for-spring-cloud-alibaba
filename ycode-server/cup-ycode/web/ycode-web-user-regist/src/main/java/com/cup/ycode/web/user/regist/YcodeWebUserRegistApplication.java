package com.cup.ycode.web.user.regist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.cloud.stream.annotation.EnableBinding;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.cup.ycode")
@EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients(basePackages = "com.cup.ycode")
public class YcodeWebUserRegistApplication {
    public static void main(String[] args) {
        SpringApplication.run(YcodeWebUserRegistApplication.class, args);
    }
}
