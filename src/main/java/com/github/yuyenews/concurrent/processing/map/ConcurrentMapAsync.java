package com.github.yuyenews.concurrent.processing.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 并发处理Map里的元素（异步）
 */
public class ConcurrentMapAsync {

    private Logger logger = LoggerFactory.getLogger(ConcurrentMapAsync.class);

    /**
     * 异步执行任务的线程池
     */
    private ThreadPoolExecutor poolExecutor;

    public ConcurrentMapAsync(int corePoolSize,
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

    public ConcurrentMapAsync(int corePoolSize,
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
    private ConcurrentMapSync concurrentMapSync = new ConcurrentMapSync();

    /**
     * 将一个Map分成若干组，排队执行，每组内部并发执行
     * 同步执行
     * 默认分成10组，超时时间10分钟
     *
     * @param dataMap             数据集
     * @param concurrentMapRunner 执行器
     * @param <K, V>
     */
    public <K, V> void asyncRunner(Map<K, V> dataMap,
                                  ConcurrentMapRunner<K, V> concurrentMapRunner) {
        asyncRunner(dataMap, concurrentMapRunner, 10, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个Map分成若干组，排队执行，每组内部并发执行
     * 同步执行
     * 默认超时时间10分钟
     *
     * @param dataMap             数据集
     * @param concurrentMapRunner 执行器
     * @param groupSize            每组大小，这个大小就决定了会同时开几个线程
     * @param <K, V>
     */
    public <K, V> void asyncRunner(Map<K, V> dataMap,
                                  ConcurrentMapRunner<K, V> concurrentMapRunner,
                                  int groupSize) {
        asyncRunner(dataMap, concurrentMapRunner, groupSize, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个Map分成若干组，每一组分别用一个线程执行
     * 同步执行
     * 默认分成10组，超时时间10分钟
     *
     * @param dataMap                  数据集
     * @param concurrentMapGroupRunner 执行器
     * @param <K, V>
     */
    public <K, V> void asyncGroupRunner(Map<K, V> dataMap,
                                       ConcurrentMapGroupRunner<K, V> concurrentMapGroupRunner) {
        asyncGroupRunner(dataMap, concurrentMapGroupRunner, 10, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个Map分成若干组，每一组分别用一个线程执行
     * 同步执行
     * 默认超时时间10分钟
     *
     * @param dataMap                  数据集
     * @param concurrentMapGroupRunner 执行器
     * @param groupSize                 每组大小，这个大小就决定了会同时开几个线程
     * @param <K, V>
     */
    public <K, V> void asyncGroupRunner(Map<K, V> dataMap,
                                       ConcurrentMapGroupRunner<K, V> concurrentMapGroupRunner,
                                       int groupSize) {
        asyncGroupRunner(dataMap, concurrentMapGroupRunner, groupSize, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个Map分成若干组，每一组分别用一个线程执行
     * 同步执行
     *
     * @param dataMap                  数据集
     * @param concurrentMapGroupRunner 执行器
     * @param groupSize                 每组多少条，它关系到能分成多少组，而组数就决定了会同时开几个线程
     * @param timeout                   每一组的超时时间，单位由unit参数设置
     * @param unit                      超时时间单位
     * @param <K, V>
     */
    public <K, V> void asyncGroupRunner(Map<K, V> dataMap,
                                       ConcurrentMapGroupRunner<K, V> concurrentMapGroupRunner,
                                       int groupSize,
                                       long timeout,
                                       TimeUnit unit) {
        poolExecutor.submit(()->{
            concurrentMapSync.syncGroupRunner(dataMap, concurrentMapGroupRunner, groupSize, timeout, unit);
        });
    }


    /**
     * 将一个Map分成若干组，排队执行，每组内部并发执行
     * 同步执行
     *
     * @param dataMap             数据集
     * @param concurrentMapRunner 执行器
     * @param groupSize            每组大小，这个大小就决定了会同时开几个线程
     * @param timeout              每一组的超时时间，单位由unit参数设置
     * @param unit                 超时时间单位
     * @param <K, V>
     */
    public <K, V> void asyncRunner(Map<K, V> dataMap,
                                  ConcurrentMapRunner<K, V> concurrentMapRunner,
                                  int groupSize,
                                  long timeout,
                                  TimeUnit unit) {
        poolExecutor.submit(()->{
            concurrentMapSync.syncRunner(dataMap, concurrentMapRunner, groupSize, timeout, unit);
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
