package com.springboot.demo.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @EnableZuulProxy  开启网关功能
 *
 * 默认情况下，一切服务的映射路径就是服务名本身。例如服务名为：`service-provider`，则默认的映射路径就	是：`/service-provider/**`
 * zuul.prefix=/api来指定了路由的前缀，这样在发起请求时，路径就要以/api开头
 *
 *
 * Zuul中默认就已经集成了Ribbon负载均衡和Hystix熔断机制。
 * 但是所有的超时策略都是走的默认值，比如熔断超时时间只有1S，很容易就触发了。因此建议我们手动进行配置：
 * */
@SpringBootApplication
@EnableZuulProxy // 开启网关功能
@EnableDiscoveryClient
public class ZuulDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulDemoApplication.class, args);
	}

}
