package com.github.yuyenews.concurrent.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * list工具类
 */
public class ListsUtil {

    /**
     * 将list分成若干组，每组size条
     *
     * @param list 需要分组的集合
     * @param size 每组分多少条
     * @return
     * @param <T>
     */
    public static <T> List<List<T>> partition(Collection<T> list, int size) {
        if(CollectionUtils.isEmpty(list)){
            throw new NullPointerException("list is null");
        }

        if(size <= 0){
            throw new IllegalArgumentException("size cannot <= 0");
        }
        List<List<T>> array = new ArrayList<>();

        List<T> groupItem = new ArrayList<>();
        for(T t : list){
            if(groupItem.size() < size){
                groupItem.add(t);
                continue;
            }
            array.add(groupItem);

            groupItem = new ArrayList<>();
            groupItem.add(t);
        }

        if(groupItem.size() > 0){
            array.add(groupItem);
        }

        return array;
    }
}
