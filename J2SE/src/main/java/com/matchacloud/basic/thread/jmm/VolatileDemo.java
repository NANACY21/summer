package com.matchacloud.basic.thread.jmm;

/**
 * volatile讲解:
 * 1.volatile可以保证可见性、有序性
 * 2.不保证原子性，因为数据从工作内存刷回主内存这一操作在多线程下是线程不安全的
 * <p>
 * volatile如何保证可见性:
 * 1.写操作之后立刻刷回主内存，并通知其他线程取主内存数据
 * 2.读操作时线程本地内存无效，重新去主存读取数据并拷贝到本地内存
 * <p>
 * volatile如何保证有序性：
 * 1.禁止指令重排从而保证有序性(没有数据依赖关系的代码语句可能会重排序)
 * 2.通过内存屏障来禁止指令重排，内存屏障是一种特殊的指令，内存屏障是遵循了先行发生原则
 * 3.volatile变量写操作之后会插入写内存屏障，保证禁止写操作之前的读写操作重排到写操作之后、写操作之后的读写操作重排到写操作之前
 * 4.volatile变量读操作之前会插入读内存屏障，保证禁止读操作之前的读写操作重排到读操作之后、读操作之后的读写操作重排到读操作之前
 * <p>
 * volatile适用场景:
 * 1.读多写少时 并发写volatile就是废物
 * 2.单一赋值
 * 3.状态标志
 */
public class VolatileDemo {

    /**
     * 实现多线程下线程安全的读写该变量
     */
    private volatile int race = 0;

    public synchronized void increase() {
        race++;
    }

    public int getRace() {
        return race;
    }

    /**
     * 需要防止指令重排的场景
     */
    int state = 0;

    /**
     * 需要防止指令重排的场景
     */
    volatile boolean ready = false;

    /**
     * 需要防止指令重排的场景
     */
    public void write() {
        state = 42;
        ready = true;
    }

    /**
     * 需要防止指令重排的场景
     */
    public void read() {
        if (ready) {
            System.out.println("state=" + state);
        }
    }

    /**
     * 演示可见性
     */
    static volatile boolean flag = true;

    /**
     * 演示可见性
     */
    static volatile int number;

    /**
     * 演示可见性
     */
    public static void visible() {
        Thread threadA = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "开始执行");
            // 空循环
            while (flag) {

            }
            System.out.println(Thread.currentThread().getName() + "退出");
        }, "线程A");
        threadA.start();
        threadA.interrupt();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = false;
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                number++;
            }, "线程" + i).start();
        }
        System.out.println("主线程退出");
        System.out.println(number);
    }

    /**
     * 需要防止指令重排的场景
     */
    private static VolatileDemo instance;

    /**
     * 需要防止指令重排的场景
     * 以下程序多线程访问会出现问题
     */
    public static VolatileDemo getInstance() {
        if (instance == null) {
            synchronized (VolatileDemo.class) {
                if (instance == null) {
                    instance = new VolatileDemo();
                }
            }
        }
        return instance;
    }

    /**
     * 需要防止指令重排的场景 之 资源初始化
     */
    private static int resource1;
    private static int resource2;
    // 使用 volatile 防止指令重排
    private static volatile boolean initialized;

    public static void initialize() {
        resource1 = 1;
        resource2 = 2;
        initialized = true;
    }

    public static void useResources() {
        if (initialized) {
            // 使用 resource1 和 resource2
            System.out.println("Using resource1: " + resource1 + ", resource2: " + resource2);
        }
    }
}
