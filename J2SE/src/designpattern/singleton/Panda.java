package designpattern.singleton;

/**
 * 单例写法3 使用静态内部类 饱汉式 线程安全 启动加载
 */
public class Panda {
    private Panda() {

    }

    public static Panda getPanda() {
        return InnerPanda.panda;
    }

    private static class InnerPanda {
        public static final Panda panda = new Panda();
    }


    /*
    内部类：
    InnerPanda可访问Panda所有属性和方法
    InnerPanda只允许Panda访问，还要先new InnerPanda对象

    匿名内部类：
    匿名类
    new 接口名{

    }
    静态内部类：
    作为外部类的静态成员，不可以访问外部类的非静态成员
     */
}
