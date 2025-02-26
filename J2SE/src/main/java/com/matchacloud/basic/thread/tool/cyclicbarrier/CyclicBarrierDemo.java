package com.matchacloud.basic.thread.tool.cyclicbarrier;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier：
 * 1.线程a b c执行各自任务 执行完均等待 只有a b c都执行完任务时，线程d再执行任务d，此时a b c都继续执行
 * 2.CyclicBarrier和CountDownLatch都可以实现让线程 a、b、c 都执行完任务之后，再执行线程 d 的任务。但它们的实现方式和侧重点有所不同
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        // 创建CyclicBarrier，设置参与线程数量为3，并指定所有线程到达屏障后执行的任务
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("所有线程都已完成任务，开始执行任务d");
        });

        // 线程a
        new Thread(() -> {
            try {
                System.out.println("线程a开始执行任务");
                // 模拟线程a执行任务花费的时间
                Thread.sleep(2000);
                System.out.println("线程a完成任务，等待其他线程");
                // 线程a到达屏障，等待其他线程
                cyclicBarrier.await();
                // 线程a在任务d执行完后继续执行
                System.out.println("线程a继续执行后续任务");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        // 线程b
        new Thread(() -> {
            try {
                System.out.println("线程b开始执行任务");
                // 模拟线程b执行任务花费的时间
                Thread.sleep(3000);
                System.out.println("线程b完成任务，等待其他线程");
                // 线程b到达屏障，等待其他线程
                cyclicBarrier.await();
                // 线程b在任务d执行完后继续执行
                System.out.println("线程b继续执行后续任务");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        // 线程c
        new Thread(() -> {
            try {
                System.out.println("线程c开始执行任务");
                // 模拟线程c执行任务花费的时间
                Thread.sleep(1000);
                System.out.println("线程c完成任务，等待其他线程");
                // 线程c到达屏障，等待其他线程
                cyclicBarrier.await();
                // 线程c在任务d执行完后继续执行
                System.out.println("线程c继续执行后续任务");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
    }
}