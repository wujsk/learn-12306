package com.learn.index12306.framework.starter.bizs.userservice.service.handler.chain.user;

import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserRegisterReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.service.UserService;
import com.learn.index12306.framework.starter.convention.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: cyy
 * @create: 2025-03-23 18:08
 **/
@Component
@RequiredArgsConstructor
public final class UserRegisterCheckDeletionChainHandler implements UserRegisterCreateChainFilter<UserRegisterReqDTO> {

    private final UserService userService;

    @Override
    public void handler(UserRegisterReqDTO requestParam) {
        Integer userDeletionNum = userService.queryUserDeletionNum(requestParam.getIdType(), requestParam.getIdCard());
        if (userDeletionNum >= 5) {
            throw new ClientException("证件号多次注销账号已被加入黑名单");
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
