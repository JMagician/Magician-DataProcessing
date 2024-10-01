package com.github.yuyenews.concurrent.demo.processing.map;

import com.github.yuyenews.concurrent.MagicianConcurrent;

import java.util.HashMap;
import java.util.Map;

public class DemoSyncProcessingMap {

    public static void main(String[] args) {
        Map<String, Object> dataMap = new HashMap<>();

        MagicianConcurrent.getConcurrentMapSync().syncRunner(dataMap, (key, value) -> {

        });

        MagicianConcurrent.getConcurrentMapSync().syncGroupRunner(dataMap, data -> {

        });
    }
}
