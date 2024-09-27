package com.github.yuyenews.concurrent.processing;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 并发执行（异步）
 */
public class ConcurrentProcessingAsync {

    /**
     * 异步执行任务的线程池
     */
    private ThreadPoolExecutor poolExecutor;

    public ConcurrentProcessingAsync(int corePoolSize,
                                     int maximumPoolSize,
                                     long keepAliveTime,
                                     TimeUnit unit,
                                     ThreadFactory threadFactory) {

        poolExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                new LinkedBlockingQueue<>(),
                threadFactory);
    }

    /**
     * 并发执行器
     */
    private ConcurrentProcessingSync concurrentProcessingSync = new ConcurrentProcessingSync();

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 异步执行
     * 默认分成10组，超时时间10分钟
     *
     * @param dataList                   数据集
     * @param concurrentProcessingRunner 执行器
     * @param <T>
     */
    public <T> void asyncRunner(List<T> dataList,
                                ConcurrentProcessingRunner<T> concurrentProcessingRunner) {
        asyncRunner(dataList, concurrentProcessingRunner, 10, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 异步执行
     * 默认超时时间10分钟
     *
     * @param dataList                   数据集
     * @param concurrentProcessingRunner 执行器
     * @param <T>
     */
    public <T> void asyncRunner(List<T> dataList,
                                ConcurrentProcessingRunner<T> concurrentProcessingRunner,
                                int groupSize) {
        asyncRunner(dataList, concurrentProcessingRunner, groupSize, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 异步执行
     * 默认分成10组，超时时间10分钟
     *
     * @param dataList                        数据集
     * @param concurrentProcessingGroupRunner 执行器
     * @param <T>
     */
    public <T> void asyncGroupRunner(List<T> dataList,
                                     ConcurrentProcessingGroupRunner<T> concurrentProcessingGroupRunner) {
        asyncGroupRunner(dataList, concurrentProcessingGroupRunner, 10, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 异步执行
     * 默超时时间10分钟
     *
     * @param dataList                        数据集
     * @param concurrentProcessingGroupRunner 执行器
     * @param <T>
     */
    public <T> void asyncGroupRunner(List<T> dataList,
                                     ConcurrentProcessingGroupRunner<T> concurrentProcessingGroupRunner,
                                     int groupSize) {
        asyncGroupRunner(dataList, concurrentProcessingGroupRunner, groupSize, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 异步执行
     *
     * @param dataList                   数据集
     * @param concurrentProcessingRunner 执行器
     * @param groupSize                  每组大小，这个大小就决定了会同时开几个线程
     * @param <T>
     */
    public <T> void asyncRunner(List<T> dataList,
                                ConcurrentProcessingRunner<T> concurrentProcessingRunner,
                                int groupSize,
                                long awaitTime,
                                TimeUnit unit) {

        poolExecutor.submit(() -> {
            concurrentProcessingSync.syncRunner(dataList, concurrentProcessingRunner, groupSize, awaitTime, unit);
        });
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 异步执行
     *
     * @param dataList                        数据集
     * @param concurrentProcessingGroupRunner 执行器
     * @param groupSize                       每组多少条，它关系到能分成多少组，而组数就决定了会同时开几个线程
     * @param <T>
     */
    public <T> void asyncGroupRunner(List<T> dataList,
                                     ConcurrentProcessingGroupRunner<T> concurrentProcessingGroupRunner,
                                     int groupSize,
                                     long awaitTime,
                                     TimeUnit unit) {

        poolExecutor.submit(() -> {
            concurrentProcessingSync.syncGroupRunner(dataList, concurrentProcessingGroupRunner, groupSize, awaitTime, unit);
        });
    }

    /**
     * 关闭所有线程
     */
    public void shutdown() {
        poolExecutor.shutdown();
    }

    /**
     * 立刻关闭所有线程
     */
    public void shutdownNow() {
        poolExecutor.shutdownNow();
    }
}
