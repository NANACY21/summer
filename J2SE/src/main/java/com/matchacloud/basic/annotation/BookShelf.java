package com.matchacloud.basic.annotation;

/**
 * 使用注解:为注解中的属性赋值(注解使用位置:类、方法、成员变量、局部变量等位置)
 * <p>
 * 思考:
 * 1.postman请求怎么定位到controller某一方法的
 * 2.springboot主函数注解的作用 注解定义 使用 解析分别是谁做的 目的是?使用了注解 我们帮你做很多这注解相关的事
 * 3.怎么利用注解将bean注册到ioc容器
 */
@Book(author = {"李篪", "lichi"})
public class BookShelf {

}
