package com.vinspier.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类引导类
 * */
@SpringBootApplication
// @ComponentScan
// @EnableAutoConfiguration
// @SpringBootConfiguration
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
