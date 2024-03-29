package com.example.redis.service;

import com.example.redis.config.CustomizeProperties;
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
 * 用时间戳还是会有问题的
 * 存在其他线程错误释放的问题
 *
 */
@Service
@EnableConfigurationProperties(CustomizeProperties.class)
public class RedisLockServiceImpl implements RedisLockService {

    private static final Logger logger = LoggerFactory.getLogger(RedisLockServiceImpl.class);

    @Autowired
    private CustomizeProperties customizeProperties;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public boolean lock(String key) {
        long start = System.currentTimeMillis();
        // 锁自动失效时间 防止死锁产生
        long waitTimeOut = customizeProperties.getRedisLockExpired();
        // 自旋等待获取锁
        for (; ; ) {
            // 设置过期时间 避免发生死锁
            boolean succeed = redisTemplate.opsForValue().setIfAbsent(key, System.currentTimeMillis() + waitTimeOut + 1);
            // 如果redis中没有值 则设置成功
            if (succeed) {
                logger.info("获取锁成功 锁key={}", key);
                return true;
            }
            // 如果有则不更新 其他线程还在占用 表示暂时未获取到
            // 判断获取时间是否达到设定时间
            if (System.currentTimeMillis() - start > waitTimeOut) {
                logger.error("等待超时 获取锁失败 锁key={}", key);
                return false;
            }
        }
    }

    @Override
    public boolean lock(String key, long lockTimeout) {
        // 自旋等待获取锁
        for (; ; ) {
            // 设置过期时间 避免发生死锁
            boolean succeed = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, System.currentTimeMillis() + lockTimeout + 1));
            // 如果redis中没有值 则设置成功
            if (succeed) {
                logger.info("获取锁成功 time at {}", System.currentTimeMillis());
                return true;
            }
            Long expiredTimeAt = (Long) redisTemplate.opsForValue().get(key);
            // 失效时间还未过期 则在有效时间内随机等待一小段时间后重新尝试获取锁
            if (expiredTimeAt > System.currentTimeMillis()) {
                try {
                    // 随机延迟 避免脑裂
                    Thread.sleep(new Random().nextInt(Long.valueOf(expiredTimeAt - System.currentTimeMillis() - 1).intValue()));
                    continue;
                } catch (InterruptedException e) {
                    logger.info("occur an error when wait for fetch a distributed lock!");
                    return false;
                }
            }
            // 失效时间已经过期
            expiredTimeAt = (Long) redisTemplate.opsForValue().getAndSet(key, System.currentTimeMillis() + lockTimeout + 1);
            // 再次判断是否过期 未过期说明其他线程优先获取到锁了 继续等待
            if (expiredTimeAt > System.currentTimeMillis()) {
                continue;
            }
            // 如果取出来的旧值是过期数据 说明获得锁成功
            return true;
        }
    }

    @Override
    public boolean unLock(String key) {
        Long expiredTime = (Long) redisTemplate.opsForValue().get(key);
        if (null == expiredTime) {
            return true;
        }
        // 释放锁的时候 检查key的是否在有效时间内
        // 如果不是说明 其他竞争者已经重新设置值了
        // 此时的客户端可能发生网路异常或者其他阻塞时间太久了 所以不能把其他线程占用的锁释放掉
        if (expiredTime > System.currentTimeMillis()) {
            return redisTemplate.delete(key);
        }
        return false;
    }

}
