package com.learn.index12306.framework.starter.bizs.userservice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.index12306.framework.starter.bizs.userservice.dao.entity.UserDO;

/**
 * @author: cyy
 * @create: 2025-03-23 13:52
 * @description: 用户持久层
 **/
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * 注销用户
     * @param userDO 注销用户入参
     */
    void deletionUser(UserDO userDO);
}
