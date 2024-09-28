package com.github.yuyenews.concurrent.job;

import com.github.yuyenews.concurrent.commons.enums.JobEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务管理器
 */
public class MagicianJobManager {

    /**
     * 消费者集合
     */
    private List<MagicianConsumer> consumers = new ArrayList<>();

    /**
     * 生产者集合
     */
    private List<MagicianProducer> producers = new ArrayList<>();

    /**
     * 生产者线程池
     */
    private ThreadPoolExecutor producersPoolExecutor;

    /**
     * 消费者线程池
     */
    private ThreadPoolExecutor consumersPoolExecutor;

    /**
     * 添加一个消费者
     * @param consumer
     * @return
     */
    public MagicianJobManager addConsumer(MagicianConsumer consumer) {
        consumers.add(consumer);
        return this;
    }

    /**
     * 添加一个生产者
     * @param producer
     * @return
     */
    public MagicianJobManager addProducer(MagicianProducer producer) {
        producers.add(producer);
        return this;
    }

    /**
     * 执行
     * @throws Exception
     */
    public void start() throws Exception {
        // 初始化生产者线程池
        producersPoolExecutor = new ThreadPoolExecutor(consumers.size(),
                consumers.size(),
                1,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>());

        // 初始化消费者线程池
        consumersPoolExecutor = new ThreadPoolExecutor(consumers.size(),
                consumers.size(),
                1,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>());

        // 开启消费者线程
        for (MagicianConsumer consumer : consumers) {
            consumersPoolExecutor.submit(consumer);
        }

        // 开启生产者线程
        Map<String, Object> idMap = new HashMap<>();
        for (MagicianProducer producer : producers) {

            // 这里做了一个校验，防止出现ID相同的生产者
            if(idMap.get(producer.getId()) != null){
                throw new Exception("duplicate producer id exists");
            }
            idMap.put(producer.getId(), true);

            producer.addConsumer(consumers);
            producersPoolExecutor.submit(producer);
        }
    }

    /**
     * 关闭生产者或消费者线程池
     * @param jobEnum
     */
    public void shutdown(JobEnum jobEnum) {
        if (JobEnum.CONSUMER.equals(jobEnum)) {
            consumersPoolExecutor.shutdown();
        } else if (JobEnum.PRODUCER.equals(jobEnum)) {
            producersPoolExecutor.shutdown();
        } else if (JobEnum.ALL.equals(jobEnum)) {
            producersPoolExecutor.shutdown();
            consumersPoolExecutor.shutdown();
        }
    }

    /**
     * 立刻关闭生产者或消费者线程池
     * @param jobEnum
     */
    public void shutdownNow(JobEnum jobEnum) {
        if (JobEnum.CONSUMER.equals(jobEnum)) {
            consumersPoolExecutor.shutdownNow();
        } else if (JobEnum.PRODUCER.equals(jobEnum)) {
            producersPoolExecutor.shutdownNow();
        } else if (JobEnum.ALL.equals(jobEnum)) {
            producersPoolExecutor.shutdownNow();
            consumersPoolExecutor.shutdownNow();
        }
    }
}
