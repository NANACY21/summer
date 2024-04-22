package designpattern.singleton;

/**
 * 单例写法4 线程安全的饱汉式 但效率低
 */
public class Pig {
    private static Pig pig;

    private Pig() {

    }

    public static Pig getPig() {
        //加了锁，谁抢到这把锁，谁就执行
        //多线程并发时，只有一个线程执行synchronized代码块
        synchronized (Pig.class) {
            if (null == pig) {
                pig = new Pig();
            }
            return pig;
        }
    }
}
