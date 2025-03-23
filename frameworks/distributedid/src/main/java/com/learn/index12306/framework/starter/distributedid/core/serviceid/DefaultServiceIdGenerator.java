package com.learn.index12306.framework.starter.distributedid.core.serviceid;

import com.learn.index12306.framework.starter.distributedid.core.snowflake.SnowflakeIdInfo;

/**
 * @author: cyy
 * @create: 2025-03-15 19:53
 * @description: 默认业务 ID 生成器
 **/
public class DefaultServiceIdGenerator implements ServiceIdGenerator{

    @Override
    public SnowflakeIdInfo parseSnowflakeId(long snowflakeId) {
        return null;
    }
}
