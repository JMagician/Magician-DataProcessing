package com.github.yuyenews.concurrent.job;

/**
 * 任务对象
 */
public class DataTask {

    /**
     * 生产者ID
     */
    private String producerId;

    /**
     * 需要被消费者处理的任务数据
     */
    private Object data;

    public DataTask(String producerId, Object data){
        this.producerId = producerId;
        this.data = data;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
