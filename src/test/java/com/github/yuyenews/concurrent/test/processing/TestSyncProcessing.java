package com.github.yuyenews.concurrent.test.processing;

import com.github.yuyenews.concurrent.MagicianConcurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestSyncProcessing {

    public static void main(String[] args) {
        List<String> dataList = new ArrayList<>();

        MagicianConcurrent.getConcurrentProcessingSync().syncRunner(dataList, data -> {

        }, 10, 1, TimeUnit.MINUTES);
    }
}
