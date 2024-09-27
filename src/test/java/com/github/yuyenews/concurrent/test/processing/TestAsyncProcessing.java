package com.github.yuyenews.concurrent.test.processing;

import com.github.yuyenews.concurrent.MagicianConcurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestAsyncProcessing {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        MagicianConcurrent.getConcurrentProcessingAsync(1, 10, 1, TimeUnit.MINUTES)
                .asyncRunner(list, data -> {

                }, 10, 1, TimeUnit.MINUTES);
    }
}
