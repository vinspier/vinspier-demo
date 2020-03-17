package com.springboot.demo.ribbon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RibbonDemoApplication.class)
public class RibbonDemoApplicationTests {

	@Autowired
	private RibbonLoadBalancerClient client;

	@Test
	public void contextLoads() {
	}

	/**
	 * 测试获取服务方式
	 * */
	@Test
	public void testLoadBalance(){
		for (int i = 0; i < 100; i++) {
			ServiceInstance instance = this.client.choose("user-service");
			System.out.println(instance.getHost() + ":" +instance.getPort());
		}
	}
}
