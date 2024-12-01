package base.exeorder;

/**
 * 动物
 */
public class Animal {

    public Animal() {
        System.out.println("Animal构造器");
    }

    {
        System.out.println("Animal构造代码块");
    }

    /**
     * 静态代码块 在类加载时调用，运行main()要加载main()所在类
     */
    static {
        System.out.println("Animal静态代码块");
    }

    public static void main(String[] args) {

        new Animal();
        System.out.println("--------------------");

        /**
         * 类只会初始化一次
         */
        new Animal();
    }
}
