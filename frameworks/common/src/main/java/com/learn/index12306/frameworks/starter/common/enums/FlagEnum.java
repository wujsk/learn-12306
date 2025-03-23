package com.learn.index12306.frameworks.starter.common.enums;

/**
 * @author: cyy
 * @create: 2025-03-21 18:59
 * @description: 标识枚举，非 {@link Boolean#TRUE} 即 {@link Boolean#FALSE}
 **/
public enum FlagEnum {

    /**
     * 状态
     */
    FALSE(0),

    /**
     * 删除状态
     */
    TRUE(1);

    private final Integer code;

    FlagEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String strCode() {
        return String.valueOf(this.code);
    }

    @Override
    public String toString() {
        return strCode();
    }
}
