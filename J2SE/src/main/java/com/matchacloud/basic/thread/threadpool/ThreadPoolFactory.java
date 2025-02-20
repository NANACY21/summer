package com.matchacloud.basic.thread.threadpool;

import java.util.concurrent.*;

/**
 * 线程池工厂
 * 几种不同线程池
 * 线程池拒绝策略
 * <p>
 * 空闲线程:线程池里处于等待状态、没有正在执行任务(没有占用cpu)的线程
 * 回收线程:比如线程空闲到一定时间，某线程就会被回收即内存回收
 */
public class ThreadPoolFactory {

    /**
     * 缓存线程池
     */
    private static ExecutorService CACHED_THREAD_POOL = Executors.newCachedThreadPool();

    /**
     * 创建一个固定线程池 该线程池中 1000 个线程 该线程池重用固定数量的从共享无界队列中运行的线程
     */
    public static ExecutorService FIXED_THREAD_POOL = Executors.newFixedThreadPool(1000);

    /**
     * 单线程池
     */
    private static ExecutorService SINGLE_THREAD_POOL = Executors.newSingleThreadExecutor();

    /**
     * 定时任务线程池
     * 定时任务线程池就是用来执行定时任务的，至于每个任务是不是同一个线程执行，看具体情况
     * 如果一个任务需要执行1小时，每隔1分钟执行一次这个任务，那么定时任务线程池有可能会新开一个线程来执行这个重复的任务
     */
    private static ExecutorService SCHEDULED_THREAD_POOL = Executors.newScheduledThreadPool(10);

    /**
     * 自定义线程池
     */
    public static ThreadPoolExecutor CUSTOM_THREAD_POOL = new ThreadPoolExecutor(
            2,
            4,
            10,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1),
            Executors.defaultThreadFactory(), // 线程工厂
            new ThreadPoolExecutor.AbortPolicy() // 拒绝策略
    );

    /**
     * 线程池4种拒绝策略
     */
    public static void setRejectMode() {
        /******线程池拒绝策略(再提交任务时线程池拒绝执行)******/

        //1.默认，抛出异常，execute方法所在的线程a会捕获异常，如果catch里throw，线程a会因此终止执行
        RejectedExecutionHandler abortPolicy = new ThreadPoolExecutor.AbortPolicy();

        //2.调用者直接执行任务，主线程不会阻塞
        RejectedExecutionHandler callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();

        //3.丢弃队列中等待最久的任务(出队)，该任务入队阻塞队列
        RejectedExecutionHandler discardOldestPolicy = new ThreadPoolExecutor.DiscardOldestPolicy();

        //4.直接丢弃任务 也不会抛异常
        RejectedExecutionHandler discardPolicy = new ThreadPoolExecutor.DiscardPolicy();

        //设置线程池拒绝策略
        CUSTOM_THREAD_POOL.setRejectedExecutionHandler(abortPolicy);
    }

    /**
     * 使用方式
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            //创建10个线程任务，并提交到线程池
            for (int i = 0; i < 10; i++) {
                //新启一个线程做某件事去
                ThreadPoolFactory.CUSTOM_THREAD_POOL.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "正在执行...");
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ThreadPoolFactory.CUSTOM_THREAD_POOL.shutdown();
        }
    }
}
