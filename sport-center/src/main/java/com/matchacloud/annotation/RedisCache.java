package com.matchacloud.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 自定义 Redis 缓存注解
 * 用于方法返回值自动缓存
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisCache {

    /**
     * 缓存键前缀（默认使用类名+方法名）
     */
    String prefix() default "";

    /**
     * 缓存键后缀（支持Spring EL表达式，如 "#id"）
     */
    String suffix() default "";

    /**
     * 过期时间（默认30分钟）
     */
    long timeout() default 30;

    /**
     * 时间单位（默认分钟）
     */
    TimeUnit unit() default TimeUnit.MINUTES;

    /**
     * 是否忽略空值（默认true，不缓存null结果）
     */
    boolean ignoreNull() default true;
}