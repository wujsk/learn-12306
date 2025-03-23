package com.learn.index12306.frameworks.starter.log.core;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.learn.index12306.frameworks.starter.log.annotation.ILog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author: cyy
 * @create: 2025-03-15 15:36
 * @description: {@link ILog} 日志打印 AOP 切面
 **/
@Aspect
public class ILogPrintAspect {

    /**
     * 打印类或方法上的 {@link ILog}
     * within 代表类上有注解
     * around 代表方法上有注解
     */
    @Around("@within(com.learn.index12306.frameworks.starter.log.annotation.ILog) || @annotation(com.learn.index12306.frameworks.starter.log.annotation.ILog)")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String beginTime = DateUtil.now();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Logger log = LoggerFactory.getLogger(methodSignature.getDeclaringType());
        Object result = null;
        try {
            // 执行方法
            result = joinPoint.proceed();
        } finally {
            // 封装日志对象
            // 因为重载方法，所以根据方法名称和参数类型获取方法
            Method targetMethod = joinPoint.getClass().getDeclaredMethod(methodSignature.getName(), methodSignature.getParameterTypes());
            // 获取注解，注解可能在类上，也可能在方法上
            ILog iLog = Optional.ofNullable(targetMethod.getAnnotation(ILog.class)).orElse(joinPoint.getTarget().getClass().getAnnotation(ILog.class));
            if (iLog != null) {
                ILogPrintDTO logPrintDTO = new ILogPrintDTO();
                logPrintDTO.setBeginTime(beginTime);
                if (iLog.input()) {
                    logPrintDTO.setInputParams(buildInput(joinPoint));
                }
                if (iLog.output()) {
                    logPrintDTO.setOutputParam(result);
                }
                String methodType = "", requestURI = "";
                try {
                    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    assert servletRequestAttributes != null;
                    HttpServletRequest request = servletRequestAttributes.getRequest();
                    requestURI = request.getRequestURI();
                    methodType = request.getMethod();
                } catch (Exception ignored) {
                }
                log.info("[{}] {}, executeTime: {}ms, info: {}", methodType, requestURI, System.currentTimeMillis() - startTime, JSON.toJSONString(logPrintDTO));
            }
        }
        // 将结果传递会调用方
        return result;
    }

    /**
     * 进行处理，避免参数过长
     */
    private Object[] buildInput(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object[] printArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse) {
                continue;
            } else if (args[i] instanceof byte[]) {
                printArgs[i] = "byte array";
            } else if (args[i] instanceof MultipartFile) {
                printArgs[i] = "file";
            } else {
                printArgs[i] = args[i];
            }
        }
        return printArgs;
    }
}
