package com.example.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "customize")
public class CustomizeProperties {

    private Long redisLockExpired;

    public Long getRedisLockExpired() {
        return redisLockExpired;
    }

    public void setRedisLockExpired(Long redisLockExpired) {
        this.redisLockExpired = redisLockExpired;
    }
}
