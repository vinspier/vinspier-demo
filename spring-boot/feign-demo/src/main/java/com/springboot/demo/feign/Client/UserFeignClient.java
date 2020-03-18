package com.springboot.demo.feign.Client;

import com.springboot.demo.feign.configuration.FeignLogConfiguration;
import com.springboot.demo.feign.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @FeignClient 标注该类是一个feign接口
 * value值指向一个服务名称
 * fallback 指定其实现类 作为熔断的回复方法
 * Feign会根据注解帮我们生成URL，并访问获取结果
 *
 * Feign中本身已经集成了Ribbon依赖和自动配置：
 * Feign默认也有对Hystrix的集成：
 * */

@FeignClient(value = "user-service",fallback = UserFeignClientFallback.class,configuration = FeignLogConfiguration.class)
public interface UserFeignClient {

    @GetMapping("user/getById/{id}")
    User queryById(@PathVariable("id") Long id);

    @GetMapping("user/getById2/{id}")
    User queryById2(@PathVariable("id") Long id);
}
