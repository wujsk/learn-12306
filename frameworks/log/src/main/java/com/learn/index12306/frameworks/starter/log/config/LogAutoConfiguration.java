package com.learn.index12306.frameworks.starter.log.config;

import com.learn.index12306.frameworks.starter.log.core.ILogPrintAspect;
import org.springframework.context.annotation.Bean;

/**
 * @author: cyy
 * @create: 2025-03-15 16:13
 * @description: 日志自动装配类
 **/
public class LogAutoConfiguration {

    @Bean
    public ILogPrintAspect iLogPrintAspect() {
        return new ILogPrintAspect();
    }
}
