package com.github.yuyenews.concurrent.processing.list;


import com.github.yuyenews.concurrent.commons.helper.ProcessingHelper;
import com.github.yuyenews.concurrent.util.ListsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 并发处理List里的元素（同步）
 */
public class ConcurrentListSync {

    private Logger logger = LoggerFactory.getLogger(ConcurrentListSync.class);

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 同步执行
     * 默认分成10组，超时时间10分钟
     *
     * @param dataList             数据集
     * @param concurrentListRunner 执行器
     * @param <T>
     */
    public <T> void syncRunner(List<T> dataList,
                               ConcurrentListRunner<T> concurrentListRunner) {
        syncRunner(dataList, concurrentListRunner, 10, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 同步执行
     * 默认超时时间10分钟
     *
     * @param dataList             数据集
     * @param concurrentListRunner 执行器
     * @param groupSize            每组大小，这个大小就决定了会同时开几个线程
     * @param <T>
     */
    public <T> void syncRunner(List<T> dataList,
                               ConcurrentListRunner<T> concurrentListRunner,
                               int groupSize) {
        syncRunner(dataList, concurrentListRunner, groupSize, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 同步执行
     * 默认分成10组，超时时间10分钟
     *
     * @param dataList                  数据集
     * @param concurrentListGroupRunner 执行器
     * @param <T>
     */
    public <T> void syncGroupRunner(List<T> dataList,
                                    ConcurrentListGroupRunner<T> concurrentListGroupRunner) {
        syncGroupRunner(dataList, concurrentListGroupRunner, 10, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 同步执行
     * 默认超时时间10分钟
     *
     * @param dataList                  数据集
     * @param concurrentListGroupRunner 执行器
     * @param groupSize                 每组大小，这个大小就决定了会同时开几个线程
     * @param <T>
     */
    public <T> void syncGroupRunner(List<T> dataList,
                                    ConcurrentListGroupRunner<T> concurrentListGroupRunner,
                                    int groupSize) {
        syncGroupRunner(dataList, concurrentListGroupRunner, groupSize, 10, TimeUnit.MINUTES);
    }

    /**
     * 将一个List分成若干组，每一组分别用一个线程执行
     * 同步执行
     *
     * @param dataList                  数据集
     * @param concurrentListGroupRunner 执行器
     * @param groupSize                 每组多少条，它关系到能分成多少组，而组数就决定了会同时开几个线程
     * @param timeout                   每一组的超时时间，单位由unit参数设置
     * @param unit                      超时时间单位
     * @param <T>
     */
    public <T> void syncGroupRunner(List<T> dataList,
                                    ConcurrentListGroupRunner<T> concurrentListGroupRunner,
                                    int groupSize,
                                    long timeout,
                                    TimeUnit unit) {

        try {
            // 将集合分成若干组，每组元素由调用者指定
            List<List<T>> dataGroup = ListsUtil.partition(dataList, groupSize);

            ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(dataGroup.size(),
                    dataGroup.size(),
                    1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

            CountDownLatch count = new CountDownLatch(dataGroup.size());

            // 给每一组都开一个线程，并发执行
            for (List<T> dataItem : dataGroup) {
                poolExecutor.submit(() -> {
                    try {
                        concurrentListGroupRunner.run(dataItem);
                    } catch (Exception e) {
                        logger.error("ConcurrentProcessing syncGroupRunner error", e);
                    } finally {
                        count.countDown();
                    }
                });
            }

            // 等所有线程执行结束后，或者超时后，再跳出此方法
            ProcessingHelper.runnerAwait(timeout, unit, count, poolExecutor);
        } catch (Exception e) {
            logger.error("ConcurrentProcessingSync syncGroupRunner error", e);
            throw e;
        }
    }

    /**
     * 将一个List分成若干组，排队执行，每组内部并发执行
     * 同步执行
     *
     * @param dataList             数据集
     * @param concurrentListRunner 执行器
     * @param groupSize            每组大小，这个大小就决定了会同时开几个线程
     * @param timeout              每一组的超时时间，单位由unit参数设置
     * @param unit                 超时时间单位
     * @param <T>
     */
    public <T> void syncRunner(List<T> dataList,
                               ConcurrentListRunner<T> concurrentListRunner,
                               int groupSize,
                               long timeout,
                               TimeUnit unit) {

        try {
            // 将集合分成若干组，每组大小由调用者指定
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
                            concurrentListRunner.run(item);
                        } catch (Exception e) {
                            logger.error("ConcurrentProcessing syncRunner error", e);
                        } finally {
                            count.countDown();
                        }
                    });
                }

                // 等所有线程执行结束后，或者超时后，再执行下一组
                ProcessingHelper.runnerAwait(timeout, unit, count, poolExecutor);
            }
        } catch (Exception e) {
            logger.error("ConcurrentProcessingSync syncRunner error", e);
            throw e;
        }
    }
}