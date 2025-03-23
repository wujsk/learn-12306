package com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: cyy
 * @create: 2025-03-23 12:49
 * @description: 用户注册返回参数
 **/
@Data
@Schema(name = "用户注册返回参数", description = "用户注册返回参数")
public class UserRegisterRespDTO {

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
     * 手机号
     */
    @Schema(name = "手机号", description = "手机号")
    private String phone;
}
