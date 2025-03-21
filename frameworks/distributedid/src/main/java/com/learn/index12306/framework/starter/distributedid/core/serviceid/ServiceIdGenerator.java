package com.learn.index12306.framework.starter.distributedid.core.serviceid;

import com.learn.index12306.framework.starter.distributedid.core.IdGenerator;
import com.learn.index12306.framework.starter.distributedid.core.snowflake.SnowflakeIdInfo;

/**
 * @author: cyy
 * @create: 2025-03-15 19:47
 * @description: 业务ID生成器
 */
public interface ServiceIdGenerator extends IdGenerator {

    /**
     * 根据 {@param serviceId} 生成雪花算法 ID
     */
    default long nextId(long serviceId) {
        return 0L;
    }

    /**
     * 根据 {@param serviceId} 生成雪花算法 ID
     */
    default long nextId(String serviceId) {
        return 0L;
    }

    /**
     * 根据 {@param serviceId} 生成字符串类型雪花算法 ID
     */
    default String nextIdStr(long serviceId) {
        return null;
    }

    /**
     * 根据 {@param serviceId} 生成字符串类型雪花算法 ID
     */
    default String nextIdStr(String serviceId) {
        return null;
    }

    /**
     * 解析雪花算法
     */
    SnowflakeIdInfo parseSnowflakeId(long snowflakeId);
}
