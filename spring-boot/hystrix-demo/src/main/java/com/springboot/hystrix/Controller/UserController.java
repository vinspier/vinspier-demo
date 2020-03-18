package com.springboot.hystrix.Controller;


import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springboot.hystrix.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 指定一个类的全局降级方法
 * */
@Controller
@DefaultProperties(defaultFallback = "globalFallback")
@RequestMapping(value = "hystrix/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 标记该方法需要指定降级方法
     * */
    @GetMapping(value = "getById/{id}")
    @HystrixCommand(fallbackMethod = "getUserByIdFallback")
    @ResponseBody
    public String getUserById(@PathVariable Long id){
        return restTemplate.getForObject("http://user-service/user/getById2/" + id,User.class).toString();
    }

    /**
     * 标记该方法需要降级 采用默认降级方法
     * */
    @GetMapping(value = "getById2/{id}")
    @HystrixCommand
    @ResponseBody
    public String getUserById2(@PathVariable Long id){
        return restTemplate.getForObject("http://user-service/user/getById2/" + id,String.class).toString();
    }

    /**
     * 标记该方法需要指定降级方法
     * */
    @GetMapping(value = "getById3/{id}")
    @HystrixCommand
    @ResponseBody
    public String getUserById3(@PathVariable Long id){
        // 模拟调用服务者发生错误 促使熔断器发生熔断
        if(id == 1){
            int a = 1/0;
        }
        return restTemplate.getForObject("http://user-service/user/getById/" + id,String.class).toString();
    }

    /**
     * 熔断降级的方法 参数 必须与 被指定该方法的触发者犯法的参数一致
     * */
    public String getUserByIdFallback(Long id){
        return "请求超时，请稍后重试";
    }

    /**
     * 默认降级方法，不用任何参数，以匹配更多方法，但是返回值一定一致
     * */
    public String globalFallback(){
        return "系统发生错误，请联系管理员";
    }

    public String testFallback(Long id){
        return "测试熔断,系统发生错误，请联系管理员";
    }
}
