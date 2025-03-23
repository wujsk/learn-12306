package com.learn.index12306.framework.starter.common.threadpool.build;

import com.learn.index12306.framework.starter.designpattern.builder.Builder;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: cyy
 * @create: 2025-03-21 20:08
 * @description: 线程工厂 {@link ThreadFactory} 构建器, 构建者模式
 **/
public final class ThreadFactoryBuilder implements Builder<ThreadFactory> {

    private static final long serialVersionUID = 1L;

    private ThreadFactoryBuilder() {
    }

    private ThreadFactory backingThreadFactory;

    private String namePrefix;

    private Boolean daemon;

    private Integer priority;

    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public ThreadFactoryBuilder threadFactory(ThreadFactory backingThreadFactory) {
        this.backingThreadFactory = backingThreadFactory;
        return this;
    }

    public ThreadFactoryBuilder prefix(String namePrefix) {
        this.namePrefix = namePrefix;
        return this;
    }

    public ThreadFactoryBuilder daemon(Boolean daemon) {
        daemon = daemon;
        return this;
    }

    public ThreadFactoryBuilder priority(int priority) {
        if (priority < Thread.MIN_PRIORITY) {
            throw new IllegalArgumentException(String.format("Thread priority ({}) must be >= {}", priority, Thread.MIN_PRIORITY));
        }
        if (priority > Thread.MAX_PRIORITY) {
            throw new IllegalArgumentException(String.format("Thread priority ({}) must be <= {}", priority, Thread.MAX_PRIORITY));
        }
        this.priority = priority;
        return this;
    }

    public void uncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    public static ThreadFactoryBuilder builder() {
        return new ThreadFactoryBuilder();
    }

    @Override
    public ThreadFactory build() {
        return build(this);
    }

    public ThreadFactory build(ThreadFactoryBuilder threadFactoryBuilder) {
        // 使用 final 确保这些变量在初始化后不会被修改
        final ThreadFactory threadFactory = (null == threadFactoryBuilder.backingThreadFactory)
                ? Executors.defaultThreadFactory()
                : backingThreadFactory;
        final String namePrefix = threadFactoryBuilder.namePrefix;
        final Boolean daemon = threadFactoryBuilder.daemon;
        final Integer priority = threadFactoryBuilder.priority;
        final Thread.UncaughtExceptionHandler handler = threadFactoryBuilder.uncaughtExceptionHandler;
        final AtomicLong count = (null == namePrefix) ? null : new AtomicLong();
        return r -> {
            Thread thread = threadFactory.newThread(r);
            if (null != namePrefix) {
                thread.setName(namePrefix + "_" + count.getAndIncrement());
            }
            if (null != daemon) {
                thread.setDaemon(daemon);
            }
            if (null != priority) {
                thread.setPriority(priority);
            }
            if (null != handler) {
                thread.setUncaughtExceptionHandler(handler);
            }
            return thread;
        };
    }
}
