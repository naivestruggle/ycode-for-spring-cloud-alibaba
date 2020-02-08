package com.cup.ycode.service.sso.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.cup.ycode")
@EnableDiscoveryClient
@MapperScan(basePackages = "com.cup.ycode.commons.mapper")
public class YcodeServiceSSOAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(YcodeServiceSSOAdminApplication.class, args);
    }
}
