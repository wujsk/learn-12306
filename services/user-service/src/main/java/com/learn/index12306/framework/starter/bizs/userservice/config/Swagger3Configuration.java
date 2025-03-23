package com.learn.index12306.framework.starter.bizs.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: cyy
 * @create: 2025-03-23 12:37
 * @description: swagger3配置
 **/
@Configuration
public class Swagger3Configuration {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("用户相关接口")
                .description("12006用户相关接口")
                .version("0.0.1"));
    }
}
