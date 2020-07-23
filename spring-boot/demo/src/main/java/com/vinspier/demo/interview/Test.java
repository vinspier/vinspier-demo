package com.vinspier.demo.interview;

public class Test {

    public static void main(String[] args) {
        CustomizeCache cache = new CustomizeCache(5);
        for (int i = 0; i < 10; i++){
            cache.put("" + i,i);
        }
        System.out.println("[size = " + cache.size() +  "]");
        cache.iterator();
        System.out.println("=======================");
        cache.get("7");
        cache.get("6");
        cache.get("5");
        cache.iterator();
        System.out.println("=======================");
        cache.remove("9");
        System.out.println("[size = " + cache.size() +  "]");
        cache.iterator();
    }

}
