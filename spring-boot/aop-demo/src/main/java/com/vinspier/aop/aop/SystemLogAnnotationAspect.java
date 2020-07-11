package com.vinspier.aop.aop;

import com.vinspier.aop.annotation.SystemLogAnnotation;
import com.vinspier.aop.enums.ExceptionMsgEnum;
import com.vinspier.aop.enums.SystemLogTypeEnum;
import com.vinspier.aop.pojo.SystemLog;
import com.vinspier.aop.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class SystemLogAnnotationAspect {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogAnnotationAspect.class);

    private SystemLog systemLog;

    @Autowired
    private SystemLogService logService;

    /**
     * 定义业务的切入点 匹配路径规则
     * 例如controller包下的所有类的所有public方法
     * */
    @Pointcut("@annotation(com.vinspier.aop.annotation.SystemLogAnnotation)")
    public void pointcut() {
        logger.info("[------------- 定义了注解切点位置 -------------]");
    }

    /**
     * 前置增强 在切点方法执行之前
     * */
    @Before("pointcut()")
    public void beforeMethod(JoinPoint joinPoint) throws Throwable{
        logger.info("[------------- 调用前置增强方法 -------------]");
        systemLog = new SystemLog();
        // 获取拦截方法的类、名称、参数等信息
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)){
            throw new IllegalArgumentException(ExceptionMsgEnum.PONITCUT_NOT_METHOD.getMsg());
        }
        systemLog.setType(SystemLogTypeEnum.OPERATION.getType());
        systemLog.setClassname(joinPoint.getTarget().getClass().getName());
        MethodSignature methodSignature = (MethodSignature)signature;
        systemLog.setMethod(methodSignature.getName());
        systemLog.setParams(Arrays.toString(joinPoint.getArgs()));
        systemLog.setCreateTime(LocalDateTime.now());
        Object target = joinPoint.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        SystemLogAnnotation logAnnotation = currentMethod.getAnnotation(SystemLogAnnotation.class);
        systemLog.setMessage(logAnnotation.message());
    }

    /**
     * 在切点前后增强方法
     * */
  /*  @Around("pointcut()")
    public void aroundMethod(ProceedingJoinPoint joinPoint){
        logger.info("[------------- 调用环绕增强方法 前 -------------]");
        logger.info("[------------- 反射回调切点的方法 -------------]");
        logger.info("[------------- 调用环绕增强方法 后 -------------]");
    }*/

    /**
     * 异常通知
     * */
    @AfterThrowing(value = "pointcut()",throwing = "e")
    public void afterThrowingInPointcut(JoinPoint joinPoint,Exception e) throws Throwable{
        logger.info("[------------- 调用异常通知增强方法 -------------]");
        systemLog.setMessage("pointcut method throws exception");
        systemLog.setType(SystemLogTypeEnum.ERROR.getType());
        systemLog.setExceptionMsg(getExceptionMsg(e));
    }

    /**
     * 返回通知，通知内容未result
     * */
    @AfterReturning(value = "pointcut()",returning = "result")
    public void afterReturningInPointcut(JoinPoint joinPoint,Object result){
        logger.info("[------------- 调用返回通知方法 -------------]");
    }

    /**
     * 后置增强 在切点方法执行之后
     * */
    @After("pointcut()")
    public void afterMethod(JoinPoint joinPoint){
        logger.info("[------------- 调用后置增强方法 -------------]");
        saveLog(systemLog);
    }

    private String getExceptionMsg(Exception e){
        StringWriter sw = new StringWriter();

        try {
            e.printStackTrace(new PrintWriter(sw));
        } finally {
            try {
                sw.close();
            } catch (IOException var8) {
                var8.printStackTrace();
            }
        }
        return sw.getBuffer().toString().replaceAll("\\$", "T");
    }

    private void saveLog(SystemLog log){
        logService.saveLog(log);
    }
}
