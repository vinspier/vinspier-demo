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
@RequestMapping(value = "user-service")
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
    public ResponseTemplate getUserById(@PathVariable Long id){
        ResponseTemplate template = ResponseTemplate.createOk();
        User user = userMapper.selectByPrimaryKey(id);
        template.setData(user);
        return template;
    }

}
