package com.learn.index12306.frameworks.starter.database.handler;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.learn.index12306.frameworks.starter.distributedid.util.SnowflakeIdUtil;

/**
 * @author: cyy
 * @create: 2025-03-22 14:11
 **/
public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        return SnowflakeIdUtil.nextId();
    }
}
