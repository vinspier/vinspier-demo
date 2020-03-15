package com.vinspier.demo.mvc.Service;

import com.vinspier.demo.mvc.pojo.User;

import java.util.List;

public interface UserService {

    User findByUsername(String username);
    User findById(Long id);
    List<User> getList();
}
