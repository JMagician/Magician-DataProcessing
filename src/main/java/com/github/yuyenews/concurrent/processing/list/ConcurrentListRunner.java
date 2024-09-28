package com.github.yuyenews.concurrent.processing.list;

/**
 * 执行器，每次执行一条数据
 * @param <T>
 */
public interface ConcurrentListRunner<T> {

    void run(T t);
}
