package com.vinspier.demo.mvc.Service.impl;

import com.vinspier.demo.mvc.Service.UserService;
import com.vinspier.demo.mvc.mapper.UserMapper;
import com.vinspier.demo.mvc.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
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
}
