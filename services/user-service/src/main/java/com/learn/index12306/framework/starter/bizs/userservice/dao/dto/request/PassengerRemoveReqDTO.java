package com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: cyy
 * @create: 2025-03-23 12:44
 * @description: 乘车人移除请求参数
 **/
@Data
@Schema(name = "乘车人移除请求参数", description = "乘车人移除请求参数")
public class PassengerRemoveReqDTO {

    /**
     * 乘车人id
     */
    @Schema(name = "乘车人id", description = "乘车人id")
    private String id;
}
