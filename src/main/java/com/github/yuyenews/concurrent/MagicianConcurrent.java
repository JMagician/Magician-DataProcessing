package com.github.yuyenews.concurrent;

import com.github.yuyenews.concurrent.job.MagicianJobManager;
import com.github.yuyenews.concurrent.processing.list.ConcurrentListAsync;
import com.github.yuyenews.concurrent.processing.list.ConcurrentListSync;
import com.github.yuyenews.concurrent.processing.task.ConcurrentTaskSync;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 项目入口
 * 从这里，你可以创建本项目的任意对象
 */
public class MagicianConcurrent {

    /**
     * 创建List并发执行对象（同步）
     * @return
     */
    public static ConcurrentListSync getConcurrentListSync() {
        return new ConcurrentListSync();
    }

    /**
     * 创建List并发执行对象（异步）
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 最大空闲时间
     * @param unit 最大空闲时间的单位
     * @param threadFactory 拒绝策略
     * @return
     */
    public static ConcurrentListAsync getConcurrentListAsync(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, ThreadFactory threadFactory) {
        return new ConcurrentListAsync(corePoolSize, maximumPoolSize, keepAliveTime, unit, threadFactory);
    }

    /**
     * 创建List并发执行对象（异步）
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 最大空闲时间
     * @param unit 最大空闲时间的单位
     * @return
     */
    public static ConcurrentListAsync getConcurrentListAsync(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        return new ConcurrentListAsync(corePoolSize, maximumPoolSize, keepAliveTime, unit);
    }

    /**
     * 创建List并发执行对象（异步）
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @return
     */
    public static ConcurrentListAsync getConcurrentListAsync(int corePoolSize, int maximumPoolSize) {
        return new ConcurrentListAsync(corePoolSize, maximumPoolSize, 1, TimeUnit.MINUTES);
    }

    /**
     * 创建任务并发执行对象（同步）
     * @return
     */
    public static ConcurrentTaskSync getConcurrentTaskSync(){
        return new ConcurrentTaskSync();
    }

    /**
     * 创建任务生产者、消费者模型对象
     * @return
     */
    public static MagicianJobManager getJobManager(){
        return new MagicianJobManager();
    }

}
