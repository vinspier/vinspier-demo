package com.vinspier.demo.mvc.controller;

import com.vinspier.demo.mvc.util.ResponseTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "demo-mvc")
public class TestController {

    @RequestMapping(value = "test")
    public ResponseTemplate test(){
        ResponseTemplate template = ResponseTemplate.createOk();
        template.setData("234");
        return template;
    }

}
