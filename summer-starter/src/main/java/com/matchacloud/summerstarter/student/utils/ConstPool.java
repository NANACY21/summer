package com.matchacloud.summerstarter.student.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 常量池
 */
public class ConstPool {

    /**
     * 模拟数据库中的用户信息
     */
    public static final String userName = "lixiaolong";

    /**
     * 密码在数据库里非明文存储 密码明文：19401127
     */
    public static final String password = new BCryptPasswordEncoder().encode("19401127");

    /**
     * 角色
     */
    public static final String role = "普通员工";
}
