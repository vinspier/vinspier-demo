package com.springcloud.demo.controller;


import com.springcloud.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(value = "user-consumer")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "getById/{id}")
    @ResponseBody
    public Object getUserById(@PathVariable Long id){
        return restTemplate.getForObject("http://localhost/user-service/getById/" + id,String.class);
    }

    @GetMapping(value = "getUserList/{serverId}")
    @ResponseBody
    public Object getUserList(@PathVariable String serverId){
        List<ServiceInstance> instances = discoveryClient.getInstances(serverId);
        return restTemplate.getForObject("http://" + instances.get(0).getHost() + ":" + instances.get(0).getPort() + "/user-service/getList/",String.class);
    }

}
