package com.github.yuyenews.concurrent.test.job;

import com.github.yuyenews.concurrent.job.MagicianProducer;

import java.util.UUID;

public class TestProducerOne extends MagicianProducer {

    private static String id = UUID.randomUUID().toString();

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void producer() {
        for (int i = 0; i < 20; i++) {
            try {
                this.publish("000" + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
