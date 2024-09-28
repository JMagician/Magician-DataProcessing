package com.github.yuyenews.concurrent.processing.task;

import com.github.yuyenews.concurrent.commons.enums.ConcurrentTaskResultEnum;

/**
 * 回调函数
 */
public interface ConcurrentTaskCall {

    void call(ConcurrentTaskResultEnum result, Throwable throwable);

}
