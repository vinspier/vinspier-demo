package com.vinspier.demo.mvc.controller;

import com.vinspier.demo.mvc.Service.UserService;
import com.vinspier.demo.mvc.pojo.User;
import com.vinspier.demo.mvc.util.ResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "getList")
    public ResponseTemplate getUser(){
        ResponseTemplate template = ResponseTemplate.createOk();
        List<User> userList = userService.getList();
        template.setData(userList);
        return template;
    }

    @RequestMapping(value = "findById/{id}")
    public ResponseTemplate findById(@PathVariable Long id){
        ResponseTemplate template = ResponseTemplate.createOk();
        User user = userService.findById(id);
        template.setData(user);
        return template;
    }

}
