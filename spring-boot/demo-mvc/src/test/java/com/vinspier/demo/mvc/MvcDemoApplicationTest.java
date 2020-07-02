package com.vinspier.demo.mvc;

import com.vinspier.demo.mvc.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MvcDemoApplicationTest {

    @Autowired
    private UserService userService;

    /**
     * 测试 事务传播特性 之 never
     * */
    @Test
    public void testTransaction(){
        userService.requiredTransaction();
    }

    /**
     * 调用者存在事务
     * 测试 事务 同个类中 方法调用
     * 事务失效
     * */
    @Test
    public void testTransaction1(){
        userService.outerWithTransaction();
    }

    /**
     * 调用者不存在事务
     * 测试 事务 同个类中 方法调用
     * 事务失效
     * */
    @Test
    public void testTransaction2(){
        userService.outerWithoutTransaction();
    }
}
