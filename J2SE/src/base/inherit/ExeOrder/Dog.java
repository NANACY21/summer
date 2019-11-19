package base.inherit.ExeOrder;

/**
 * 子类
 */
public class Dog extends Animal {

    public Dog() {
        System.out.println("Dog构造器");
    }

    {
        System.out.println("Dog构造代码块");
    }

    static {
        System.out.println("Dog静态代码块1");
    }

    public static void main(String[] args) {

        /**
         * new时加载顺序：static->属性->构造器
         * 有继承时，依旧这个顺序，但先父类的，
         * 父类静态->子类静态->父类属性->子类属性->父构造->子构造
         *
         * 先静态后对象
         */
        new Dog();
    }

    static {
        System.out.println("Dog静态代码块2");
    }
}
