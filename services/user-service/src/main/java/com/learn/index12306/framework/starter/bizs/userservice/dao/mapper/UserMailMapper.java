package com.learn.index12306.framework.starter.bizs.userservice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.index12306.framework.starter.bizs.userservice.dao.entity.UserMailDO;

/**
 * @author: cyy
 * @create: 2025-03-23 18:49
 * @description: 用户邮箱表持久层
 **/
public interface UserMailMapper extends BaseMapper<UserMailDO> {

    /**
     * 注销用户
     * @param userMailDO 注销用户入参
     */
    void deletionUser(UserMailDO userMailDO);
}
