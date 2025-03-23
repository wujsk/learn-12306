package com.learn.index12306.framework.starter.bizs.userservice.service.handler.chain.user;

import com.learn.index12306.framework.starter.bizs.userservice.common.enums.UserChainMarkEnum;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserRegisterReqDTO;
import com.learn.index12306.framework.starter.designpattern.chain.AbstractChainHandler;

/**
 * @author: cyy
 * @create: 2025-03-23 15:18
 * @description: 用户注册责任链过滤器
 **/
public interface UserRegisterCreateChainFilter<T extends UserRegisterReqDTO> extends AbstractChainHandler<UserRegisterReqDTO> {

    @Override
    default String mark() {
        return UserChainMarkEnum.USER_REGISTER_FILTER.name();
    }
}
