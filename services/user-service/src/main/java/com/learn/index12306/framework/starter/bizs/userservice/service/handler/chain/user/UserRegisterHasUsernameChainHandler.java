package com.learn.index12306.framework.starter.bizs.userservice.service.handler.chain.user;

import com.learn.index12306.framework.starter.bizs.userservice.common.enums.UserRegisterErrorCodeEnum;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserRegisterReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: cyy
 * @create: 2025-03-23 15:24
 * @description:
 **/
@Component
@RequiredArgsConstructor
public final class UserRegisterHasUsernameChainHandler implements UserRegisterCreateChainFilter<UserRegisterReqDTO>{

    private final UserLoginService userLoginService;

    @Override
    public void handler(UserRegisterReqDTO requestParam) {
        if (!userLoginService.hasUsername(requestParam.getUsername())){
            throw new RuntimeException(UserRegisterErrorCodeEnum.HAS_USERNAME_NOTNULL.name());
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
