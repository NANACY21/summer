package com.matchacloud.basic.thread.threadpool;

import java.util.concurrent.*;

/**
 * 线程池工厂
 */
public class ThreadPoolFactory {

    /**
     * 缓存线程池
     */
    private static ExecutorService CACHED_THREAD_POOL = Executors.newCachedThreadPool();

    //创建一个固定线程池 该线程池中 1000 个线程 该线程池重用固定数量的从共享无界队列中运行的线程
    public static ExecutorService FIXED_THREAD_POOL = Executors.newFixedThreadPool(1000);
    //单线程池
    private static ExecutorService SINGLE_THREAD_POOL = Executors.newSingleThreadExecutor();

    /**
     * 自定义线程池
     */
    public static ExecutorService CUSTOM_THREAD_POOL = new ThreadPoolExecutor(
            2,
            4,
            10,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1),
            Executors.defaultThreadFactory(), // 线程工厂
            new ThreadPoolExecutor.AbortPolicy() // 拒绝策略
    );


    /**
     * 使用方式
     * @param args
     */
    public static void main(String[] args) {
        // 创建自定义线程池
        ExecutorService threadPool = ThreadPoolFactory.CUSTOM_THREAD_POOL;
        try {
            //创建10个线程任务，并提交到线程池
            for (int i = 0; i < 10; i++) {
                //新启一个线程做某件事去
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "正在执行...");
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            threadPool.shutdown();
        }


    }




}
