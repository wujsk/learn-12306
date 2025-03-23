package com.learn.index12306.framework.starter.bizs.userservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.learn.index12306.framework.starter.database.base.BaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: cyy
 * @create: 2025-03-23 15:10
 * @description: 用户手机号实体对象
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_user_phone")
@Schema(name = "用户手机号实体对象", description = "用户手机号实体对象")
public class UserPhoneDO extends BaseDO {

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
     * 手机号
     */
    @Schema(name = "手机号", description = "手机号")
    private String phone;

    /**
     * 注销时间戳
     */
    @Schema(name = "注销时间戳", description = "注销时间戳")
    private Long deletionTime;
}
