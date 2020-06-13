package com.example.redis.service;


public interface UserBankAccountService {
    void initCount(String username,Integer count);
    void add(String username,Integer count);
    void decrement(String username,Integer count);
    Integer get(String username);
    void set(String username,Integer count);
}
