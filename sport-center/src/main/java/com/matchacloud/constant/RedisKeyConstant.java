package com.matchacloud.constant;

/**
 * Redis 键名常量类
 * 统一管理所有缓存键，避免硬编码和键名冲突
 */
public class RedisKeyConstant {

    // 前缀统一规范：模块名:业务名:标识
    private static final String PREFIX = "sports:";

    // 运动员缓存相关
    public static final String ATHLETE_INFO = PREFIX + "athlete:info:"; // 运动员详情：sports:athlete:info:1001
    public static final String ATHLETE_LIST = PREFIX + "athlete:list:"; // 运动员列表：sports:athlete:list:track

    // 运动项目缓存相关
    public static final String EVENT_INFO = PREFIX + "event:info:"; // 运动项目详情：sports:event:info:2001
    public static final String EVENT_ALL = PREFIX + "event:all"; // 所有运动项目：sports:event:all

    // 通用缓存相关
    public static final String DICT_CACHE = PREFIX + "dict:"; // 字典缓存：sports:dict:status
    public static final String LOCK_PREFIX = PREFIX + "lock:"; // 分布式锁：sports:lock:athlete:1001
}
