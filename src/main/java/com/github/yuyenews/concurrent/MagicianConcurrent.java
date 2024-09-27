package com.github.yuyenews.concurrent;

import com.github.yuyenews.concurrent.job.MagicianJobManager;
import com.github.yuyenews.concurrent.processing.ConcurrentProcessingAsync;
import com.github.yuyenews.concurrent.processing.ConcurrentProcessingSync;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 项目入口
 * 从这里，你可以创建本项目的任意对象
 */
public class MagicianConcurrent {

    /**
     * 创建并发执行对象（同步）
     * @return
     */
    public static ConcurrentProcessingSync getConcurrentProcessingSync() {
        return new ConcurrentProcessingSync();
    }

    /**
     * 创建并发执行对象（异步）
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 最大空闲时间
     * @param unit 最大空闲时间的单位
     * @param threadFactory 拒绝策略
     * @return
     */
    public static ConcurrentProcessingAsync getConcurrentProcessingAsync(int corePoolSize,
                                                                         int maximumPoolSize,
                                                                         long keepAliveTime,
                                                                         TimeUnit unit,
                                                                         ThreadFactory threadFactory) {
        return new ConcurrentProcessingAsync(corePoolSize, maximumPoolSize, keepAliveTime, unit, threadFactory);
    }

    /**
     * 创建任务生产者、消费者模型对象
     * @return
     */
    public static MagicianJobManager getJobManager(){
        return new MagicianJobManager();
    }

}
