package com.example.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.collections.DefaultRedisZSet;
import org.springframework.data.redis.support.collections.RedisZSet;

@SpringBootApplication
// @SpringBootConfiguration
public class RedisApplication {

	/** 自定义序列化的方式 默认采用的是JDK的方式 */
	@Bean
	public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate redisTemplate = new RedisTemplate();
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		redisTemplate.setKeySerializer(redisSerializer);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setHashKeySerializer(redisSerializer);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}




	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

}
