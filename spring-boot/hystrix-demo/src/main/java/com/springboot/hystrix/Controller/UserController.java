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
 * @DefaultProperties 指定一个类的全局降级方法
 * 熔断状态机3个状态：
 * Closed：关闭状态，所有请求都正常访问。
 *
 * Open：打开状态，所有请求都会被降级。Hystix会对请求情况计数，
 * 当一定时间内失败请求百分比达到阈值，则触发熔断，断路器会完全打开。默认失败比例的阈值是50%，请求次数最少不低于20次。
 *
 *Half Open：半开状态，open状态不是永久的，打开后会进入休眠时间（默认是5S）。随后断路器会自动进入半开状态。
 * 此时会释放部分请求通过，若这些请求都是健康的，则会完全关闭断路器，否则继续保持打开，再次进行休眠计时
 * */
@Controller
@DefaultProperties(defaultFallback = "globalFallback")
@RequestMapping(value = "hystrix/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @HystrixCommand 标记该方法需要指定降级方法
     * */
    @GetMapping(value = "getById/{id}")
    @HystrixCommand(fallbackMethod = "getUserByIdFallback")
    @ResponseBody
    public String getUserById(@PathVariable Long id){
        return restTemplate.getForObject("http://user-service/user/getById2/" + id,User.class).toString();
    }

    /**
     * @HystrixCommand 标记该方法需要降级 采用默认降级方法
     * */
    @GetMapping(value = "getById2/{id}")
    @HystrixCommand
    @ResponseBody
    public String getUserById2(@PathVariable Long id){
        return restTemplate.getForObject("http://user-service/user/getById2/" + id,String.class).toString();
    }

    /**
     * @HystrixCommand 标记该方法需要指定降级方法
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
