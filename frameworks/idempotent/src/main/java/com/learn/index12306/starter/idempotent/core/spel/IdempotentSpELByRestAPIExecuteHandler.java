package com.learn.index12306.starter.idempotent.core.spel;

import com.learn.index12306.framework.starter.convention.exception.ClientException;
import com.learn.index12306.starter.idempotent.annotation.Idempotent;
import com.learn.index12306.starter.idempotent.core.AbstractIdempotentExecuteHandler;
import com.learn.index12306.starter.idempotent.core.IdempotentAspect;
import com.learn.index12306.starter.idempotent.core.IdempotentContext;
import com.learn.index12306.starter.idempotent.core.IdempotentParamWrapper;
import com.learn.index12306.starter.idempotent.util.SpELUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * @author: cyy
 * @create: 2025-03-22 17:11
 * @description: 基于 SpEL 方法验证请求幂等性，适用于 RestAPI 场景
 **/
@RequiredArgsConstructor
public final class IdempotentSpELByRestAPIExecuteHandler extends AbstractIdempotentExecuteHandler implements IdempotentSpELService {

    private final RedissonClient redissonClient;

    private final static String LOCK = "lock:spEL:restAPI";

    @SneakyThrows
    @Override
    protected IdempotentParamWrapper buildWrapper(ProceedingJoinPoint joinPoint) {
        Idempotent idempotent = IdempotentAspect.getIdempotent(joinPoint);
        String key = (String) SpELUtil.parseKey(idempotent.key(), ((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getArgs());
        return IdempotentParamWrapper.builder().lockKey(key).joinPoint(joinPoint).build();
    }

    @Override
    public void handler(IdempotentParamWrapper wrapper) {
        String uniqueKey = wrapper.getIdempotent().uniqueKeyPrefix() + wrapper.getLockKey();
        RLock lock = redissonClient.getLock(uniqueKey);
        if (!lock.tryLock()) {
            throw new ClientException(wrapper.getIdempotent().message());
        }
        IdempotentContext.put(LOCK, lock);
    }

    @Override
    public void postProcessing() {
        RLock lock = null;
        try {
            lock = (RLock) IdempotentContext.getKey(LOCK);
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
    }

    @Override
    public void exceptionProcessing() {
        RLock lock = null;
        try {
            lock = (RLock) IdempotentContext.getKey(LOCK);
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
    }
}
