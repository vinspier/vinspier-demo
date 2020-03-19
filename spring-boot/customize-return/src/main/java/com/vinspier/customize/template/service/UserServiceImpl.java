package com.vinspier.customize.template.service;

import com.vinspier.customize.template.mapper.UserMapper;
import com.vinspier.customize.template.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: UserServiceImpl
 * @Description: 用户相关业务逻辑实现类
 * @Author:
 * @Date: 2020/3/19 11:38
 * @Version V1.0
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password, String token) {
        return null;
    }

    @Override
    public User getById(Long id) {
        return userMapper.getById(id);
    }

    @Override
    public User queryByPrimaryKey(Object var) {
        return userMapper.selectByPrimaryKey(var);
    }

    @Override
    public User queryByExample(String property, Object var) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(property,var);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }
}
