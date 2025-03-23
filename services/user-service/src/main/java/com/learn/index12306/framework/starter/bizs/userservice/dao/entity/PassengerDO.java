package com.learn.index12306.framework.starter.bizs.userservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.learn.index12306.framework.starter.database.base.BaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: cyy
 * @create: 2025-03-23 20:57
 **/
@Data
@TableName("t_passenger")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "乘客信息", description = "乘客信息")
public class PassengerDO extends BaseDO {

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
    private LocalDateTime createDate;

    /**
     * 审核状态
     */
    @Schema(name = "审核状态", description = "审核状态")
    private Integer verifyStatus;
}
