package com.github.yuyenews.concurrent.test;

import com.github.yuyenews.concurrent.job.MagicianConsumer;

public class TestConsumerOne extends MagicianConsumer {
    @Override
    public long getExecFrequencyLimit() {
        return 0;
    }

    @Override
    public void doRunner(Object data) {
        try {
            Thread.sleep(1000);

//            System.out.println("dataType: " + data + "_" + data.getClass().getSimpleName());
//            System.out.println("taskCount: " + this.getTaskCount());
//            System.out.println("data:" + data);
//            System.out.println("------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
