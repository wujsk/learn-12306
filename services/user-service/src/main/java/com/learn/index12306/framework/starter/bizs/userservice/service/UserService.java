package com.learn.index12306.framework.starter.bizs.userservice.service;

import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request.UserUpdateReqDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserQueryActualRespDTO;
import com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response.UserQueryRespDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author: cyy
 * @create: 2025-03-23 18:09
 * @description: 用户信息接口层
 **/
public interface UserService {

    /**
     * 根据用户 ID 查询用户信息
     *
     * @param userId 用户 ID
     * @return 用户详细信息
     */
    UserQueryRespDTO queryUserByUserId(@NotEmpty String userId);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户详细信息
     */
    UserQueryRespDTO queryUserByUsername(@NotEmpty String username);

    /**
     * 根据用户名查询用户无脱敏信息
     *
     * @param username 用户名
     * @return 用户详细信息
     */
    UserQueryActualRespDTO queryActualUserByUsername(@NotEmpty String username);

    /**
     * 根据证件类型和证件号查询注销次数
     *
     * @param idType 证件类型
     * @param idCard 证件号
     * @return 注销次数
     */
    Integer queryUserDeletionNum(@NotNull(message = "证件类型不能为空") Integer idType, @NotEmpty(message = "身份证号不能为空") String idCard);

    /**
     * 根据用户 ID 修改用户信息
     *
     * @param requestParam 用户信息入参
     */
    void update(UserUpdateReqDTO requestParam);
}
