package com.matchacloud.summerstarter.student.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * 定义切入点，匹配所有被 @Loggable 注解标记的方法
     */
    @Pointcut("@annotation(com.matchacloud.summerstarter.student.annotation.Loggable)")
    public void loggableMethods() {}

    /**
     * 在方法执行前打印日志
     * @param joinPoint
     */
    @Before("loggableMethods()")
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("lichi开始执行方法: {}", methodName);
    }

    /**
     * 在方法执行后打印日志
     * @param joinPoint
     */
    @After("loggableMethods()")
    public void afterMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("lichi方法执行结束: {}", methodName);
    }
}
