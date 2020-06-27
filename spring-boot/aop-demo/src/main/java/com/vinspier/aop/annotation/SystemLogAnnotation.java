package com.vinspier.aop.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})  //定义了注解声明在哪些元素之前
@Documented
public @interface SystemLogAnnotation {

    String name() default "";

    String message() default "";

}
