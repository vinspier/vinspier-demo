package com.vinspier.aop.controller;

import com.vinspier.aop.annotation.SystemLogAnnotation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping(value = "/findByName/{username}")
    public Object findByName(@PathVariable("username") String username){
        System.out.println("========== 模拟查询 ==========");
        return username;
    }

    @GetMapping(value = "/findByName1/{username}")
    public void findByName1(@PathVariable("username") String username){
        System.out.println("========== 模拟查询 ==========");
    }

    @GetMapping(value = "/findByName2/{username}")
    public void findByName2(@PathVariable("username") String username) throws Throwable{
        System.out.println("========== 模拟出错查询 ==========");
        throw new Exception("模拟出错");
    }

    @GetMapping(value = "/findByName3/{username}")
    @SystemLogAnnotation(message = "通过名称查询用户")
    public void findByName3(@PathVariable("username") String username){
        System.out.println("========== 模拟出错查询 ==========");
    }

}
