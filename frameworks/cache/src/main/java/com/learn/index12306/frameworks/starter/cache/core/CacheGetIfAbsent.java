package com.learn.index12306.frameworks.starter.cache.core;

/**
 * @author: cyy
 * @create: 2025-03-12 10:15
 * @description: 缓存查询为空
 **/
@FunctionalInterface
public interface CacheGetIfAbsent<T> {

    /**
     * 如果查询结果为空，执行逻辑
     */
    void execute(T param);
}
