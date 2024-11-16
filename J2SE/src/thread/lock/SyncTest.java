package thread.lock;

/**
 * 同步关键字:排他锁(锁同一个对象则不能同时执行) 可重入锁 非公平锁
 */
public class SyncTest {

    private static final Object lock = new Object();
    private int a;

    public static void a() {

        /**同步代码块，
         * 该代码块拿到lock对象->锁上lock对象->这才执行同步代码块内容->执行完才释放lock对象
         * 其它同步块如果也用到lock对象，需要等该代码块执行完
         */
        synchronized (lock) {
            System.out.println("执行a方法");
            try {
                Thread.sleep(5000);//睡5秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void b() {
        synchronized (lock) {
            System.out.println("执行b方法");
        }
    }

    /**
     * synchronized作用在实例方法，锁的是当前对象，
     * 该方法得要到锁并锁上当前对象才执行
     */
    public synchronized void c() {

        System.out.println("执行c方法");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void c_1() {

        this.a = 1;
        System.out.println("执行c_1方法");
    }


    public void c_2() {
        this.a = 2;
        System.out.println("执行c_2方法");
    }

    /**
     * synchronized作用在静态方法锁的是字节码对象
     */
    public static synchronized void d() {

        System.out.println("执行d方法");
    }

    public static void main(String[] args) {

        SyncTest test = new SyncTest();

        //同步锁示例
        new Thread(new Runnable() {
            @Override
            public void run() {
                SyncTest.a();
            }
        }, "T1").run();

        new Thread(new Runnable() {
            @Override
            public void run() {
                SyncTest.b();
            }
        }, "T2").run();

        //synchronized关键字作用在方法上的不同：
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.c();
            }
        }, "T3").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SyncTest.d();
            }
        }, "T4").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.c_1();
            }
        }, "t5").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.c_2();
            }
        }, "t6").start();
    }
}
