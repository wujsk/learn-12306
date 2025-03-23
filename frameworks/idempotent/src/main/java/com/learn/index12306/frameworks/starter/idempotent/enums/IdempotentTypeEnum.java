package com.learn.index12306.frameworks.starter.idempotent.enums;

/**
 * @author: cyy
 * @create: 2025-03-22 15:05
 * @description: 幂等验证类型枚举
 **/
public enum IdempotentTypeEnum {

    /**
     * 基于 Token 方式验证
     */
    TOKEN,

    /**
     * 基于方法参数方式验证
     */
    PARAM,

    /**
     * 基于 SpEL 表达式方式验证
     */
    SPEL
}
