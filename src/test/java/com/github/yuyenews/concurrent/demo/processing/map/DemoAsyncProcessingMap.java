package com.github.yuyenews.concurrent.demo.processing.map;

import com.github.yuyenews.concurrent.MagicianConcurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DemoAsyncProcessingMap {

    public static void main(String[] args) {
        Map<String, Object> dataMap = new HashMap<>();

        MagicianConcurrent.getConcurrentMapAsync(1,
                1,
                1,
                TimeUnit.MINUTES).asyncRunner(dataMap, (key, value) -> {

        });

        MagicianConcurrent.getConcurrentMapAsync(1,
                1,
                1,
                TimeUnit.MINUTES).asyncGroupRunner(dataMap, data -> {

        });
    }
}
