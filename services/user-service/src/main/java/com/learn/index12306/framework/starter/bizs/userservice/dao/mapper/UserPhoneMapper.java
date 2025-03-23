package com.learn.index12306.framework.starter.bizs.userservice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.index12306.framework.starter.bizs.userservice.dao.entity.UserPhoneDO;

/**
 * @author: cyy
 * @create: 2025-03-23 18:49
 * @description: 用户手机号表持久层
 **/
public interface UserPhoneMapper extends BaseMapper<UserPhoneDO> {

    /**
     * 注销用户
     * @param userPhoneDO 注销用户入参
     */
    void deletionUser(UserPhoneDO userPhoneDO);
}
