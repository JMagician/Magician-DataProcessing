package com.github.yuyenews.data.processing.demo.concurrent.map;

import com.github.yuyenews.data.processing.MagicianDataProcessing;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DemoAsyncProcessingMap {

    public static void main(String[] args) {
        Map<String, Object> dataMap = new HashMap<>();

        MagicianDataProcessing.getConcurrentMapAsync(1,
                1,
                1,
                TimeUnit.MINUTES).asyncRunner(dataMap, (key, value) -> {

        });

        MagicianDataProcessing.getConcurrentMapAsync(1,
                1,
                1,
                TimeUnit.MINUTES).asyncGroupRunner(dataMap, data -> {

        });
    }
}
