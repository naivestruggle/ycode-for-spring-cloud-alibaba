package com.cup.ycode.service.user.regist;

import com.cup.ycode.service.user.regist.source.SendEmailSource;
import com.cup.ycode.service.user.regist.source.SendEmailVerifyCodeSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
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

/**
 * 启用事件绑定
 */
@EnableBinding({SendEmailSource.class, SendEmailVerifyCodeSource.class})

/**
 * 开启异步调用功能
 */
//@EnableAsync

public class YcodeServiceUserRegistApplication {
    public static void main(String[] args) {
        SpringApplication.run(YcodeServiceUserRegistApplication.class, args);
    }
}
