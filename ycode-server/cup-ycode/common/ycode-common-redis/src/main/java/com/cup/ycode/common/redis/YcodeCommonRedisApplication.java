package com.cup.ycode.common.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//注册到nacos   这是 Spring Cloud 底层提供的 注册注解
@EnableDiscoveryClient
public class YcodeCommonRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(YcodeCommonRedisApplication.class, args);
    }
}
