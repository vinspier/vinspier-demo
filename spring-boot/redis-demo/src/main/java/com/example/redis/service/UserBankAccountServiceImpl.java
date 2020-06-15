package com.example.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.RedisZSet;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserBankAccountServiceImpl implements UserBankAccountService {

    @Resource
    private RedisTemplate redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(UserBankAccountServiceImpl.class);

    @Override
    public void initCount(String username, Integer count) {
        // 初始化数据到redis中去
        redisTemplate.opsForValue().set(username,count);
    }

    /**
     * 模拟更新redis缓存中的用户数据
     * */
    @Override
    public void add(String username, Integer count) {
        // ToDo 更新数据库的数据
        Integer originCount = (Integer) redisTemplate.opsForValue().get(username);
        // 更新数据到redis中去
        redisTemplate.opsForValue().set(username,originCount + count);
    }

    @Override
    public void decrement(String username, Integer count) {
        // ToDo 更新数据库的数据
        Integer originCount = (Integer)redisTemplate.opsForValue().get(username);
        if (originCount < 1){
            logger.error("数据小于1 更新失败");
            return;
        }
        // 更新数据到redis中去
        redisTemplate.opsForValue().set(username,originCount - count);
    }

    @Override
    public Integer get(String username) {
        return (Integer)redisTemplate.opsForValue().get(username);
    }

    @Override
    public void set(String username, Integer count) {
        // 初始化数据到redis中去
        redisTemplate.opsForValue().set(username,count);
    }


    public void addSet(String key,Long value,Double score){
        redisTemplate.opsForZSet().add(key,value,score);
    }
}
