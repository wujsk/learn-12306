package com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.learn.index12306.framework.starter.bizs.userservice.serializer.IdCardDesensitizationSerializer;
import com.learn.index12306.framework.starter.bizs.userservice.serializer.PhoneDesensitizationSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author: cyy
 * @create: 2025-03-23 21:01
 * @description: 乘车人返回参数
 **/
@Schema(name = "乘车人返回参数", description = "乘车人返回参数")
@Data
@Accessors(chain = true)
public class PassengerRespDTO {

    /**
     * 乘车人id
     */
    @Schema(name = "乘车人id", description = "乘车人id")
    private String id;

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
     * 证件类型
     */
    @Schema(name = "证件类型", description = "证件类型")
    private Integer idType;

    /**
     * 证件号码
     */
    @Schema(name = "证件号码", description = "证件号码")
    @JsonSerialize(using = IdCardDesensitizationSerializer.class)
    private String idCard;

    /**
     * 真实证件号码
     */
    @Schema(name = "真实证件号码", description = "真实证件号码")
    private String actualIdCard;

    /**
     * 优惠类型
     */
    @Schema(name = "优惠类型", description = "优惠类型")
    private Integer discountType;

    /**
     * 手机号
     */
    @Schema(name = "手机号", description = "手机号")
    @JsonSerialize(using = PhoneDesensitizationSerializer.class)
    private String phone;

    /**
     * 真实手机号
     */
    @Schema(name = "真实手机号", description = "真实手机号")
    private String actualPhone;

    /**
     * 添加日期
     */
    @Schema(name = "添加日期", description = "添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime createDate;

    /**
     * 审核状态
     */
    private Integer verifyStatus;
}
