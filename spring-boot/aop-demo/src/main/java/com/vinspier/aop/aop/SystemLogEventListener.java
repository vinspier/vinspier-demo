package com.vinspier.aop.aop;


import com.vinspier.aop.pojo.SystemLog;
import com.vinspier.aop.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 监听程序中发布的ApplicationEvent事件
 * */

@Slf4j
@Component
public class SystemLogEventListener{

    private static final Logger logger = LoggerFactory.getLogger(SystemLogEventListener.class);

    @Autowired
    private SystemLogService logService;

    @Order
    @EventListener(SystemLogEvent.class)
    public void saveLog(SystemLogEvent event){
        logger.info("[------------- 执行事件监听的日志保存方法 -------------]");
        SystemLog log = (SystemLog)event.getSource();
        logService.saveLog(log);
    }

}
