package com.learn.index12306.frameworks.starter.bases.safe;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author: cyy
 * @create: 2025-03-11 20:20
 **/
public class FastJsonSafeMode implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        // 设置 FastJson2 的安全模式为 true
        System.setProperty("fastjson2.parser.safeMode", "true");
    }
}
