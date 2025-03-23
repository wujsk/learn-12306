package com.learn.index12306.framework.starter.bizs.userservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.learn.index12306.framework.starter.database.base.BaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: cyy
 * @create: 2025-03-23 13:52
 **/
@Data
@TableName("t_user")
@Schema(name = "用户表", description = "用户表")
public class UserDO extends BaseDO {

    /**
     * id
     */
    @Schema(name = "id", description = "id")
    private Long id;

    /**
     * 用户名
     */
    @Schema(name = "用户名", description = "用户名")
    private String username;

    /**
     * 密码
     */
    @Schema(name = "密码", description = "密码")
    private String password;

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

    /**
     * 注销时间戳
     */
    @Schema(name = "注销时间戳", description = "注销时间戳")
    private Long deletionTime;
}
