package com.matchacloud.basic.thread.tool.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch：
 * 1.等线程a b c都完成了各自的既定任务，线程d开始执行既定任务。不一定d需要使用a b c的返回值。
 * <p>
 * 老板线程
 * <p>
 * CountDownLatch实现原理：
 * 1.基于aqs实现 aqs内部维护一个state属性实现计数器功能 state初始值=计数器的初始值
 * 2.主线程await() 读取state是否=0 可以获取锁从而执行 否则阻塞
 * 3.countDown() cas的方式使state--, state--后若=0 则唤醒等待的线程
 * 4.利用 AQS 的共享锁机制来实现线程的等待和唤醒，从而达到协调多个线程执行顺序的目的
 */
public class Boss implements Runnable {

    private final CountDownLatch latch;

    public Boss(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println("老板等待工人完成工作...");
            // 等待所有工人完成工作，即计数器变为 0
            latch.await();
            System.out.println("所有工人都完成了工作，老板开始检查。");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
