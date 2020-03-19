package com.vinspier.customize.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName: CustomizeTemplateApplication
 * @Description:
 * @Author:
 * @Date: 2020/3/19 9:54
 * @Version V1.0
 *
 * @MapperScan(value = "com.vinspier.customize.template.mapper")
 *
 **/

/**
 * mapper 继承 tk的mapper父类
 * 此时用的包扫描 应使用 tk的mapperScan
 * 不能用@org.mybatis.spring.annotation.MapperScan
 * */
@SpringBootApplication
@MapperScan(basePackages = "com.vinspier.customize.template.mapper")
public class CustomizeTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomizeTemplateApplication.class,args);
    }

}
