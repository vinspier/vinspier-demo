package com.example.redis.service;

public interface RedisLockService {

    // 获取分布式锁
    boolean lock(String key);
    // 释放分布式锁
    boolean unLock(String key);

}
