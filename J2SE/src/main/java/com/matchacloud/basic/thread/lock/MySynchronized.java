package com.matchacloud.basic.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步关键字(synchronized)介绍:
 * 1.排他锁(也叫悲观锁，锁同一个对象则不能同时执行)、可重入锁、非公平锁
 * 2.上锁/解锁自动完成，线程发生异常自动释放锁
 * 3.synchronized还可以修饰一个类
 * <p>
 * ReentrantLock介绍:
 * 1.排他锁(悲观锁)、可重入锁、公平锁/非公平锁
 * 2.实现Lock接口的类需要手动加锁/释放锁，出现异常也不会自动释放锁，仍需要手动释放锁
 * 3.ReentrantLock类(可重入锁)是Lock接口的一个实现类 该类的一个对象作为锁，一个锁 对应 多个线程
 * <p>
 * 同步关键字和ReentrantLock的区别：
 * 1.Lock可让等待锁的线程响应中断(如果lock()获取锁失败会进入等待状态且不会停止等待，
 *   但如果lockInterruptibly()方法来尝试获取锁，获取锁失败等待则有可能不会一直等待下去，
 *   会抛异常，如果其他线程调：等待的线程.中断()则等待的线程会终止)，
 *   而synchronized不行，使用synchronized时，
 *   等待的线程会一直等待下去，不能够响应中断
 * 2.通过 Lock 可以知道有没有成功获取锁(根据lock()返回值)，而synchronized 却无法办到
 * 3.而当竞争资源非常激烈时(即有大量线程同时竞争)，此时 Lock的性能要远远优于synchronized
 *   因为使用lock接口可以中断等待的线程，
 *   lock接口，如果超时未获取到锁会执行其他逻辑，这是同步关键字办不到的
 *   还有公平锁/非公平锁机制
 *   lock接口还可以通过newCondition()进行精确的线程间通信
 */
public class MySynchronized {

    private static final Object lock = new Object();
    private int a;

    /**
     *
     */
    ReentrantLock reentrantLock = new ReentrantLock();


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
