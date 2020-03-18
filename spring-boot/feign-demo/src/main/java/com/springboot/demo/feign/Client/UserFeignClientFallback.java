package com.springboot.demo.feign.Client;

import com.springboot.demo.feign.pojo.User;
import org.springframework.stereotype.Component;

@Component
public class UserFeignClientFallback implements UserFeignClient {

    @Override
    public User queryById(Long id) {
        return null;
    }

    @Override
    public User queryById2(Long id) {
        return null;
    }
}
