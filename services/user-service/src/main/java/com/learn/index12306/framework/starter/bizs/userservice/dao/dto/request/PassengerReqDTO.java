package com.learn.index12306.framework.starter.bizs.userservice.dao.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: cyy
 * @create: 2025-03-23 12:42
 * @description: 乘车人添加&修改请求参数
 **/
@Data
@Schema(name = "乘车人添加&修改请求参数", description = "乘车人添加&修改请求参数")
public class PassengerReqDTO {

    /**
     * 乘车人id
     */
    @Schema(name = "乘车人id", description = "乘车人id")
    private String id;

    /**
     * 真实姓名
     */
    @Schema(name = "真实姓名", description = "真实姓名")
    private String realName;

    /**
     * 证件类型
     */
    @Schema(name = "证件类型", description = "证件类型")
    private Integer idType;

    /**
     * 证件号码
     */
    @Schema(name = "证件号码", description = "证件号码")
    private String idCard;

    /**
     * 优惠类型
     */
    @Schema(name = "优惠类型", description = "优惠类型")
    private Integer discountType;

    /**
     * 手机号
     */
    @Schema(name = "手机号", description = "手机号")
    private String phone;
}
