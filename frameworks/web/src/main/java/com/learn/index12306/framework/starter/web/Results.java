package com.learn.index12306.framework.starter.web;

import com.learn.index12306.framework.starter.convention.errorcode.BaseErrorCode;
import com.learn.index12306.framework.starter.convention.exception.AbstractException;
import com.learn.index12306.framework.starter.convention.result.Result;

/**
 * @author: cyy
 * @create: 2025-03-15 16:42
 * @description: 全局返回对象构造器
 **/
public final class Results {

    /**
     * 构造成功响应
     */
    public static Result<Void> success() {
        return new Result<Void>()
                .setCode(Result.SUCCESS_CODE);
    }

    /**
     * 构造带返回数据的成功响应
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setCode(Result.SUCCESS_CODE)
                .setData(data);
    }

    /**
     * 构建服务端失败响应
     */
    public static Result<Void> failure() {
        return new Result<Void>()
                .setCode(BaseErrorCode.SERVICE_ERROR.code())
                .setMsg(BaseErrorCode.SERVICE_ERROR.message());
    }

    /**
     * 通过 errorCode、errorMessage 构建失败响应
     */
    public static Result<Void> failure(String errorCode, String errorMessage) {
        return new Result<Void>()
                .setCode(errorCode)
                .setMsg(errorMessage);
    }

    /**
     * 通过 {@link AbstractException} 构建失败响应
     */
    public static Result<Void> failure(AbstractException abstractException) {
        return new Result<Void>()
                .setCode(abstractException.getErrorCode())
                .setMsg(abstractException.getErrorMessage());
    }
}
