package com.learn.index12306.frameworks.starter.web.config;

import com.learn.index12306.frameworks.starter.web.GlobalExceptionHandler;
import com.learn.index12306.frameworks.starter.web.initialize.InitializeDispatcherServletController;
import com.learn.index12306.frameworks.starter.web.initialize.InitializeDispatcherServletHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author: cyy
 * @create: 2025-03-15 19:12
 * @description: web 自动配置
 **/
public class WebAutoConfiguration {

    public static final String INITIALIZE_PATH = "/initialize/dispatcher-servlet";

    @Bean
    public InitializeDispatcherServletController initializeDispatcherServletController() {
        return new InitializeDispatcherServletController();
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(5000);
        return factory;
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public InitializeDispatcherServletHandler initializeDispatcherServletHandler(RestTemplate restTemplate, ConfigurableEnvironment configurableEnvironment) {
        return new InitializeDispatcherServletHandler(restTemplate, configurableEnvironment);
    }

    @Bean
    @ConditionalOnMissingBean // 可以确保用户自定义的 GlobalExceptionHandler 优先被使用，而默认实现仅作为备用。
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
