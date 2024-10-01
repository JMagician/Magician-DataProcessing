package com.github.yuyenews.concurrent.demo.pac;

import com.github.yuyenews.concurrent.pac.MagicianProducer;

import java.util.UUID;

public class DemoProducerOne extends MagicianProducer {

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
