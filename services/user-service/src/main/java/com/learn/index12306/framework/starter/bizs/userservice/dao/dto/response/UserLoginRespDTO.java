package com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: cyy
 * @create: 2025-03-23 12:46
 * @description: 用户登录返回参数
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "用户登录返回参数", description = "用户登录返回参数")
public class UserLoginRespDTO {

    /**
     * 用户 ID
     */
    @Schema(name = "用户ID", description = "用户ID")
    private String userId;

    /**
     * 用户名
     */
    @Schema(name = "用户名", description = "用户名")
    private String username;

    /**
     * 真实姓名
     */
    @Schema(name = "真实姓名", description = "真实姓名")
    private String realName;

    /**
     * Token
     */
    @Schema(name = "Token", description = "Token")
    private String accessToken;
}
