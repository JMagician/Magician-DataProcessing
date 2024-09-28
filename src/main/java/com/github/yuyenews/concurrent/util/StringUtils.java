package com.github.yuyenews.concurrent.util;

public class StringUtils {

    public static boolean isEmpty(String s){
        if(s == null || s.trim().equals("")){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String s){
        return isEmpty(s) == false;
    }
}
