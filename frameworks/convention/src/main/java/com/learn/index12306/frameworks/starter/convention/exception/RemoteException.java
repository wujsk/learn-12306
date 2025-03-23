package com.learn.index12306.frameworks.starter.convention.exception;

import com.learn.index12306.frameworks.starter.convention.errorcode.BaseErrorCode;
import com.learn.index12306.frameworks.starter.convention.errorcode.IErrorCode;

/**
 * @author: cyy
 * @create: 2025-03-13 17:25
 * @description: 远程服务调用异常
 **/
public class RemoteException extends AbstractException{

    public RemoteException(IErrorCode errorCode) {
        this(null, null, errorCode);
    }

    public RemoteException(String message) {
        this(message, null, BaseErrorCode.REMOTE_ERROR);
    }

    public RemoteException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public RemoteException(String message, Throwable throwable, IErrorCode errorCode) {
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
