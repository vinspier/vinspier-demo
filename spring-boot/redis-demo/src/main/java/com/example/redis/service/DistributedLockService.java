package com.example.redis.service;

public interface DistributedLockService {
    Boolean lock(String lockName);
    Boolean unlock(String lockName);
}
