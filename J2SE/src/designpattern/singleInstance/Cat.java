package designpattern.singleInstance;//单例模式!!!

/**
 * 单例写法2:饿汉式 类加载就给准备好实例
 * 线程安全
 */
public class Cat {

    private static final Cat cat = new Cat();

    private Cat() {
    }

    public static Cat getCat() {
        return cat;
    }
}
