package com.springcloud.demo.service.controller;

import com.springcloud.demo.service.mapper.UserMapper;
import com.springcloud.demo.service.pojo.User;
import com.springcloud.demo.service.util.ResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "getList")
    public ResponseTemplate getUser(){
        ResponseTemplate template = ResponseTemplate.createOk();
        List<User> userList = userMapper.selectAll();
        template.setData(userList);
        return template;
    }

    @GetMapping(value = "getById/{id}")
    public User getUserById(@PathVariable Long id){
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @GetMapping(value = "getById1/{id}")
    public ResponseTemplate getUserById1(@PathVariable Long id){
        ResponseTemplate template = ResponseTemplate.createOk();
        User user = userMapper.selectByPrimaryKey(id);
        template.setData(user);
        return template;
    }


    /**
     * 当服务发生错误 或者超过调用者指定的熔断时间
     *
     * */
    @GetMapping(value = "getById2/{id}")
    public User getUserById2(@PathVariable Long id){
        User user = userMapper.selectByPrimaryKey(id);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            System.out.println("============= 线程休眠2S =============");
        }
        return user;
    }

}
