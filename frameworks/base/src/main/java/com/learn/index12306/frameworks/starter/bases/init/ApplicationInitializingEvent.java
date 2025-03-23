package com.learn.index12306.frameworks.starter.bases.init;

import org.springframework.context.ApplicationEvent;

/**
 * @author: cyy
 * @create: 2025-03-11 20:35
 * @description: 应用初始化事件
 *               <p> 规约事件，通过此事件可以查看业务系统所有初始化行为
 **/
public class ApplicationInitializingEvent extends ApplicationEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public ApplicationInitializingEvent(Object source) {
        super(source);
    }
}
