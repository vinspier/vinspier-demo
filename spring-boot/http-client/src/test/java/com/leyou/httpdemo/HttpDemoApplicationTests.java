package com.leyou.httpdemo;

import com.leyou.httpdemo.pojo.ResultCode;
import com.leyou.httpdemo.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HttpDemoApplication.class)
public class HttpDemoApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void httpGet() {
//		User user = this.restTemplate.getForObject("http://localhost/hello", User.class);
//		System.out.println(user);

		ResponseEntity<String> resultCode = restTemplate.getForEntity("http://localhost:90/secKill/grab/1000/17867160219/f6e12a983a64dd3365f966786a6d5b76", String.class);
		System.out.println(resultCode);
	}

}
