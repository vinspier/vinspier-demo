package com.vinspier.aop;

import com.vinspier.aop.util.SystemLogPublishClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AopDemoApplicationTest {

	private static final Logger logger = LoggerFactory.getLogger(AopDemoApplicationTest.class);

	@Test
	public void contextLoads() {
		logger.info("[=========test method=========]");
	}

	@Test
	public void publishLog(){
		SystemLogPublishClient.publishLogEvent("publishLog","[asfd]","client publish log");
	}
}
