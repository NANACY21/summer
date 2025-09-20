package com.matchacloud.aspect;

import com.matchacloud.annotation.RedisCache;
import com.matchacloud.service.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Redis 缓存注解切面
 * 实现注解驱动的自动缓存逻辑
 */

@Component
@Aspect
public class RedisCacheAspect {

    @Autowired
    private RedisService redisService;

    // SpEL表达式解析器
    private final ExpressionParser parser = new SpelExpressionParser();
    // 参数名发现器
    private final DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    /**
     * 环绕通知：实现缓存逻辑
     */
    @Around("@annotation(redisCache)")
    public Object around(ProceedingJoinPoint joinPoint, RedisCache redisCache) throws Throwable {
        // 1. 生成缓存键
        String cacheKey = generateCacheKey(joinPoint, redisCache);

        // 2. 检查缓存是否存在
        Object result = redisService.get(cacheKey, Object.class);
        if (result != null) {
            return result; // 缓存命中，直接返回
        }

        // 3. 缓存未命中，执行原方法
        result = joinPoint.proceed();

        // 4. 处理缓存逻辑（忽略空值）
        if (redisCache.ignoreNull() && result == null) {
            return null;
        }

        // 5. 存入缓存
        redisService.set(cacheKey, result, redisCache.timeout(), redisCache.unit());
        return result;
    }

    /**
     * 生成缓存键（支持SpEL表达式）
     */
    private String generateCacheKey(ProceedingJoinPoint joinPoint, RedisCache redisCache) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();

        // 基础键：前缀 + 类名 + 方法名
        StringBuilder keyBuilder = new StringBuilder();
        if (redisCache.prefix().isEmpty()) {
            // 默认前缀：类名+方法名
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = method.getName();
            keyBuilder.append(className).append(":").append(methodName);
        } else {
            keyBuilder.append(redisCache.prefix());
        }

        // 拼接后缀（支持SpEL表达式）
        if (!redisCache.suffix().isEmpty()) {
            keyBuilder.append(":");
            // 解析SpEL表达式（如 "#id"、"#user.name"）
            EvaluationContext context = new StandardEvaluationContext();
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
            String suffixValue = parser.parseExpression(redisCache.suffix()).getValue(context, String.class);
            keyBuilder.append(suffixValue);
        }

        return keyBuilder.toString();
    }
}
