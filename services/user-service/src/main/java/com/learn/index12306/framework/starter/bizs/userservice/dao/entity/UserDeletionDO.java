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
 * @create: 2025-03-23 18:19
 * @description: 用户注销表实体
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_deletion")
@Schema(name = "用户注销表实体", description = "用户注销表实体")
public class UserDeletionDO extends BaseDO {

    /**
     * id
     */
    @Schema(name = "id", description = "id")
    private Long id;

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
}
