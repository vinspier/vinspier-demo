package com.vinspier.demo.interview;

public interface CustomizeCached<K,V> {

    V put(K key,V val);

    V get(K key);

    V remove(K key);

    K removeLast();

    int size();

    void iterator();

}
