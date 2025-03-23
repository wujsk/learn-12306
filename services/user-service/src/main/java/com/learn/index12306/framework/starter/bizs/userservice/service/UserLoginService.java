package com.learn.index12306.framework.starter.bizs.userservice.service;

import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserDeletionReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserLoginReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserRegisterReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserLoginRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserRegisterRespDTO;

/**
 * @author: cyy
 * @create: 2025-03-23 12:32
 * @description: 用户登录service
 **/
public interface UserLoginService {

    /**
     * 用户登录接口
     *
     * @param requestParam 用户登录入参
     * @return 用户登录返回结果
     */
    UserLoginRespDTO login(UserLoginReqDTO requestParam);

    /**
     * 通过 Token 检查用户是否登录
     *
     * @param accessToken 用户登录 Token 凭证
     * @return 用户是否登录返回结果
     */
    UserLoginRespDTO checkLogin(String accessToken);

    /**
     * 用户退出登录
     *
     * @param accessToken 用户登录 Token 凭证
     */
    void logout(String accessToken);

    /**
     * 用户名是否能够复用
     *
     * @param username 用户名
     * @return 用户名是否能够复用返回结果
     */
    Boolean hasUsername(String username);

    /**
     * 用户注册
     *
     * @param requestParam 用户注册入参
     * @return 用户注册返回结果
     */
    UserRegisterRespDTO register(UserRegisterReqDTO requestParam);

    /**
     * 注销用户
     *
     * @param requestParam 注销用户入参
     */
    void deletion(UserDeletionReqDTO requestParam);
}
