package com.learn.index12306.starter.idempotent.enums;

/**
 * @author: cyy
 * @create: 2025-03-22 15:04
 * @description: 幂等验证场景枚举
 **/
public enum IdempotentSceneEnum {

    /**
     * 基于 RestAPI 场景验证
     */
    RESTAPI,

    /**
     * 基于 MQ 场景验证
     */
    MQ
}
