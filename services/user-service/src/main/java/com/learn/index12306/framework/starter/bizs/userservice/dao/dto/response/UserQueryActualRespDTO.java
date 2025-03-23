package com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: cyy
 * @create: 2025-03-23 12:47
 * @description: 用户查询返回无脱敏参数
 **/
@Data
@Schema(name = "用户查询返回无脱敏参数", description = "用户查询返回无脱敏参数")
public class UserQueryActualRespDTO {

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
     * 国家/地区
     */
    @Schema(name = "国家/地区", description = "国家/地区")
    private String region;

    /**
     * 证件类型
     */
    @Schema(name = "证件类型", description = "证件类型")
    private Integer idType;

    /**
     * 证件号
     */
    @Schema(name = "证件号", description = "证件号")
    private String idCard;

    /**
     * 手机号
     */
    @Schema(name = "手机号", description = "手机号")
    private String phone;

    /**
     * 固定电话
     */
    @Schema(name = "固定电话", description = "固定电话")
    private String telephone;

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
     * 审核状态
     */
    @Schema(name = "审核状态", description = "审核状态")
    private Integer verifyStatus;

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
