package com.learn.index12306.framework.starter.bizs.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: cyy
 * @create: 2025-03-23 14:26
 * @description: 用户注册布隆过滤器配置
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = UserRegisterBloomFilterProperties.PREFIX)
public class UserRegisterBloomFilterProperties {

    public static final String PREFIX = "framework.cache.redis.bloom-filter.user-register";

    /**
     * 用户注册布隆过滤器实例名称
     */
    private String name = "user_register_cache_penetration_bloom_filter";

    /**
     * 每个元素的预期插入量
     */
    private Long expectedInsertions = 10000L;

    /**
     * 预期错误概率
     */
    private Double falseProbability = 0.03D;
}
