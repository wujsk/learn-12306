package com.learn.index12306.framework.starter.bizs.userservice.util;

import static com.learn.index12306.framework.starter.bizs.userservice.common.constants.Index12306Constant.USER_REGISTER_REUSE_SHARDING_COUNT;

/**
 * @author: cyy
 * @create: 2025-03-23 14:41
 * @description: 用户名可复用工具类
 **/
public final class UserReuseUtil {

    /**
     * 计算分片位置
     */
    public static int hashShardingIdx(String username) {
        return Math.abs(username.hashCode() % USER_REGISTER_REUSE_SHARDING_COUNT);
    }
}
