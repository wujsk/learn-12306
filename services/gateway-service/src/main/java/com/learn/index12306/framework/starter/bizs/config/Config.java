package com.learn.index12306.framework.starter.bizs.config;

import lombok.Data;

import java.util.List;

/**
 * @author: cyy
 * @create: 2025-03-24 08:12
 * @description: 过滤器配置
 **/
@Data
public class Config {

    /**
     * 黑名单前置路径
     */
    private List<String> blackPathPre;
}
