package com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: cyy
 * @create: 2025-03-23 12:41
 * @description: 用户删除请求参数
 **/
@Data
@Schema(name = "用户删除请求参数", description = "用户删除请求参数")
public class UserDeletionReqDTO {

    /**
     * 用户名
     */
    @Schema(name = "用户名", description = "用户名")
    private String username;
}
