package com.learn.index12306.frameworks.starter.cache.core;

/**
 * @author: cyy
 * @create: 2025-03-12 10:11
 * @description: 缓存加载器
 **/
@FunctionalInterface
public interface CacheLoader<T> {

    /**
     * 加载缓存
     */
    T load();
}
