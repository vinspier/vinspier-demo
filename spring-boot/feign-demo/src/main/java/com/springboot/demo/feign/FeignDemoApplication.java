package com.springboot.demo.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @EnableFeignClients 开启feign客户端
 *
 * feign已经自动集成了Ribbon负载均衡的RestTemplate。
 * 所以，此处不需要再注册RestTemplate。
 * */
@SpringCloudApplication
@EnableFeignClients
public class FeignDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignDemoApplication.class, args);
	}

}
