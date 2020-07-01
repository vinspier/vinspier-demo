package com.vinspier.demo.mvc.Service.impl;

import com.vinspier.demo.mvc.Service.OrderService;
import com.vinspier.demo.mvc.Service.UserService;
import com.vinspier.demo.mvc.mapper.UserMapper;
import com.vinspier.demo.mvc.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
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
        User user = new User();
        user.setUsername("fxb123");
        user.setPassword("123435");
        user.setPhone("188833");
        user.setCreated(new Date());
        user.setSalt("sdfgh");
        userMapper.insert(user);
        orderService.selectOrderNeverTransaction();
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void neverTransaction() {
        System.out.println("[---- 该方法不支持事务 ----]");
    }

}
