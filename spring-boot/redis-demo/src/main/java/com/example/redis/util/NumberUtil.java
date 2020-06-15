package com.example.redis.util;

import java.util.Random;

public class NumberUtil {

    private final static Random random = new Random();

    private NumberUtil() {

    }

    public static long randomLong(){
        return random.nextLong();
    }

    public static int randomInt(){
        return random.nextInt();
    }

    public static int randomInt(int max){
        return random.nextInt(max);
    }

    public static double randomDouble(){
        return random.nextDouble();
    }

}
