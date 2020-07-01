package com.vinspier.demo.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private HandlerInterceptor myInterceptor;

    /**
     * 添加自己的拦截器
     * 指定拦截路径
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("[---- 加载配置类 ----]");
        registry.addInterceptor(myInterceptor).addPathPatterns("/**");
    }

}
