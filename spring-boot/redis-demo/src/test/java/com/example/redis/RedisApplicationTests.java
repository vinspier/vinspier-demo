package com.example.redis;

import com.example.redis.service.DistributedLockService;
import com.example.redis.service.RedisLockService;
import com.example.redis.service.UserBankAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisApplicationTests {

	private final String USERNAME = "fxb";
	private final String LOCK_NAME = "fxb_lock";
	private final Integer INIT_ACCOUNT = 500;

	private final int MODIFY_TIMES = 500;

	private CountDownLatch countDownLatch = new CountDownLatch(MODIFY_TIMES);
	ExecutorService executorService = Executors.newFixedThreadPool(MODIFY_TIMES);

	@Autowired
	private UserBankAccountService userBankAccountService;
	@Autowired
	private RedisLockService redisLockService;
	@Autowired
	private DistributedLockService distributedLockService;

	// 初始设置账户值
	@Test
	public void initBankAccount() {
		userBankAccountService.initCount(USERNAME,INIT_ACCOUNT);
	}

	// 获取当前值
	@Test
	public void get() {
		System.out.println(userBankAccountService.get(USERNAME));
	}

	// 模拟没有加分布式锁的高并发修改场景
	// 启动1000个线程
	@Test
	public void multiAddWithoutLock() throws InterruptedException{
		Integer originalAccount = userBankAccountService.get(USERNAME);
		System.out.println("original account:  " + originalAccount);
		Long start = System.currentTimeMillis();
		for(int i = 0; i < MODIFY_TIMES; i++){
			executorService.execute(() -> {
				userBankAccountService.add(USERNAME,1);
			});
			countDownLatch.countDown();
		}
		countDownLatch.await();
		Thread.sleep(5000);
		Long end = System.currentTimeMillis();
		System.out.println("time took " + (end - start));
		Integer currentAccount = userBankAccountService.get(USERNAME);
		System.out.println("current account:  " + currentAccount);
	}

	// 模拟加分布式锁的高并发修改场景
	// 该锁不可重入
	// 缺点 该锁不支持重入
	// 启动500个线程
	@Test
	public void multiDecrementWithLock() throws InterruptedException{
		Integer initAccount = userBankAccountService.get(USERNAME);
		System.out.println("initial account:  " + initAccount);
		Long start = System.currentTimeMillis();
		for(int i = 0; i < MODIFY_TIMES; i++){
			new Thread(() -> {
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
			countDownLatch.countDown();
		}
		countDownLatch.await();
		Thread.sleep(10000);
		Long end = System.currentTimeMillis();
		System.out.println("time took " + (end - start));
		Integer currentAccount = userBankAccountService.get(USERNAME);
		System.out.println("current account:  " + currentAccount);
	}

	// 模拟加redisson分布式锁的高并发修改场景
	// 该锁不可重入
	// 缺点 该锁不支持重入
	// 启动500个线程
	@Test
	public void multiDecrementWithRedissonLock() throws InterruptedException{
		Integer initAccount = userBankAccountService.get(USERNAME);
		System.out.println("initial account:  " + initAccount);
		Long start = System.currentTimeMillis();
		for(int i = 0; i < MODIFY_TIMES; i++){
			new Thread(() -> {
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
			countDownLatch.countDown();
		}
		countDownLatch.await();
		Thread.sleep(10000);
		Long end = System.currentTimeMillis();
		System.out.println("time took " + (end - start));
		Integer currentAccount = userBankAccountService.get(USERNAME);
		System.out.println("current account:  " + currentAccount);
	}
}
