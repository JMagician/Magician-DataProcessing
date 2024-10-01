package com.github.yuyenews.concurrent.demo.pac;

import com.github.yuyenews.concurrent.pac.MagicianProducer;

import java.util.ArrayList;
import java.util.UUID;

public class DemoProducerTwo extends MagicianProducer {

    @Override
    public void producer() {
        for (int i = 0; i < 20; i++) {
            try {
                this.publish(new ArrayList<>());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
