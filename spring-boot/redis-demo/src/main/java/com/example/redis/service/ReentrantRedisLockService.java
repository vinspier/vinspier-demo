package com.example.redis.service;

/**
 * 可存储map结构 threadId -> count 实现分布式可重入
 * */
public interface ReentrantRedisLockService extends RedisLockService {

    /**
     * 非阻塞性
     * 未获得锁直接返回客户端false
     * @param key
     * @return
     */
    boolean reentrantLock(String key);

    /**
     * 阻塞性
     * 直到获得锁直接返回客户true
     * @param key
     * @param lockTimeout key的值有效时间 这里区别于redis key的自动释放失效时间
     * @return
     */
    boolean reentrantLock(String key,long lockTimeout);

    /**
     * 释放分布式锁
     * @param key
     * @return
     */
    boolean releaseReentrantLock(String key);

}
