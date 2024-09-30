package com.github.yuyenews.concurrent.processing.map;

/**
 * 执行器，每次执行一条数据
 */
public interface ConcurrentMapRunner<K, V> {

    void run(K key, V value);
}
