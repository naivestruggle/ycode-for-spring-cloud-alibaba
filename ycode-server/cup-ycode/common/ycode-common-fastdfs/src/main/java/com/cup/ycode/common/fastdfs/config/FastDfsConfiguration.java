package com.cup.ycode.common.fastdfs.config;

import com.cup.ycode.common.fastdfs.factory.StorageFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Java 配置方式定义 StorageFactory 的 Bean 使其可以被依赖注入
 */
@Configuration
public class FastDfsConfiguration {
    @Bean
    public StorageFactory storageFactory() {
        return new StorageFactory();
    }
}
