package com.github.yuyenews.concurrent.processing.map;

import java.util.Map;

/**
 * 执行器，每次执行一组数据
 * @param <K, V>
 */
public interface ConcurrentMapGroupRunner<K, V> {

    void run(Map<K, V> item);
}
