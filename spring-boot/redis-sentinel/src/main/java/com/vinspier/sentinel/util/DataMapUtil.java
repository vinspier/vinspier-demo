package com.vinspier.sentinel.util;

import java.util.HashMap;

/**
 * 数据库模拟存储
 */
public class DataMapUtil {

    private static HashMap<String,Object> dbMap;

    static {
        dbMap = new HashMap<>();
        for (int i = 0; i < 10; i++){
            dbMap.put("key" + i,i);
        }
    }

    private DataMapUtil() {
    }

    public static Object get(String key){
        return dbMap.get(key);
    }

    public static Object put(String key,Object value){
        Object old = dbMap.get(key);
        dbMap.put(key,value);
        return old;
    }
}
