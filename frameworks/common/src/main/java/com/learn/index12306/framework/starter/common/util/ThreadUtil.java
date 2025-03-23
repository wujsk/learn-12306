package com.learn.index12306.framework.starter.common.util;

import lombok.SneakyThrows;

/**
 * @author: cyy
 * @create: 2025-03-21 19:06
 * @description: 线程池工具类
 **/
public class ThreadUtil {

    /**
     * 睡眠当前线程指定时间 {@param millis}
     * @param millis 睡眠时间，单位毫秒
     */
    @SneakyThrows(InterruptedException.class)
    public static void sleep(long millis) {
        Thread.sleep(millis);
    }
}
