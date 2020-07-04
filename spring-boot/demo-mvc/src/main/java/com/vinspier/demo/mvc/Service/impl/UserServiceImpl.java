package com.vinspier.demo.mvc.Service.impl;

import com.vinspier.demo.mvc.Service.OrderService;
import com.vinspier.demo.mvc.Service.UserService;
import com.vinspier.demo.mvc.mapper.UserMapper;
import com.vinspier.demo.mvc.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Scope 指定bean的范围
 * more就是 singleton
 * */
@Service
@Scope(value = "singleton")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderService orderService;

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userMapper.selectOne(new User());
    }

    @Override
    public User findById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> getList() {
        return userMapper.selectAll();
    }


    /**
     *
     * 内部调用了selectOrderNeverTransaction方法
     * 该方法设置了 不支持事务
     * 所以会抛出一个异常
     * org.springframework.transaction.IllegalTransactionStateException
     * Existing transaction found for transaction marked with propagation 'never'
     * */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void requiredTransaction() {
        System.out.println("[---- 该方法支持事务 方式REQUIRED ----]");
        User user = getUser();
        userMapper.insert(user);
        /** 调用另一个不支持事务的方法*/
        orderService.selectOrderNeverTransaction();
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void neverTransaction() {
        System.out.println("[---- 该方法不支持事务 ----]");
    }

    @Override
    @Transactional
    public void outerWithTransaction() {
        User user = getUser();
        userMapper.insert(user);
        /** 虽然内部调用 被调用者的事务失效了
         * 但是 这个逻辑 合并到了此方法的事务中去了
         * */
        innerWithTransaction();
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void outerWithoutTransaction() {
        /**
         * 此方法不支持事务
         * 被调用方的事务失效 在为抛出异常的之前的逻辑全部有效
         * */
        innerWithTransaction();
    }

    @Override
    @Transactional
    public void innerWithTransaction() {
        User user = getUser();
        userMapper.insert(user);
        User user1 = getUser();
        /** 模拟数据重复插入，数据库会抛错 */
        user1.setUsername("fxb");
        userMapper.insert(user1);
    }

    @Override
    @Transactional
    public void throwChecked() throws Exception{
        User user = getUser();
        userMapper.insert(user);
        throw new Exception("抛出检查异常");
    }

    @Override
    @Transactional
    public void throwUnchecked() {
        User user = getUser();
        userMapper.insert(user);
        throw new RuntimeException("抛出运行时异常 免检异常");
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
