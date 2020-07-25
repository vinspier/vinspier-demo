package com.vinspier.cluster.controller;

import com.vinspier.cluster.util.DataMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
public class RedisSentinelController {

    private Logger logger = LoggerFactory.getLogger(RedisSentinelController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/getByKey/{key}")
    public String getByKey(@PathVariable ("key") String key){
        // get form redis
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null){
            logger.info("get data from redis [{}]",value);
            return "get data from redis [" + value.toString() + "]";
        }
        value = DataMapUtil.get(key);
        logger.info("get data from dbMap [{}]",value);
        redisTemplate.opsForValue().set(key,value);
        logger.info("put data to redis [key={},value={}]",key,value);
        return "get data from dbMap [" + value.toString() + "]";
    }

    @RequestMapping("/put/{key}/{value}")
    public Boolean put(@PathVariable ("key") String key,@PathVariable ("value") Object value){
        DataMapUtil.put(key,value);
        logger.info("put data to dbMap [key={},value={}]",key,value);
        redisTemplate.opsForValue().set(key,value);
        logger.info("put data to redis [key={},value={}]",key,value);
        return true;
    }

    @RequestMapping("/randomPut")
    public void randomPut(){
        Random random = new Random();
        String key;
        int val;
        for (int i = 0; i < 20; i++){
            key = UUID.randomUUID().toString().substring(0,10);
            val = random.nextInt();
            redisTemplate.opsForValue().set(key, val);
            logger.info(">>>message: put data to redis @data=[{}={}]",key,val);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
