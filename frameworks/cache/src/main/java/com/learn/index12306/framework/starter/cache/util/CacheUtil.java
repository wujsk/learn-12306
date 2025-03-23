package com.learn.index12306.framework.starter.cache.util;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author: cyy
 * @create: 2025-03-12 15:04
 * @description: 缓存工具类
 **/
public class CacheUtil {

    private static final String SPLICING_OPERATOR = "_";

    /**
     * 判断结果是否为空或空的字符串
     * @param cacheVal 缓存值
     * @return true or false
     */
    public static boolean isNullOrEmpty(Object cacheVal) {
        return cacheVal == null || (cacheVal instanceof String && Strings.isNullOrEmpty((String) cacheVal));
    }

    public static String buildKey(String... keys) {
        Stream.of(keys)
                .forEach(key ->
                        Optional.ofNullable(Strings.emptyToNull(key))
                                .orElseThrow(() -> new RuntimeException("构建缓存 key 不允许为空"))
                );
        return Joiner.on(SPLICING_OPERATOR).join(keys);
    }
}
