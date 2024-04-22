package designpattern.singleton;//单例模式!!!

/**
 * 单例写法1:饿汉式 类加载就给准备好实例 线程安全!!!
 */
public class Cat {

    //这也算内存泄漏!!! 生命周期太长了
    private static final Cat cat = new Cat();

    /**
     * 私有化构造方法
     * 阻止让别人用new来对此创建对象(保证对象唯一)
     */
    private Cat() {

    }

    /**
     * 提供一个静态的方法(不用创建对象就可以调用)来接收唯一的对象
     * @return
     */
    public static Cat getCat() {
        return cat;
    }
}
