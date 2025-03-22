package com.learn.index12306.starter.common.threadpool.proxy;

import com.learn.index12306.starter.common.threadpool.build.ThreadPoolBuilder;
import com.learn.index12306.starter.common.util.ThreadUtil;

import java.lang.reflect.Proxy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: cyy
 * @create: 2025-03-21 20:38
 **/
public final class RejectProxyUtil {

    /**
     * 创建拒绝策略代理类
     * @param rejectedExecutionHandler 真正的线程池拒绝策略执行器
     * @param rejectedNum              拒绝策略执行统计器
     * @return 代理拒绝策略
     */
    public static RejectedExecutionHandler createProxy(RejectedExecutionHandler rejectedExecutionHandler, AtomicLong rejectedNum) {
        return (RejectedExecutionHandler) Proxy.newProxyInstance(
                rejectedExecutionHandler.getClass().getClassLoader(),
                new Class[]{RejectedExecutionHandler.class},
                new RejectProxyInvocationHandler(rejectedExecutionHandler, rejectedNum)
        );
    }

    /**
     * 测试线程池拒绝策略动态代理程序
     */
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(1, 3, 1024, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));
        ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();
        AtomicLong rejectedNum = new AtomicLong();
        RejectedExecutionHandler proxyRejectedExecutionHandler = createProxy(abortPolicy, rejectedNum);
        threadPoolExecutor.setRejectedExecutionHandler(proxyRejectedExecutionHandler);
        for (int i = 0; i < 5; i++) {
            try {
                threadPoolExecutor.execute(() -> ThreadUtil.sleep(100000L));
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        System.out.println("================ 线程池拒绝策略执行次数: " + rejectedNum.get());
    }
}
