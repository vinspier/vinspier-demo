package com.vinspier.sentinel.controller;

import com.vinspier.sentinel.util.DataMapUtil;
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


    /**
     * 当主节点发生故障后，从节点选举产生作为新的主节点
     * 服务恢复可用
     *
     * */
    @RequestMapping("/randomPut")
    public void randomPut(){
        Random random = new Random();
        new Thread(() -> {
            while (true){
                String key;
                int val;
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
        }).start();
        new Thread(() -> {
            String key = UUID.randomUUID().toString().substring(0,10);
            while (true){
                redisTemplate.opsForValue().get(key);
                logger.info(">>>message: get data from redis @key=[{}]",key);
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
