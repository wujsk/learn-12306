package com.learn.index12306.frameworks.starter.cache.core;

/**
 * @author: cyy
 * @create: 2025-03-12 10:13
 * @description: 缓存过滤
 **/
@FunctionalInterface
public interface CacheGetFilter<T> {

    /**
     * 缓存过滤
     * @param param 输入参数
     * @return {@code true} 如果输入参数匹配，否则 {@link Boolean#TRUE}
     */
    boolean filter(T param);
}
