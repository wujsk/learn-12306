package com.learn.index12306.framework.starter.bizs.userservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: cyy
 * @create: 2025-03-23 23:06
 * @description: 审核状态
 **/
@AllArgsConstructor
public enum VerifyStatusEnum {

    /**
     * 未审核
     */
    UNREVIEWED(0),

    /**
     * 已审核
     */
    REVIEWED(1);

    @Getter
    private final int code;
}
