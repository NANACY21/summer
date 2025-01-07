package thread.demo;

/**ok
 * 死锁示例
 * 发生死锁的条件：嵌套锁
 * 将两个线程锁的顺序改为相同顺序可避免死锁
 */
public class DeadLock {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + "锁住lock1");
                    synchronized (lock1) {
                        System.out.println(Thread.currentThread().getName() + "锁住lock2");
                    }
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + "锁住lock2");
                    synchronized (lock2) {
                        System.out.println(Thread.currentThread().getName() + "锁住lock1");
                    }
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
