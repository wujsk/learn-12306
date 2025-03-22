package com.learn.index12306.starter.common.enums;

/**
 * @author: cyy
 * @create: 2025-03-21 19:03
 * @description: 状态枚举
 **/
public enum StatusEnum {

    SUCCESS(0, "成功"),

    FAIL(1, "失败");

    public final Integer code;

    public final String msg;

    StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
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
