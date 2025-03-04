package com.matchacloud.basic.jvm;

/**
 * java new一个对象过程：
 * 1.检查类是否加载 类对象是否进内存
 * 2.为实例对象分配堆内存
 * 3.初始化对象的成员变量默认值
 * 4.设置对象头(对象元数据信息)
 * 5.执行构造方法
 * 6.如果有引用 将创建好的对象赋值给引用变量
 */
public class NewObject {

    public static void main(String[] args) {
        NewObject newObject = new NewObject();

    }
}
