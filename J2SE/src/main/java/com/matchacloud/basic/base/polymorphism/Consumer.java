package com.matchacloud.basic.base.polymorphism;

/**接口示例
 *
 * 用户
 */
public interface Consumer {

    //接口中只能定义常量
    public static final String id = "20166109";

    //接口没有构造方法

    /**
     * 方法是公有，无方法体
     */
    public void fly();
    public void useCredit();

    /**
     * jdk1.8新特性，对接口进行扩展，不影响其它类
     * 可以不重写
     */
    default void newMethod() {

    }

    default int newMethod2(int j) {
        return 1;
    }

    /*final、private方法不能被重写，而该方法必须被重写，
    因此接口不应该有final方法*/
    //public final void buy();
}
