package com.learn.index12306.framework.starter.web.initialize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;

import static com.learn.index12306.framework.starter.web.config.WebAutoConfiguration.INITIALIZE_PATH;

/**
 * @author: cyy
 * @create: 2025-03-15 19:03
 * @description: 初始化 {@link DispatcherServlet}
 **/
@RestController
@Slf4j(topic = "Initialize DispatcherServlet")
public class InitializeDispatcherServletController {

    @GetMapping(INITIALIZE_PATH)
    public void initializeDispatcherServlet() {
        log.info("Initialized the dispatcherServlet to improve the first response time of the interface...");
    }
}
