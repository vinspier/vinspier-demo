package com.example.redis.service;

public interface RedisLockService {

    /**
     * 非阻塞性
     * 未获得锁直接返回客户端false
     * @param key
     * @return
     */
    boolean lock(String key);

    /**
     * 阻塞性
     * 直到获得锁直接返回客户true
     * @param key
     * @param lockTimeout key的值有效时间 这里区别于redis key的自动释放失效时间
     * @return
     */
    boolean lock(String key,long lockTimeout);

    /**
     * 释放分布式锁
     * @param key
     * @return
     */
    boolean unLock(String key);

}
