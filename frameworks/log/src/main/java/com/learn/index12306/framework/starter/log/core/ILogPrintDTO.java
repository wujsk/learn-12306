package com.learn.index12306.framework.starter.log.core;

import lombok.Data;

/**
 * @author: cyy
 * @create: 2025-03-15 15:34
 * @description: ILog 日志打印实体
 **/
@Data
public class ILogPrintDTO {

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 请求参数
     */
    private Object[] inputParams;

    /**
     * 返回参数
     */
    private Object outputParam;
}
