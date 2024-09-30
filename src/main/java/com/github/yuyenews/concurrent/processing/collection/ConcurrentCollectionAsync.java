package com.github.yuyenews.concurrent.processing.collection;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 并发处理List里的元素（异步）
 */
public class ConcurrentCollectionAsync {

    /**
     * 异步执行任务的线程池
     */
    private ThreadPoolExecutor poolExecutor;

    public ConcurrentCollectionAsync(int corePoolSize,
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

    public ConcurrentCollectionAsync(int corePoolSize,
                                     int maximumPoolSize,
                                     long keepAliveTime,
                                     TimeUnit unit) {

        poolExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                new LinkedBlockingQueue<>());
    }

    /**
     * 并发执行器
     */
    private ConcurrentCollectionSync concurrentCollectionSync = new ConcurrentCollectionSync();

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 异步执行
     * 默认分成10组，超时时间10分钟
     *
     * @param dataList                   数据集
     * @param concurrentCollectionRunner 执行器
     * @param <T>
     */
    public <T> void asyncRunner(Collection<T> dataList,
                                ConcurrentCollectionRunner<T> concurrentCollectionRunner) {
        asyncRunner(dataList, concurrentCollectionRunner, 10, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 异步执行
     * 默认超时时间10分钟
     *
     * @param dataList                   数据集
     * @param concurrentCollectionRunner 执行器
     * @param <T>
     */
    public <T> void asyncRunner(Collection<T> dataList,
                                ConcurrentCollectionRunner<T> concurrentCollectionRunner,
                                int groupSize) {
        asyncRunner(dataList, concurrentCollectionRunner, groupSize, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 异步执行
     * 默认分成10组，超时时间10分钟
     *
     * @param dataList                        数据集
     * @param concurrentCollectionGroupRunner 执行器
     * @param <T>
     */
    public <T> void asyncGroupRunner(Collection<T> dataList,
                                     ConcurrentCollectionGroupRunner<T> concurrentCollectionGroupRunner) {
        asyncGroupRunner(dataList, concurrentCollectionGroupRunner, 10, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 异步执行
     * 默超时时间10分钟
     *
     * @param dataList                        数据集
     * @param concurrentCollectionGroupRunner 执行器
     * @param <T>
     */
    public <T> void asyncGroupRunner(Collection<T> dataList,
                                     ConcurrentCollectionGroupRunner<T> concurrentCollectionGroupRunner,
                                     int groupSize) {
        asyncGroupRunner(dataList, concurrentCollectionGroupRunner, groupSize, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 异步执行
     *
     * @param dataList                   数据集
     * @param concurrentCollectionRunner 执行器
     * @param groupSize                  每组大小，这个大小就决定了会同时开几个线程
     * @param timeout 每一组的超时时间，单位由unit参数设置
     * @param unit 超时时间单位
     * @param <T>
     */
    public <T> void asyncRunner(Collection<T> dataList,
                                ConcurrentCollectionRunner<T> concurrentCollectionRunner,
                                int groupSize,
                                long timeout,
                                TimeUnit unit) {

        poolExecutor.submit(() -> {
            concurrentCollectionSync.syncRunner(dataList, concurrentCollectionRunner, groupSize, timeout, unit);
        });
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 异步执行
     *
     * @param dataList                        数据集
     * @param concurrentCollectionGroupRunner 执行器
     * @param groupSize                       每组多少条，它关系到能分成多少组，而组数就决定了会同时开几个线程
     * @param timeout                         每一组的超时时间，单位由unit参数设置
     * @param unit                            超时时间单位
     * @param <T>
     */
    public <T> void asyncGroupRunner(Collection<T> dataList,
                                     ConcurrentCollectionGroupRunner<T> concurrentCollectionGroupRunner,
                                     int groupSize,
                                     long timeout,
                                     TimeUnit unit) {

        poolExecutor.submit(() -> {
            concurrentCollectionSync.syncGroupRunner(dataList, concurrentCollectionGroupRunner, groupSize, timeout, unit);
        });
    }

    /**
     * 获取线程池
     * @return
     */
    public ThreadPoolExecutor getPoolExecutor() {
        return poolExecutor;
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        poolExecutor.shutdown();
    }

    /**
     * 立刻关闭线程池
     */
    public void shutdownNow() {
        poolExecutor.shutdownNow();
    }
}
