package com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: cyy
 * @create: 2025-03-23 12:45
 * @description: 用户修改请求参数
 **/
@Data
@Schema(name = "用户修改请求参数", description = "用户修改请求参数")
public class UserUpdateReqDTO {

    /**
     * 用户ID
     */
    @Schema(name = "用户iD", description = "用户iD")
    private String id;

    /**
     * 用户名
     */
    @Schema(name = "用户名", description = "用户名")
    private String username;

    /**
     * 邮箱
     */
    @Schema(name = "邮箱", description = "邮箱")
    private String mail;

    /**
     * 旅客类型
     */
    @Schema(name = "旅客类型", description = "旅客类型")
    private Integer userType;

    /**
     * 邮编
     */
    @Schema(name = "邮编", description = "邮编")
    private String postCode;

    /**
     * 地址
     */
    @Schema(name = "地址", description = "地址")
    private String address;
}
