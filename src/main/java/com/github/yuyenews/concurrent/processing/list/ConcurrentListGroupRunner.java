package com.github.yuyenews.concurrent.processing.list;


import java.util.List;

/**
 * 执行器，每次执行一组数据
 * @param <T>
 */
public interface ConcurrentListGroupRunner<T> {

    void run(List<T> t);
}
