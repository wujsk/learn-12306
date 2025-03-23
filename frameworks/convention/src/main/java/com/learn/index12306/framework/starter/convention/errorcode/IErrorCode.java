package com.learn.index12306.framework.starter.convention.errorcode;


/**
 * @author: cyy
 * @create: 2025-03-13 16:57
 * @description: 平台错误码
 */
public interface IErrorCode {

    /**
     * 错误码
     */
    String code();

    /**
     * 错误信息
     */
    String message();
}
