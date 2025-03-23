package com.learn.index12306.framework.starter.bizs.userservice.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: cyy
 * @create: 2025-03-23 14:28
 * @description: 布隆过滤器配置
 **/
@Configuration
@EnableConfigurationProperties(UserRegisterBloomFilterProperties.class)
public class RBloomFilterConfiguration {

    @Bean
    public RBloomFilter<String> userRegisterCachePenetrationBloomFilter(RedissonClient redissonClient, UserRegisterBloomFilterProperties userRegisterBloomFilterProperties) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(userRegisterBloomFilterProperties.getName());
        bloomFilter.tryInit(userRegisterBloomFilterProperties.getExpectedInsertions(),
                userRegisterBloomFilterProperties.getFalseProbability());
        return bloomFilter;
    }
}
