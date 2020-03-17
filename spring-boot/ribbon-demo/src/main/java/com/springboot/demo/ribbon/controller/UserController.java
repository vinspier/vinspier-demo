package com.springboot.demo.ribbon.controller;

import com.springboot.demo.ribbon.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("ribbon/user")
public class UserController {

    @Autowired
    private RestTemplate template;

    @GetMapping(value = "getById")
    @ResponseBody
    public User queryUserById(@RequestParam("id") Long id){
        // 通过client获取服务提供方的服务列表，这里我们只有一个
        // ServiceInstance instance = discoveryClient.getInstances("service-provider").get(0);
        String baseUrl = "http://user-service/user/getById/" + id;
        User user = this.template.getForObject(baseUrl, User.class);
        return user;
    }

}
