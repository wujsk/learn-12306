package com.learn.index12306.frameworks.starter.distributedid.core.snowflake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: cyy
 * @create: 2025-03-15 20:08
 * @description: WorkId 包装器
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkIdWrapper {

    private Long workId;

    private Long dataCenterId;
}
