package com.learn.index12306.framework.starter.designpattern.builder;

import java.io.Serializable;

/**
 * @author: cyy
 * @create: 2025-03-13 19:43
 * @description: Builder 模式抽象接口
 **/
public interface Builder<T> extends Serializable {

    /**
     * 构建方法
     * @return 构建后的对象
     */
    T build();
}
