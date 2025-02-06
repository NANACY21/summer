package com.matchacloud.basic.thread.tool.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 老板线程
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
