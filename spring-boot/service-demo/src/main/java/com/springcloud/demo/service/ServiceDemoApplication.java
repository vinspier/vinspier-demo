package com.springcloud.demo.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.springcloud.demo.service.mapper")
public class ServiceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDemoApplication.class, args);
	}

}
