package com.learn.index12306.framework.starter.bizs.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: cyy
 * @create: 2025-03-12 08:10
 * @description: 用户信息实体
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String realName;

    /**
     * token
     */
    private String token;
}
