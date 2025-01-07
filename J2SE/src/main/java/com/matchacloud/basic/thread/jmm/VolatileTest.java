package com.matchacloud.basic.thread.jmm;//JMM(Java内存模型)相关!!!

/**
 * volatile关键字保证变量可见性示例!!!
 * 多线程中使用volatile关键字以禁止指令重排(得益于内存屏障)以遵循先行发生原则!!!
 */
public class VolatileTest {
    //volatile关键字保证变量可见性示例!!!
    //这是共享变量
    static volatile boolean flag = true;
    //这是线程共享变量 volatile不能保证原子性!!!
    static volatile int number;

    //volatile适用场景：1 单一赋值 2 状态标志 3 读多写少的时候(并发写volatile就是废物!!!)

    //volatile禁用指令重排示例!!!
    int i = 0;
    volatile boolean flag2 = false;

    public void write() {
        i = 42;
        flag2 = true;
    }

    public void read() {
        if (flag2) {
            // 这里会看到42
            System.out.println("----i=" + i);
        }
    }
    public static void main(String[] args) {

        Thread threadA = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "开始执行");
            while (flag) {
                // 空循环
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
            },"线程"+String.valueOf(i)).start();
        }
        System.out.println("主线程退出");
        System.out.println(number);
    }
}
