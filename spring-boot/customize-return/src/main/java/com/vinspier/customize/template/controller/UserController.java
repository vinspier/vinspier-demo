package com.vinspier.customize.template.controller;

import com.alibaba.fastjson.JSONObject;
import com.vinspier.customize.template.common.ResponseTemplate;
import com.vinspier.customize.template.common.ResultCode;
import com.vinspier.customize.template.exception.CustomizeException;
import com.vinspier.customize.template.pojo.User;
import com.vinspier.customize.template.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: TestController
 * @Description:
 * @Author:
 * @Date: 2020/3/19 10:42
 * @Version V1.0
 **/

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getById/{id}")
    @ResponseBody
    public ResponseEntity<ResponseTemplate> getById(@PathVariable Long id){
        User user = userService.getById(id);
        ResponseTemplate template = ResponseTemplate.ok(user);
        return ResponseEntity.ok(template);
    }

    @GetMapping("queryByPrimaryKey/{id}")
    @ResponseBody
    public ResponseTemplate queryByPrimaryKey(@PathVariable Long id){
        User user = userService.queryByPrimaryKey(id);
        ResponseTemplate template = ResponseTemplate.ok(user);
        return template;
    }

    @GetMapping("queryByExample/{property}/{value}")
    @ResponseBody
    public ResponseTemplate queryByExample(@PathVariable String property,@PathVariable Object value){
        User user = userService.queryByExample(property,value);
        ResponseTemplate template = ResponseTemplate.ok(user);
        return template;
    }

    @GetMapping("getByUsername/{username}")
    @ResponseBody
    public ResponseTemplate getByUsername(@PathVariable String username){
        User user = userService.getByUsername(username);
        ResponseTemplate template = ResponseTemplate.ok(user);
        return template;
    }

    @GetMapping("queryByPrimaryKey1/{id}")
    @ResponseBody
    public String queryByPrimaryKey1(@PathVariable Long id){
        User user = userService.getById(id);
        if (user == null){
            throw new CustomizeException(ResultCode.USER_NOT_EXIST);
        }
        ResponseTemplate template = ResponseTemplate.ok(user);
        return JSONObject.toJSONString(template);
    }

}
