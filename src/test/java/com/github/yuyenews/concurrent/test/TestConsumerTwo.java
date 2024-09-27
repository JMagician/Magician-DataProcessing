package com.github.yuyenews.concurrent.test;

import com.github.yuyenews.concurrent.job.MagicianConsumer;

public class TestConsumerTwo extends MagicianConsumer {

    @Override
    public long getExecFrequencyLimit() {
        return 500;
    }

    @Override
    public void doRunner(Object data) {
        try {
            Thread.sleep(1000);

//            System.out.println("dataType2: " + data + "_" + data.getClass().getSimpleName());
//            System.out.println("taskCount2: " + this.getTaskCount());
//            System.out.println("data2:" + data);
//            System.out.println("2------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
