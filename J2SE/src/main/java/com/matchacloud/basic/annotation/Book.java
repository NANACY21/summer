package com.matchacloud.basic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 5种引用数据类型:注解,类,数组,接口,枚举
 * 常见的注解:过时注解,重写注解,消除所有警告注解
 * 注解可以声明在包、类、字段、方法、局部变量、方法参数等的前面,用来对这些元素进行说明、注释
 *
 * 注解的作用:
 * 1.说明:对代码进行说明,生成doc文档(API文档)
 * 2.检查:检查代码是否符合条件@override @FunctionalInterface
 * 3.分析:对代码进行分析,起到了代替配置文件的作用(会用)
 *
 * 定义注解
 *
 * 元注解:管理注解的注解,在注解上使用元注解，则该注解被元注解管理了
 * Target元注解可以控制注解的使用位置如类、方法、字段等位置
 * Retention元注解可以控制注解的生命周期(加载位置),控制注解是否能在源码中(一定)、使用了注解的类的class文件中、内存中(需要指定管理者)出现
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Book {
    /**
     * 注解里有属性(抽象方法),定义属性是为了增强注解的作用
     * @return
     */
    String bookName() default "Java知识百科";

    String[] author();

}
