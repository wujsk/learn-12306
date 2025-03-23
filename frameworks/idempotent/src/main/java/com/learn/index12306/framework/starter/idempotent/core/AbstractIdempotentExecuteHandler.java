package com.learn.index12306.framework.starter.idempotent.core;

import com.learn.index12306.framework.starter.idempotent.annotation.Idempotent;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author: cyy
 * @create: 2025-03-22 15:35
 * @description: 抽象幂等执行处理器
 **/
public abstract class AbstractIdempotentExecuteHandler implements IdempotentExecuteHandler{

    /**
     * 构建幂等验证过程中所需要的参数包装器
     * @param joinPoint AOP 方法处理
     * @return 幂等参数包装器
     */
    protected abstract IdempotentParamWrapper buildWrapper(ProceedingJoinPoint joinPoint);

    @Override
    public void execute(ProceedingJoinPoint joinPoint, Idempotent idempotent) {
        IdempotentParamWrapper wrapper = buildWrapper(joinPoint).setIdempotent(idempotent);
        handler(wrapper);
    }
}
