package com.vinspier.demo.controller;

import com.vinspier.demo.util.ResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
@RequestMapping(value = "demo")
public class TestController {

    @Autowired
    private DataSource dataSource;

    @RequestMapping(value = "test")
    public ResponseTemplate test(){
        ResponseTemplate template = ResponseTemplate.createOk();
        template.setData("123");
        return template;
    }


    @RequestMapping(value = "dataSource")
    public ResponseTemplate dataSource(){
        ResponseTemplate template = ResponseTemplate.createOk();
        template.setData(dataSource.toString());
        return template;
    }

}
