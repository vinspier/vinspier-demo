package com.vinspier.demo.mvc.Service;

import com.vinspier.demo.mvc.pojo.User;

import java.util.List;

public interface UserService {

    User findByUsername(String username);
    User findById(Long id);
    List<User> getList();

    /** 测试 事务传播特性之REQUIRED */
    void requiredTransaction();
    /** 测试 事务传播特性之NEVER */
    void neverTransaction();

    /** 外部调用 支持事务 */
    void outerWithTransaction();
    /** 外部调用 不支持事务 */
    void outerWithoutTransaction();
    /** 内部被调用 支持事务 */
    void innerWithTransaction();
}
