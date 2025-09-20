package com.matchacloud.service;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 通用服务接口
 * 定义基础CRUD和高级操作方法
 */
public interface RedisService {

    // ============================ 字符串操作 ============================
    /**
     * 设置键值对
     */
    void set(String key, Object value);

    /**
     * 设置键值对并指定过期时间
     */
    void set(String key, Object value, long timeout, TimeUnit unit);

    /**
     * 获取值
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 获取字符串值
     */
    String getString(String key);

    // ============================ 哈希操作 ============================
    /**
     * 向哈希表中添加字段
     */
    void hset(String key, String hashKey, Object value);

    /**
     * 获取哈希表中的字段值
     */
    <T> T hget(String key, String hashKey, Class<T> clazz);

    // ============================ 集合操作 ============================
    /**
     * 向集合添加元素
     */
    Long sAdd(String key, Object... values);

    /**
     * 获取集合所有元素
     */
    <T> Set<T> sMembers(String key, Class<T> clazz);

    // ============================ 通用操作 ============================
    /**
     * 删除键
     */
    Boolean delete(String key);

    /**
     * 批量删除键
     */
    Long delete(Collection<String> keys);

    /**
     * 设置过期时间
     */
    Boolean expire(String key, long timeout, TimeUnit unit);

    /**
     * 判断键是否存在
     */
    Boolean hasKey(String key);

    /**
     * 模糊查询键
     */
    Set<String> keys(String pattern);
}
