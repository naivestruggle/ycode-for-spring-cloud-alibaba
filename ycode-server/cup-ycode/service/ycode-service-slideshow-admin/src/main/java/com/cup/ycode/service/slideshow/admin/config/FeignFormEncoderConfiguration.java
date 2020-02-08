package com.cup.ycode.service.slideshow.admin.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignFormEncoderConfiguration {
    @Bean
    public Encoder feignFormEncoder(){
        return new SpringFormEncoder();
    }
}
