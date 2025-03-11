package com.learn.index12306.framework.starter.bases.config;

import com.learn.index12306.framework.starter.bases.ApplicationContextHolder;
import com.learn.index12306.framework.starter.bases.init.ApplicationContentPostProcessor;
import com.learn.index12306.framework.starter.bases.safe.FastJsonSafeMode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: cyy
 * @create: 2025-03-11 20:38
 * @description: 应用基础自动装配
 **/
@Configuration
public class ApplicationBaseAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApplicationContentPostProcessor applicationContentPostProcessor(ApplicationContext applicationContext) {
        return new ApplicationContentPostProcessor(applicationContext);
    }

    @Bean
    @ConditionalOnMissingBean
    public FastJsonSafeMode fastJsonSafeMode() {
        return new FastJsonSafeMode();
    }
}
