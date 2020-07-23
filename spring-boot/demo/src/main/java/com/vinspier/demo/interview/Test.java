package com.vinspier.demo.interview;

public class Test {

    public static void main(String[] args) {
        CustomizeCache cache = new CustomizeCache();
        cache.put("1",1);
        cache.put("2",2);
        cache.put("3",3);
        cache.put("4",4);
        cache.iterator();
        System.out.println("=======================");
        cache.get("1");
        cache.remove("3");
        cache.iterator();
    }

}
