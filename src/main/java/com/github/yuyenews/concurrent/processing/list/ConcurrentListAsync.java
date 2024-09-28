package com.github.yuyenews.concurrent.processing.list;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 并发处理List里的元素（异步）
 */
public class ConcurrentListAsync {

    /**
     * 异步执行任务的线程池
     */
    private ThreadPoolExecutor poolExecutor;

    public ConcurrentListAsync(int corePoolSize,
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

    public ConcurrentListAsync(int corePoolSize,
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
    private ConcurrentListSync concurrentListSync = new ConcurrentListSync();

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 异步执行
     * 默认分成10组，超时时间10分钟
     *
     * @param dataList                   数据集
     * @param concurrentListRunner 执行器
     * @param <T>
     */
    public <T> void asyncRunner(List<T> dataList,
                                ConcurrentListRunner<T> concurrentListRunner) {
        asyncRunner(dataList, concurrentListRunner, 10, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 异步执行
     * 默认超时时间10分钟
     *
     * @param dataList                   数据集
     * @param concurrentListRunner 执行器
     * @param <T>
     */
    public <T> void asyncRunner(List<T> dataList,
                                ConcurrentListRunner<T> concurrentListRunner,
                                int groupSize) {
        asyncRunner(dataList, concurrentListRunner, groupSize, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 异步执行
     * 默认分成10组，超时时间10分钟
     *
     * @param dataList                        数据集
     * @param concurrentListGroupRunner 执行器
     * @param <T>
     */
    public <T> void asyncGroupRunner(List<T> dataList,
                                     ConcurrentListGroupRunner<T> concurrentListGroupRunner) {
        asyncGroupRunner(dataList, concurrentListGroupRunner, 10, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 异步执行
     * 默超时时间10分钟
     *
     * @param dataList                        数据集
     * @param concurrentListGroupRunner 执行器
     * @param <T>
     */
    public <T> void asyncGroupRunner(List<T> dataList,
                                     ConcurrentListGroupRunner<T> concurrentListGroupRunner,
                                     int groupSize) {
        asyncGroupRunner(dataList, concurrentListGroupRunner, groupSize, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 异步执行
     *
     * @param dataList                   数据集
     * @param concurrentListRunner 执行器
     * @param groupSize                  每组大小，这个大小就决定了会同时开几个线程
     * @param timeout 每一组的超时时间，单位由unit参数设置
     * @param unit 超时时间单位
     * @param <T>
     */
    public <T> void asyncRunner(List<T> dataList,
                                ConcurrentListRunner<T> concurrentListRunner,
                                int groupSize,
                                long timeout,
                                TimeUnit unit) {

        poolExecutor.submit(() -> {
            concurrentListSync.syncRunner(dataList, concurrentListRunner, groupSize, timeout, unit);
        });
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 异步执行
     *
     * @param dataList                        数据集
     * @param concurrentListGroupRunner 执行器
     * @param groupSize                       每组多少条，它关系到能分成多少组，而组数就决定了会同时开几个线程
     * @param timeout                         每一组的超时时间，单位由unit参数设置
     * @param unit                            超时时间单位
     * @param <T>
     */
    public <T> void asyncGroupRunner(List<T> dataList,
                                     ConcurrentListGroupRunner<T> concurrentListGroupRunner,
                                     int groupSize,
                                     long timeout,
                                     TimeUnit unit) {

        poolExecutor.submit(() -> {
            concurrentListSync.syncGroupRunner(dataList, concurrentListGroupRunner, groupSize, timeout, unit);
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
