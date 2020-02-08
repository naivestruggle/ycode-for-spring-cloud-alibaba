package com.cup.ycode.web.slideshow.admin.config;

import com.cup.ycode.commons.web.interceptor.AdminLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminInterceptor implements WebMvcConfigurer {

    @Bean
    public AdminLoginInterceptor adminLoginInterceptor(){
        return new AdminLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminLoginInterceptor()).addPathPatterns("/**");
    }
}
