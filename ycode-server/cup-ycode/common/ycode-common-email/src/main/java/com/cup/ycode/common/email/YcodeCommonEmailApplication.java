package com.cup.ycode.common.email;

import com.cup.ycode.common.email.sink.SendEmail;
import com.cup.ycode.common.email.sink.SendEmailVerifyCode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.cup.ycode")
@EnableBinding({SendEmail.class, SendEmailVerifyCode.class})
@EnableAsync
public class YcodeCommonEmailApplication {
    public static void main(String[] args) {
        SpringApplication.run(YcodeCommonEmailApplication.class, args);
    }
}
