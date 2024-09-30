package com.github.yuyenews.concurrent.processing.collection;

/**
 * 执行器，每次执行一条数据
 * @param <T>
 */
public interface ConcurrentCollectionRunner<T> {

    void run(T t);
}
