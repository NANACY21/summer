package com.matchacloud.summerstarter.student.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**@Aspect:
 * 用于定义切面类，在 Spring Boot 中结合 AOP（面向切面编程）可以实现日志记录、事务管理等功能。
 * 切面类中可以定义切点（@Pointcut）和通知（如 @Before、@After、@Around 等）
 */
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
