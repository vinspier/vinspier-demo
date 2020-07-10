package com.vinspier.demo.mvc.Service.impl;

import com.vinspier.demo.mvc.Service.OrderService;
import com.vinspier.demo.mvc.Service.UserService;
import com.vinspier.demo.mvc.mapper.UserMapper;
import com.vinspier.demo.mvc.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void selectOrderNeverTransaction() {
        System.out.println("[---- 查询order never支持事务 ----]");
    }


    /**
     * 被调用方 抛出免检异常
     * */
    @Override
    @Transactional
    public void doTransactionThrewUnChecked() {
        try {
            userService.throwUnchecked();
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = getUser();
        userMapper.insert(user);
        throw new RuntimeException("抛出运行时异常 免检异常");
    }

    /**
     * 被调用方 抛出检查异常
     * */
    @Override
    @Transactional
    public void doTransactionThrewChecked() {
        try {
            userService.throwChecked();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User getUser(){
        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setPassword("123435");
        user.setPhone("188833");
        user.setCreated(new Date());
        user.setSalt("sdfgh");
        return user;
    }

}
