package com.matchacloud.basic.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步关键字(synchronized)介绍:
 * 1.排他锁(也叫悲观锁，锁同一个对象则不能同时执行)、可重入锁、非公平锁
 * 2.上锁/解锁自动完成，线程发生异常自动释放锁
 * 3.synchronized还可以修饰一个类
 * <p>
 * 同步关键字底层实现原理：
 * 1.多个线程 竞争同一把锁(对象) 实现并发控制
 * 2.Monitor：不是对象内部结构的一部分。可以理解为一个同步工具，也可以说是一种同步机制，每一个对象都关联着一个 Monitor 对象
 * 对象头里的同步状态标记信息可以用于关联到对象对应的 Monitor
 * 尝试获取锁：尝试获取该对象(锁)对应的 Monitor 的所有权 锁对象和Monitor平行表关系
 * 3.字节码层面的实现原理：对于synchronized方法，在方法的字节码指令中会有一个ACC_SYNCHRONIZED标志。
 * 当线程调用一个带有ACC_SYNCHRONIZED标志的方法时，JVM 会自动在方法调用前后添加获取和释放 Monitor 的操作
 * 4.同步关键字能保证内存可见性：获取到锁时，从主存读取到工作内存，释放锁时，刷回主存，
 * 5.使用同步关键字锁升级过程：随着竞争该锁的线程数量增多，会进行锁升级，偏向锁(非公平锁) 升级成 轻量级锁(CAS) 升级成 重量级锁(性能较低)
 * 6.一开始的偏向锁：在大多数情况下，锁不仅不存在多线程竞争，而且总是由同一线程多次获得。
 * 偏向锁的目的就是在只有一个线程访问同步块时，通过在对象头中记录当前线程的 ID，让该线程下次进入同步块时无需再进行锁的获取和释放操作，从而提高性能
 * <p>
 * ReentrantLock介绍:
 * 1.排他锁(悲观锁)、可重入锁、公平锁/非公平锁
 * 2.实现Lock接口的类需要手动加锁/释放锁，出现异常也不会自动释放锁，仍需要手动释放锁
 * 3.ReentrantLock类(可重入锁)是Lock接口的一个实现类 该类的一个对象作为锁，一个锁 对应 多个线程
 * <p>
 * 同步关键字和ReentrantLock的区别：
 * 1.Lock可让等待锁的线程响应中断(如果lock()获取锁失败会进入等待状态且不会停止等待，
 * 但如果lockInterruptibly()方法来尝试获取锁，获取锁失败等待则有可能不会一直等待下去，
 * 会抛异常，如果其他线程调：等待的线程.中断()则等待的线程会终止)，
 * 而synchronized不行，使用synchronized时，
 * 等待的线程会一直等待下去，不能够响应中断
 * 2.通过 Lock 可以知道有没有成功获取锁(根据lock()返回值)，而synchronized 却无法办到
 * 3.而当竞争资源非常激烈时(即有大量线程同时竞争)，此时 Lock的性能要远远优于synchronized
 * 因为使用lock接口可以中断等待的线程，
 * lock接口，如果超时未获取到锁会执行其他逻辑，这是同步关键字办不到的
 * 还有公平锁/非公平锁机制
 * lock接口还可以通过newCondition()进行精确的线程间通信
 */
public class MySynchronized {

    private static final Object lock = new Object();

    private int a;

    public static void a() {

        /**同步代码块，
         * 该代码块拿到lock对象->锁上lock对象->这才执行同步代码块内容->执行完才释放lock对象
         * 其它同步块如果也用到lock对象，需要等该代码块执行完
         *
         * synchronized修饰一个代码块，这时锁是同步关键字括号里配置的对象
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
     * <p>
     * 修饰一个方法:一个线程执行该方法时 获取锁 加锁 执行 别的线程获取锁失败阻塞 这时 锁 是方法所在类的实例对象
     * 如果创建了两个对象 两个线程分别调这两个对象的同步方法 则俩线程会同时执行方法!!!因为俩线程不是同一把锁!!!
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
     * 修饰一个静态方法:锁是类对象 只有一个
     */
    public static synchronized void d() {

        System.out.println("执行d方法");
    }

    public static void main(String[] args) {

        MySynchronized test = new MySynchronized();

        //同步锁示例
        new Thread(new Runnable() {
            @Override
            public void run() {
                MySynchronized.a();
            }
        }, "T1").run();

        new Thread(new Runnable() {
            @Override
            public void run() {
                MySynchronized.b();
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
                MySynchronized.d();
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
