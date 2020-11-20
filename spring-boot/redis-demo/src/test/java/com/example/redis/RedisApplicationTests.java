package com.example.redis;

import com.example.redis.service.DistributedLockService;
import com.example.redis.service.RedisLockService;
import com.example.redis.service.UserBankAccountService;
import com.example.redis.util.NumberUtil;
import com.example.redis.util.UuidUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisApplicationTests {
	
	private final Logger logger = LoggerFactory.getLogger(RedisApplicationTests.class);

	private final String USERNAME = "fxb";
	private final String SET_KEY_PREFIX = "article";
	private final String LOCK_NAME = "fxb_lock";
	private final Integer INIT_ACCOUNT = 1555500;

	private final int MODIFY_TIMES = 500;


	private CountDownLatch countDownLatch = new CountDownLatch(MODIFY_TIMES);
	ExecutorService executorService = Executors.newCachedThreadPool();

	@Autowired
	private UserBankAccountService userBankAccountService;
	@Autowired
	private RedisLockService redisLockService;
	@Autowired
	private DistributedLockService distributedLockService;

	@Autowired
	private RedisTemplate redisTemplate;

	// 初始设置账户值
	@Test
	public void initBankAccount() {
		userBankAccountService.initCount(USERNAME,INIT_ACCOUNT);
	}

	// 获取当前值
	@Test
	public void get() {
		logger.info("---------------------------[info]" + userBankAccountService.get(USERNAME));
	}

	// 模拟没有加分布式锁的高并发修改场景
	// 启动1000个线程
	@Test
	public void multiAddWithoutLock() throws InterruptedException{
		Integer originalAccount = userBankAccountService.get(USERNAME);
		Long start = System.currentTimeMillis();
		for(int i = 0; i < MODIFY_TIMES; i++){
			executorService.execute(() -> {
				countDownLatch.countDown();
				userBankAccountService.add(USERNAME,1);
			});
		}
		Thread.sleep(5000);
		Long end = System.currentTimeMillis();
		logger.info("---------------------------[info]" + "original account:  " + originalAccount);
		logger.info("---------------------------[info]" + "decrement count = {} and time took {} ms",MODIFY_TIMES,(end - start));
		Integer currentAccount = userBankAccountService.get(USERNAME);
		logger.info("---------------------------[info]" + "current account:  " + currentAccount);
	}

	// 模拟加分布式锁的高并发修改场景
	// 该锁不可重入
	// 缺点 该锁不支持重入
	// 启动500个线程
	@Test
	public void multiDecrementWithLock() throws InterruptedException{
		Integer initAccount = userBankAccountService.get(USERNAME);
		Long start = System.currentTimeMillis();
		for(int i = 0; i < MODIFY_TIMES; i++){
			new Thread(() -> {
				countDownLatch.countDown();
				// 获取锁成功 则更新
				boolean locked = redisLockService.lock(LOCK_NAME);
				try {
					// 实际业务逻辑
					if (locked){
                        userBankAccountService.decrement(USERNAME,1);
                    }
				} finally {
					// 释放锁
					redisLockService.unLock(LOCK_NAME);
				}
			}).start();
		}
		Thread.sleep(10000);
		Long end = System.currentTimeMillis();
		logger.info("---------------------------[info]" + "initial account:  " + initAccount);
		logger.info("---------------------------[info]" + "decrement count = {} and time took {} ms",MODIFY_TIMES,(end - start));
		Integer currentAccount = userBankAccountService.get(USERNAME);
		logger.info("---------------------------[info]" + "current account:  " + currentAccount);
	}

	// 模拟加redisson分布式锁的高并发修改场景
	// 该锁不可重入
	// 缺点 该锁不支持重入
	// 启动500个线程
	@Test
	public void multiDecrementWithRedissonLock() throws InterruptedException{
		Integer initAccount = userBankAccountService.get(USERNAME);
		Long start = System.currentTimeMillis();
		for(int i = 0; i < MODIFY_TIMES; i++){
			new Thread(() -> {
				countDownLatch.countDown();
				// 获取锁成功 则更新
				boolean locked = distributedLockService.lock(LOCK_NAME);
				try {
					// 实际业务逻辑
					if (locked){
						userBankAccountService.decrement(USERNAME,1);
					}
				} finally {
					// 释放锁
					distributedLockService.unlock(LOCK_NAME);
				}
			}).start();
		}
		Thread.sleep(3000);
		Long end = System.currentTimeMillis();
		logger.info("---------------------------[info]" + "initial account:  " + initAccount);
		logger.info("---------------------------[info]" + "decrement count = {} and time took {} ms",MODIFY_TIMES,(end - start));
		Integer currentAccount = userBankAccountService.get(USERNAME);
		logger.info("---------------------------[info]" + "current account:  " + currentAccount);
	}

	@Test
	public void addZSet(){
		for (int i = 0; i < 5; i++){
			redisTemplate.opsForZSet().add(SET_KEY_PREFIX, NumberUtil.randomLong(),NumberUtil.randomDouble());
		}
	}

	@Test
	public void rank(){
		logger.info("---------------------------[info]" + redisTemplate.opsForZSet().rank("article", -574387857031090747L));
		logger.info("---------------------------[info]" + redisTemplate.opsForZSet().score("article", -574387857031090747L));
		redisTemplate.opsForZSet().incrementScore("article", -574387857031090747L,1.0D);
		logger.info("---------------------------[info]" + redisTemplate.opsForZSet().rank("article", -574387857031090747L));
		logger.info("---------------------------[info]" + redisTemplate.opsForZSet().score("article", -574387857031090747L));
		logger.info("---------------------------[info]" + redisTemplate.opsForZSet().size("article"));
		logger.info("---------------------------[info]" + redisTemplate.opsForZSet().rangeWithScores("article",0L,3L));
	}

	@Test
	public void testSetIfAbsent(){
		redisLockService.lock("lock",10000);
	}

	/**
	 * 阻塞方式获取分布式锁
	 */
	@Test
	public void testSyncLock(){
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 20; i++){
			threadPool.execute(() -> {
				if (redisLockService.lock("LOCK",1500)){
					try {
						// 模拟业务逻辑处理 比锁的有效时间长
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						redisLockService.unLock("LOCK");
					}
				};
			});
		}
		try {
			// 模拟业务逻辑处理 比锁的有效时间长
			Thread.sleep(600000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		threadPool.shutdown();
	}

}
