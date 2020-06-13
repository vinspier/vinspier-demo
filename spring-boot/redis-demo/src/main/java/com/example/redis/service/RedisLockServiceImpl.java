package com.example.redis.service;

import com.example.redis.config.CustomizeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

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
        long timeOut = customizeProperties.getRedisLockExpired();
        // 自旋等待获取锁
        for (;;){
            // 设置过期时间 避免发生死锁
            boolean succeed = redisTemplate.opsForValue().setIfAbsent(key,start,1000, TimeUnit.MILLISECONDS);
            // 如果redis中没有值 则设置成功
            // 如果有则不更新 其他线程还在占用 表示暂时未获取到
            if (succeed){
                logger.info("获取锁成功{}","。");
                return succeed;
            }
            // 判断获取时间是否达到设定时间
            if (timeOut < System.currentTimeMillis() - start){
                logger.error("获取锁失败{}","!");
                return false;
            }
        }
    }

    @Override
    public boolean unLock(String key) {
        return redisTemplate.delete(key);
    }

}
