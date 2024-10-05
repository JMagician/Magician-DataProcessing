package com.github.yuyenews.data.processing.concurrent.task;

import com.github.yuyenews.data.processing.commons.enums.ConcurrentTaskResultEnum;

/**
 * 回调函数
 */
public interface ConcurrentTaskCall {

    void call(ConcurrentTaskResultEnum result, Throwable throwable);

}
