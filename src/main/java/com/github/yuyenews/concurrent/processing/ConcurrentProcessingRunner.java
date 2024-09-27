package com.github.yuyenews.concurrent.processing;

/**
 * 执行器，每次执行一条数据
 * @param <T>
 */
public interface ConcurrentProcessingRunner<T> {

    void run(T t);
}
