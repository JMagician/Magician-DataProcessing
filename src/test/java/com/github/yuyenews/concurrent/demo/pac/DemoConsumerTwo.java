package com.github.yuyenews.concurrent.demo.pac;

import com.github.yuyenews.concurrent.pac.MagicianConsumer;

public class DemoConsumerTwo extends MagicianConsumer {

    @Override
    public void doRunner(Object data) {
        try {
            Thread.sleep(1000);

            System.out.println("dataType2: " + data + "_" + data.getClass().getSimpleName());
            System.out.println("taskCount2: " + this.getTaskCount());
            System.out.println("data2:" + data);
            System.out.println("2------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
