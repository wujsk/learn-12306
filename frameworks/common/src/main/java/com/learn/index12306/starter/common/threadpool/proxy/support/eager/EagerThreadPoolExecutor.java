package com.learn.index12306.starter.common.threadpool.proxy.support.eager;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: cyy
 * @create: 2025-03-21 21:51
 * @description: 快速消费线程池
 **/
public class EagerThreadPoolExecutor extends ThreadPoolExecutor {
    public EagerThreadPoolExecutor(int corePoolSize,
                                   int maximumPoolSize,
                                   long keepAliveTime,
                                   TimeUnit unit,
                                   TaskQueue<Runnable> workQueue,
                                   ThreadFactory threadFactory,
                                   RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    private final AtomicInteger submittedTaskCount = new AtomicInteger(0);

    public int getSubmittedTaskCount() {
        return submittedTaskCount.get();
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        // 任务执行完毕，计数器减一
        submittedTaskCount.decrementAndGet();
    }

    @Override
    public void execute(Runnable command) {
        // 计数器加一
        submittedTaskCount.getAndIncrement();
        try {
            // 执行任务
            super.execute(command);
        } catch (RejectedExecutionException e) {
            // 获取任务队列
            TaskQueue<Runnable> queue = (TaskQueue<Runnable>) super.getQueue();
            try {
                if (!queue.retryOffer(command, 0, TimeUnit.MILLISECONDS)) {
                    // 任务加入队列失败
                    submittedTaskCount.decrementAndGet();
                    throw new RejectedExecutionException("Queue capacity is full.", e);
                }
            } catch (InterruptedException ex) {
                // 任务加入队列失败
                submittedTaskCount.decrementAndGet();
                throw new RejectedExecutionException(ex);
            }
        } catch (Exception e) {
            // 捕获异常
            submittedTaskCount.decrementAndGet();
            throw e;
        }
    }
}
