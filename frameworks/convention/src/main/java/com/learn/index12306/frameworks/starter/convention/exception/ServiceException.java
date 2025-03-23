package com.learn.index12306.frameworks.starter.convention.exception;

import com.learn.index12306.frameworks.starter.convention.errorcode.BaseErrorCode;
import com.learn.index12306.frameworks.starter.convention.errorcode.IErrorCode;

/**
 * @author: cyy
 * @create: 2025-03-13 17:25
 * @description: 服务端异常
 **/
public class ServiceException extends AbstractException{

    public ServiceException(IErrorCode errorCode) {
        this(null, null, errorCode);
    }

    public ServiceException(String message) {
        this(message, null, BaseErrorCode.SERVICE_ERROR);
    }

    public ServiceException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ServiceException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
