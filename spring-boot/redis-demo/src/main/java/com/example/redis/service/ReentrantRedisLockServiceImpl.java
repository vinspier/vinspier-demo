package com.example.redis.service;

import com.example.redis.config.CustomizeProperties;
import jodd.time.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * 最好是用能唯一确定的值（当前线程ID）
 *
 */
@Service
@EnableConfigurationProperties(CustomizeProperties.class)
public class ReentrantRedisLockServiceImpl implements ReentrantRedisLockService {

    private static final Logger logger = LoggerFactory.getLogger(ReentrantRedisLockServiceImpl.class);

    @Autowired
    private CustomizeProperties customizeProperties;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public boolean lock(String key) {
        return this.lock(key,customizeProperties.getRedisLockExpired());
    }

    @Override
    public boolean lock(String key, long lockTimeout) {
        long threadId = getCurrentThreadId();
        long expiredAt = calculateExpiredAt(lockTimeout);
        // 自旋等待获取锁
        for (; ; ) {
            // 设置过期时间 避免发生死锁
            boolean succeed = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, threadId, lockTimeout, TimeUnit.MILLISECONDS));
            // 如果redis中没有值 则设置成功
            if (succeed) {
                logger.info("获取锁成功 锁key={} value={}", key,threadId);
                return true;
            }
            // 判断是否超时 跳出需要等待
            // 随机等待一小段时间 尽可能避免脑裂(主节点假死 从节点未即时同步 且升为主节点)
            if (!waitLockAvailable(expiredAt) || !waitLockInterrupt(lockTimeout)) {
                logger.error("等待超时 获取锁失败 锁key={}", key);
                return false;
            }
        }
    }

    /**
     * 释放时 需要判断是否是本线程持有
     * */
    @Override
    public boolean unLock(String key) {
        long cacheThreadId = (Long) redisTemplate.opsForValue().get(key);
        if (cacheThreadId != getCurrentThreadId()) {
            return true;
        }
        return redisTemplate.delete(key);
    }

    @Override
    public boolean reentrantLock(String key) {
        return reentrantLock(key,customizeProperties.getRedisLockExpired());
    }

    @Override
    public boolean reentrantLock(String key, long lockTimeout) {
        long threadId = getCurrentThreadId();
        long expiredAt = calculateExpiredAt(lockTimeout);
        for (;;) {
            boolean hasLocked = Boolean.TRUE.equals(redisTemplate.hasKey(key));
            // 是否存在判断
            if (hasLocked) {
                boolean currentThreadHold = redisTemplate.opsForHash().hasKey(key,threadId);
                // 可重入判断
                if (!currentThreadHold) {
                    continue;
                }
                // 重入计数器 +1
                return redisTemplate.opsForHash().increment(key,threadId,1) > 0;
            }
            // 尝试加锁
            boolean lockSucceed = Boolean.TRUE.equals(redisTemplate.opsForHash().putIfAbsent(key,threadId,1L));
            if (lockSucceed) {
                return true;
            }
            // 判断是否超时 跳出需要等待
            if (!waitLockAvailable(expiredAt) || !waitLockInterrupt(lockTimeout)) {
                logger.error("等待超时 获取锁失败 锁key={}", key);
                return false;
            }
        }
    }

    @Override
    public boolean releaseReentrantLock(String key) {
        long threadId = getCurrentThreadId();
        boolean currentThreadHold = redisTemplate.opsForHash().hasKey(key,threadId);
        if (currentThreadHold) {
            Long count = (Long) redisTemplate.opsForHash().get(key,threadId);
            if (count <= 1) {
                return Boolean.TRUE.equals(redisTemplate.delete(key));
            } else {
                redisTemplate.opsForHash().put(key,threadId,count - 1);
            }
        }
        return true;
    }

    private long getCurrentThreadId() {
        return Thread.currentThread().getId();
    }

    private long calculateExpiredAt(long waitTime) {
        return System.currentTimeMillis() + waitTime;
    }


    /**
     * 等待过期判断
     * */
    private boolean waitLockAvailable(long expiredAt) {
        return expiredAt < System.currentTimeMillis();
    }

    /**
     * 可中断随机等待
     * */
    private boolean waitLockInterrupt(long lockTimeout) {
        try {
            // 随机延迟 避免脑裂
            Thread.sleep(new Random().nextInt(Long.valueOf(lockTimeout - 1).intValue()));
        } catch (InterruptedException e) {
            logger.info("occur an error when wait for fetch a distributed lock!");
            return false;
        }
        return true;
    }

}
