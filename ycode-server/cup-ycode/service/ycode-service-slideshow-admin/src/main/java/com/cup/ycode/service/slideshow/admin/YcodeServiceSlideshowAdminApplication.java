package com.cup.ycode.service.slideshow.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 标志这是一个 SpringBoot 程序
 * 修改扫描包
 */
@SpringBootApplication(scanBasePackages = "com.cup.ycode")

/**
 * 启用 服务注册与发现
 */
@EnableDiscoveryClient

/**
 * 扫描 mapper
 */
@MapperScan(basePackages = "com.cup.ycode.commons.mapper")


public class YcodeServiceSlideshowAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(YcodeServiceSlideshowAdminApplication.class, args);
    }
}
