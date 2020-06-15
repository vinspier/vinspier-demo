package com.example.redis.util;

import java.util.UUID;

public class UuidUtil {

    private UuidUtil() {

    }

    public static String generateUuid(int len){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,len);
    }

    public static String generateUuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
