package com.learn.index12306.framework.starter.bizs.userservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: cyy
 * @create: 2025-03-23 12:02
 * @description: 用户服务启动类
 **/
@SpringBootApplication
@MapperScan("com.learn.index12306.framework.starter.bizs.userservice.dao.mapper")
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
