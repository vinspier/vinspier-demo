package com.vinspier.customize.template.service;

import com.vinspier.customize.template.pojo.User;

/**
 * @ClassName: UserService
 * @Description: 用户相关业务逻辑接口
 * @Author:
 * @Date: 2020/3/19 11:38
 * @Version V1.0
 **/
public interface UserService {


    User login(String username,String password,String token);
    User getById(Long id);
    User queryByPrimaryKey(Object var);
    User queryByExample(String property, Object var);
    User getByUsername(String username);

}
