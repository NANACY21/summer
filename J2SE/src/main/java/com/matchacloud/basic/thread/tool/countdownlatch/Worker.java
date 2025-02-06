package com.matchacloud.basic.thread.tool.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 工人线程
 */
public class Worker implements Runnable {

    private final CountDownLatch latch;
    private final String workerName;

    public Worker(CountDownLatch latch, String workerName) {
        this.latch = latch;
        this.workerName = workerName;
    }

    @Override
    public void run() {
        try {
            System.out.println(workerName + " 开始工作...");
            // 模拟工作耗时
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println(workerName + " 完成工作。");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // 工作完成，计数器减 1
            latch.countDown();
        }
    }


    public static void main(String[] args) {
        // 假设有 3 个工人
        int workerCount = 3;
        // 初始化 CountDownLatch，计数器初始值为工人数量
        CountDownLatch latch = new CountDownLatch(workerCount);

        // 创建并启动工人线程
        for (int i = 1; i <= workerCount; i++) {
            Thread workerThread = new Thread(new Worker(latch, "工人 " + i));
            workerThread.start();
        }

        // 创建并启动老板线程
        Thread bossThread = new Thread(new Boss(latch));
        bossThread.start();
    }
}
