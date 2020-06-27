package com.vinspier.aop.service;

import com.vinspier.aop.mapper.SystemLogDao;
import com.vinspier.aop.pojo.SystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemLogServiceImpl implements SystemLogService {

    @Autowired
    private SystemLogDao systemLogDao;

    @Override
    public int saveLog(SystemLog log) {
        return systemLogDao.insert(log);
    }

}
