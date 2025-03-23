package com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: cyy
 * @create: 2025-03-23 12:33
 * @description: 用户登录请求参数
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "用户登录请求参数", description = "用户登录请求参数")
public class UserLoginReqDTO {

    /**
     * 用户名
     */
    @Schema(name = "用户名", description = "用户名")
    private String usernameOrMailOrPhone;

    /**
     * 密码
     */
    @Schema(name = "密码", description = "密码")
    private String password;
}
