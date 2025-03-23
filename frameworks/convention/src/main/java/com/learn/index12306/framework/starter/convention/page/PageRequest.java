package com.learn.index12306.framework.starter.convention.page;

import lombok.Data;

/**
 * @author: cyy
 * @create: 2025-03-13 19:24
 * @description: 分页请求对象
 **/
@Data
public class PageRequest {

    /**
     * 当前页
     */
    private Long current = 1L;

    /**
     * 每页条数
     */
    private Long size = 10L;
}
