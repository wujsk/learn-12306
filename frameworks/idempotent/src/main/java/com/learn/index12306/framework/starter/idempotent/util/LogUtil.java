package com.learn.index12306.framework.starter.idempotent.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: cyy
 * @create: 2025-03-22 15:06
 * @description: 日志工具类
 **/
public class LogUtil {

    public static Logger getLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        signature.getDeclaringType();
        return LoggerFactory.getLogger(signature.getDeclaringType());
    }
}
