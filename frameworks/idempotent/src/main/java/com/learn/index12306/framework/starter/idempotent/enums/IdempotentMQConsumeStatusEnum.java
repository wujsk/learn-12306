package com.learn.index12306.framework.starter.idempotent.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author: cyy
 * @create: 2025-03-22 15:02
 * @description: 幂等 MQ 消费状态枚举
 **/
public enum IdempotentMQConsumeStatusEnum {

    /**
     * 消费中
     */
    CONSUMING("0"),

    /**
     * 已消费
     */
    CONSUMED("1");

    @Getter
    private final String code;

    IdempotentMQConsumeStatusEnum(String code) {
        this.code = code;
    }

    /**
     * 如果消费状态等于消费中，返回失败
     *
     * @param consumeStatus 消费状态
     * @return 是否消费失败
     */
    public static boolean isError(String consumeStatus) {
        return Objects.equals(CONSUMING.code, consumeStatus);
    }
}
