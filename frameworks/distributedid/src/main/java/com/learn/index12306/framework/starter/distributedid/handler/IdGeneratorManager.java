package com.learn.index12306.framework.starter.distributedid.handler;

import com.learn.index12306.framework.starter.distributedid.core.IdGenerator;
import com.learn.index12306.framework.starter.distributedid.core.serviceid.DefaultServiceIdGenerator;
import com.learn.index12306.framework.starter.distributedid.core.serviceid.ServiceIdGenerator;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: cyy
 * @create: 2025-03-15 20:38
 * @description: ID 生成器管理
 **/
public final class IdGeneratorManager {

    private static Map<String, IdGenerator> MANAGER = new ConcurrentHashMap<>();

    static {
        MANAGER.put("default", new DefaultServiceIdGenerator());
    }

    /**
     * 注册ID生成器
     */
    public static void registerIdGenerator(@NonNull String resource, @NonNull IdGenerator idGenerator) {
        IdGenerator actual = MANAGER.get(resource);
        if (actual != null) {
            return;
        }
        MANAGER.put(resource, idGenerator);
    }

    /**
     * 根据 {@param resource} 获取 ID 生成器
     */
    public static ServiceIdGenerator getIdGenerator(String resource) {
        return Optional.ofNullable(MANAGER.get(resource))
                .map(idGenerator -> (ServiceIdGenerator) idGenerator)
                .orElse(null);
    }

    /**
     * 获取默认 ID 生成器 {@link DefaultServiceIdGenerator}
     */
    public static ServiceIdGenerator getDefaultServiceIdGenerator() {
        return Optional.ofNullable(MANAGER.get("default"))
                .map(idGenerator -> (ServiceIdGenerator) idGenerator)
                .orElse(null);
    }
}
