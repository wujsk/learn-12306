package com.learn.index12306.framework.starter.distributedid.config;

import com.learn.index12306.framework.starter.bases.ApplicationContextHolder;
import com.learn.index12306.framework.starter.distributedid.core.snowflake.LocalRedisWorkIdChoose;
import com.learn.index12306.framework.starter.distributedid.core.snowflake.RandomWorkIdChoose;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author: cyy
 * @create: 2025-03-21 18:44
 **/
@Import(ApplicationContextHolder.class)
public class DistributedIdAutoConfiguration {

    /**
     * 本地 Redis 构建雪花 WorkId 选择器
     */
    @Bean
    @ConditionalOnProperty("spring.data.redis.host")
    public LocalRedisWorkIdChoose localRedisWorkIdChoose(){
        return new LocalRedisWorkIdChoose();
    }

    /**
     * 随机数构建雪花 WorkId 选择器。如果项目未使用 Redis，使用该选择器
     */
    @Bean
    @ConditionalOnMissingBean(LocalRedisWorkIdChoose.class)
    public RandomWorkIdChoose randomWorkIdChoose(){
        return new RandomWorkIdChoose();
    }
}
