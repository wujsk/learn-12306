package com.learn.index12306.framework.starter.bizs.userservice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.learn.index12306.framework.starter.database.base.BaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: cyy
 * @create: 2025-03-23 15:11
 * @description: 用户名复用表实体
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_reuse")
@Schema(name = "用户名复用表实体", description = "用户名复用表实体")
public final class UserReuseDO extends BaseDO {

    /**
     * 用户名
     */
    @Schema(name = "用户名", description = "用户名")
    private String username;
}
