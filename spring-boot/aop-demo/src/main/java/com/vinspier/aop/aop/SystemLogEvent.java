package com.vinspier.aop.aop;

import org.springframework.context.ApplicationEvent;

/**
 * 定义ApplicationEvent事件
 * */
public class SystemLogEvent extends ApplicationEvent {

    public SystemLogEvent(Object source) {
        super(source);
    }

}
