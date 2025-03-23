package com.learn.index12306.frameworks.starter.common.enums;

/**
 * @author: cyy
 * @create: 2025-03-21 18:53
 * @description: 删除标记枚举
 **/
public enum DelEnum {

    /**
     * 正常状态
     */
    NORMAL(0, "正常"),

    /**
     * 删除状态
     */
    DELETE(1, "删除");

    private final Integer code;
    private final String msg;

    DelEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return code;
    }

    public String update() {
        return msg;
    }

    public String strCode() {
        return String.valueOf(this.code);
    }

    @Override
    public String toString() {
        return strCode();
    }
}
