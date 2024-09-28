package com.github.yuyenews.concurrent.test.processing.task;

import com.github.yuyenews.concurrent.MagicianConcurrent;
import com.github.yuyenews.concurrent.commons.enums.ConcurrentTaskResultEnum;

import java.util.concurrent.TimeUnit;

public class TestConcurrentTaskSync {

    public static void main(String[] args) {
        MagicianConcurrent.getConcurrentTaskSync()
                .setTimeout(1000) // 超时时间
                .setTimeUnit(TimeUnit.MILLISECONDS) // 超时时间的单位
                .add(() -> { // 添加一个任务

                    // 在这里可以写上任务的业务逻辑

                }, (result, e) -> {
                    // 此任务处理后的回调
                    if(result.equals(ConcurrentTaskResultEnum.FAIL)){
                        // 任务失败，此时e里面有详细的异常信息
                    } else if(result.equals(ConcurrentTaskResultEnum.SUCCESS)) {
                        // 任务成功，此时e是空的
                    }
                })
                .add(() -> { // 添加一个任务

                    // 在这里可以写上任务的业务逻辑

                }, (result, e) -> {
                    // 此任务处理后的回调
                    if(result.equals(ConcurrentTaskResultEnum.FAIL)){
                        // 任务失败，此时e里面有详细的异常信息
                    } else if(result.equals(ConcurrentTaskResultEnum.SUCCESS)) {
                        // 任务成功，此时e是空的
                    }
                })
                .start();
    }
}
