package com.vinspier.aop.util;

import com.vinspier.aop.aop.SystemLogEvent;
import com.vinspier.aop.config.ApplicationContextConfig;
import com.vinspier.aop.enums.SystemLogTypeEnum;
import com.vinspier.aop.pojo.SystemLog;
import org.springframework.context.ApplicationContext;
import sun.rmi.runtime.Log;

import java.time.LocalDateTime;

/**
 * ApplicationEvent事件 发布客户端
 * */
public class SystemLogPublishClient {

    private final static ApplicationContext APPLICATION_CONTEXT = ApplicationContextConfig.getApplicationContext();

    private SystemLogPublishClient() {

    }

    /**
     * 发布请求日志
     * */
    public static void publishLogEvent(String method,String params,String message){
        SystemLog log = new SystemLog();
        log.setCreateTime(LocalDateTime.now());
        log.setClassname(SystemLogPublishClient.class.getName());
        log.setMethod(method);
        log.setParams(params);
        log.setMessage(message);
        log.setType(SystemLogTypeEnum.LOG_EVENT_CLIENT.getType());
        APPLICATION_CONTEXT.publishEvent(new SystemLogEvent(log));
    }

}
