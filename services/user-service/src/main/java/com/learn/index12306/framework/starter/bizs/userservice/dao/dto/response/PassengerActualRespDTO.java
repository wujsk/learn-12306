package com.learn.index12306.framework.starter.bizs.userservice.dao.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author: cyy
 * @create: 2025-03-23 21:02
 * @description: 乘车人真实返回参数，不包含脱敏信息
 **/
@Data
@Accessors(chain = true)
@Schema(name = "乘车人真实返回参数", description = "乘车人真实返回参数")
public class PassengerActualRespDTO {

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

    /**
     * 添加日期
     */
    @Schema(name = "添加日期", description = "添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime createDate;

    /**
     * 审核状态
     */
    @Schema(name = "审核状态", description = "审核状态")
    private Integer verifyStatus;
}
