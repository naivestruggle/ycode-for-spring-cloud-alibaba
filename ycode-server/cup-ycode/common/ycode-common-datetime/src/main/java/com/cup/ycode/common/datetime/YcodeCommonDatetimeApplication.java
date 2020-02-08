package com.cup.ycode.common.datetime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class YcodeCommonDatetimeApplication {
    public static void main(String[] args) {
        SpringApplication.run(YcodeCommonDatetimeApplication.class, args);
    }
}
