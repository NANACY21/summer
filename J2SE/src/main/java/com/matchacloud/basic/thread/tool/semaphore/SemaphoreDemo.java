package com.matchacloud.basic.thread.tool.semaphore;

import java.util.concurrent.Semaphore;

/**
 * 用于控制并发线程数量 只能有3个线程同时执行 就类似线程池!!!案例：6辆汽车，停3个车位
 */
public class SemaphoreDemo {
    // 信号量：3个许可，代表3个车位
    private static final Semaphore parking = new Semaphore(3);

    public static void main(String[] args) {
        // 模拟10辆车来停车
        for (int carNo = 1; carNo <= 10; carNo++) {
            new Thread(() -> {
                try {
                    // 1. 获取车位许可，无车位则阻塞
                    parking.acquire();
                    System.out.println("车辆" + Thread.currentThread().getName() + " 驶入停车场，剩余车位：" + parking.availablePermits());

                    // 模拟停车耗时2秒
                    Thread.sleep(2000);

                    System.out.println("车辆" + Thread.currentThread().getName() + " 离开停车场");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 2. 释放许可，必须放在finally，防止死锁
                    parking.release();
                }
            }, "【" + carNo + "号车】").start();
        }
    }
}
