package com.github.yuyenews.concurrent.processing;


import com.github.yuyenews.concurrent.util.ListsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 并发执行（同步）
 */
public class ConcurrentProcessingSync {

    private Logger logger = LoggerFactory.getLogger(ConcurrentProcessingSync.class);

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 同步执行
     * 默认分成10组，超时时间10分钟
     *
     * @param dataList 数据集
     * @param concurrentProcessingRunner 执行器
     * @param <T>
     */
    public <T> void syncRunner(List<T> dataList,
                               ConcurrentProcessingRunner<T> concurrentProcessingRunner) {
        syncRunner(dataList, concurrentProcessingRunner, 10, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 同步执行
     * 默认超时时间10分钟
     *
     * @param dataList 数据集
     * @param concurrentProcessingRunner 执行器
     * @param groupSize 每组大小，这个大小就决定了会同时开几个线程
     * @param <T>
     */
    public <T> void syncRunner(List<T> dataList,
                               ConcurrentProcessingRunner<T> concurrentProcessingRunner,
                               int groupSize) {
        syncRunner(dataList, concurrentProcessingRunner, groupSize, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 同步执行
     * 默认分成10组，超时时间10分钟
     *
     * @param dataList 数据集
     * @param concurrentProcessingGroupRunner 执行器
     * @param <T>
     */
    public <T> void syncGroupRunner(List<T> dataList,
                                    ConcurrentProcessingGroupRunner<T> concurrentProcessingGroupRunner) {
        syncGroupRunner(dataList, concurrentProcessingGroupRunner, 10, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 同步执行
     * 默认超时时间10分钟
     *
     * @param dataList 数据集
     * @param concurrentProcessingGroupRunner 执行器
     * @param groupSize 每组大小，这个大小就决定了会同时开几个线程
     * @param <T>
     */
    public <T> void syncGroupRunner(List<T> dataList,
                                    ConcurrentProcessingGroupRunner<T> concurrentProcessingGroupRunner,
                                    int groupSize) {
        syncGroupRunner(dataList, concurrentProcessingGroupRunner, groupSize, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 同步执行
     *
     * @param dataList 数据集
     * @param concurrentProcessingGroupRunner 执行器
     * @param groupSize 每组多少条，它关系到能分成多少组，而组数就决定了会同时开几个线程
     * @param awaitTime 同步等待多久，单位由unit参数设置
     * @param unit 同步等待多久的时间单位
     * @param <T>
     */
    public <T> void syncGroupRunner(List<T> dataList,
                                    ConcurrentProcessingGroupRunner<T> concurrentProcessingGroupRunner,
                                    int groupSize,
                                    long awaitTime,
                                    TimeUnit unit){

        try {
            // 将集合分成若干组，每组元素由调用者指定
            List<List<T>> dataGroup = ListsUtil.partition(dataList, groupSize);

            ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(dataGroup.size(),
                    dataGroup.size(),
                    1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

            CountDownLatch count = new CountDownLatch(dataGroup.size());

            // 给每一组都开一个线程，并发执行
            for (List<T> dataItem : dataGroup) {
                poolExecutor.submit(()->{
                    try {
                        concurrentProcessingGroupRunner.run(dataItem);
                    } catch (Exception e) {
                        logger.error("ConcurrentProcessing syncGroupRunner error", e);
                    } finally {
                        count.countDown();
                    }
                });
            }

            // 等所有线程执行结束后，再跳出此方法
            // 如果awaitTime <= 0 则直接跳出
            runnerAwait(awaitTime, unit, count, poolExecutor);
        } catch (Exception e){
            logger.error("ConcurrentProcessingSync syncGroupRunner error", e);
            throw e;
        }
    }

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 同步执行
     *
     * @param dataList 数据集
     * @param concurrentProcessingRunner 执行器
     * @param groupSize 每组大小，这个大小就决定了会同时开几个线程
     * @param awaitTime 同步等待多久，单位由unit参数设置
     * @param unit 同步等待多久的时间单位
     * @param <T>
     */
    public <T> void syncRunner(List<T> dataList,
                                      ConcurrentProcessingRunner<T> concurrentProcessingRunner,
                                      int groupSize,
                                      long awaitTime,
                                      TimeUnit unit) {

        try {
            // 将集合分成若干组，每组元素由调用者指定
            List<List<T>> dataGroup = ListsUtil.partition(dataList, groupSize);

            for (List<T> dataItem : dataGroup) {

                ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(dataItem.size(),
                        dataItem.size(),
                        1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

                CountDownLatch count = new CountDownLatch(dataItem.size());

                // 给组内的每一个元素都开一个线程，并发执行
                for (T item : dataItem) {
                    poolExecutor.submit(() -> {
                        try {
                            concurrentProcessingRunner.run(item);
                        } catch (Exception e) {
                            logger.error("ConcurrentProcessing syncRunner error", e);
                        } finally {
                            count.countDown();
                        }
                    });
                }

                // 等所有线程执行结束后，再执行下一组
                // 如果awaitTime <= 0 则直接执行下一组
                runnerAwait(awaitTime, unit, count, poolExecutor);
            }
        } catch (Exception e){
            logger.error("ConcurrentProcessingSync syncRunner error", e);
            throw e;
        }
    }

    /**
     * 等待线程执行结束
     *
     * @param awaitTime
     * @param unit
     * @param count
     * @param poolExecutor
     */
    private void runnerAwait(long awaitTime, TimeUnit unit, CountDownLatch count, ThreadPoolExecutor poolExecutor){
        // 如果没有设置等待时间，则直接跳出，调用方不用等待，也就相当于异步执行了
        if (awaitTime <= 0) {
            return;
        }

        // 如果设置了等待时间，则等待相应的时间
        // 如果在设置的时间内没有执行完，就停止所有线程，并跳出此方法
        try {
            count.await(awaitTime, unit);
            poolExecutor.shutdownNow();
        } catch (Exception e) {
            logger.error("ConcurrentProcessing runner await error", e);
        }
    }
}
