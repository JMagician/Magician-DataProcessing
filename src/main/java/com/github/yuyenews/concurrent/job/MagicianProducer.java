package com.github.yuyenews.concurrent.job;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 生产者线程
 */
public abstract class MagicianProducer implements Runnable {

    private Logger logger = LoggerFactory.getLogger(MagicianProducer.class);

    /**
     * ID
     */
    private String id;

    /**
     * 它所对应的消费者
     */
    private List<MagicianConsumer> consumers;

    /**
     * 他所对应的空闲消费者
     */
    private List<MagicianConsumer> freeConsumers;

    /**
     * 是否要停止
     */
    private boolean shutdown;

    public MagicianProducer(){
        this.shutdown = false;
        this.id = getId();
        if(this.id == null || "".equals(this.id.trim())){
            this.id = UUID.randomUUID().toString();
        }
    }

    /**
     * 添加消费者
     * @param consumers
     */
    public void addConsumer(List<MagicianConsumer> consumers){
        this.consumers = consumers;
        for(MagicianConsumer consumer : this.consumers){
            consumer.initProducerTaskCount(id);
        }
    }

    /**
     * 给消费者投喂任务
     * @param t
     */
    public void publish(Object t){
        if(freeConsumers == null || freeConsumers.size() == 0){
            throw new NullPointerException("");
        }
        for(MagicianConsumer consumer : freeConsumers){
            consumer.addTask(new TaskData(id, t));
        }
    }

    /**
     * 生产数据
     */
    @Override
    public void run(){
        freeConsumers = consumers;

        while (shutdown == false){
            try {
                // 生产数据，并投喂给消费者
                producer();

                // 一轮投喂结束后，检测有没有空闲消费者，如果没有就阻塞在这，直到有空闲消费者出现
                await();
            } catch (Exception e){
                logger.error("DataProducer run error, id:{}", id, e);
            }
        }
    }

    /**
     * 检测有没有空闲消费者，如果没有就阻塞在这，直到有空闲消费者出现
     */
    private void await(){
        while (true) {
            try {
                // 检测有没有空闲消费者
                freeConsumers = new ArrayList<>();
                for (MagicianConsumer consumer : consumers) {
                    if (consumer.isPending(id)) {
                        freeConsumers.add(consumer);
                    }
                }

                // 如果有就结束
                if (freeConsumers.size() > 0) {
                    break;
                }

                // 没有就阻塞100毫秒，然后再检测一次
                Thread.sleep(100);
                logger.info("DataProducer execProducer awaiting, id:{}......", id);
            } catch (Exception e){
                logger.error("DataProducer execProducer await error, id:{}", id, e);
            }
        }
    }

    /**
     * 关闭数据生产
     */
    public void shutDownNow(){
        this.shutdown = true;
    }

    /**
     * 获取ID
     * @return
     */
    public abstract String getId();

    /**
     * 生产数据
     */
    public abstract void producer();
}
