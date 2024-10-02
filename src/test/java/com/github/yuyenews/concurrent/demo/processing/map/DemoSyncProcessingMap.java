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
            // 这里是每一组Map
            for(Map.Entry<String, Object> entry : data.entrySet()){
                // 这里可以拿到Map里的每一个元素
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
            }
        });
    }
}
