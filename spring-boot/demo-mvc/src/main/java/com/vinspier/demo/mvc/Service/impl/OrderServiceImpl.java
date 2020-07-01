package com.vinspier.demo.mvc.Service.impl;

import com.vinspier.demo.mvc.Service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void selectOrderNeverTransaction() {
        System.out.println("[---- 查询order never支持事务 ----]");
    }
}
