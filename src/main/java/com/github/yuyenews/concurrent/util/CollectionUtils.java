package com.github.yuyenews.concurrent.util;

import java.util.Collection;

/**
 * 集合工具类
 */
public class CollectionUtils {

    /**
     * 判断是否为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection){
        if(collection == null || collection.size() == 0){
            return true;
        }
        return false;
    }

    /**
     * 判断是否不是空
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection){
        return isEmpty(collection) == false;
    }
}
