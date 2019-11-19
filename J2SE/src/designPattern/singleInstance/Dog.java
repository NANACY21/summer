package designPattern.singleInstance;

/**
 * 单例写法1
 */
public class Dog {

    String name;
    //阻止让别人用new来对此创建对象（保证对象唯一）
    //private私有方法只能在本类使用，如果使用public的话，外面的类就可以通过点语法拿到然后创建多个，就不唯一了。这样就相当于是成员变量了
    private static Dog dog = new Dog();

    //私有化构造方法，防止通过new来创建对象
    private Dog() {

    }

    //提供一个静态的方法（不用创建对象就可以调用）来接收唯一的对象
    public static Dog getInstance() {
        return dog;//返回之前new的那个对象
    }
    /*
     * 另一单例设计模式（要用的时候才创建对象）步骤：
     * 1.定义一个私有静态成员变量，不要赋值
     * 2.私有化构造方法，防止new对象
     * 3.创建一个公开的静态方法，判断是否存在对象，如果不存在就创建一个，如果存在就直接返回
     */
}
