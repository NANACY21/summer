package com.matchacloud.basic.thread.threadpool;
import java.util.concurrent.*;

/**
 * 线程池拒绝策略:
 * 1 ThreadPoolExecutor.AbortPolicy()(默认) 抛出异常 主线程会执行异常从而阻塞 主线程捕获异常!!!
 * 2 ThreadPoolExecutor.CallerRunsPolicy() 调用者直接执行任务 主线程不会阻塞
 * 3 ThreadPoolExecutor.DiscardOldestPolicy() 丢弃队列中等待最久的任务(出队) 该任务入队阻塞队列
 * 4 ThreadPoolExecutor.DiscardPolicy() 直接丢弃任务 也不会抛异常
 */
public class CustomThreadPoolExecutor {
    // 核心线程数
    private static final int CORE_POOL_SIZE = 5;
    // 最大线程数
    private static final int MAX_POOL_SIZE = 10;
    // 线程空闲时间
    private static final long KEEP_ALIVE_TIME = 60L;
    // 时间单位
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    // 任务队列容量
    private static final int QUEUE_CAPACITY = 20;

    // 单例的线程池实例
    private static final ExecutorService threadPool;

    static {
        // 创建一个阻塞队列用于存放任务
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
        // 创建线程池
        threadPool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                workQueue,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     * 私有构造函数，防止外部实例化
     */
    private CustomThreadPoolExecutor() {}

    /**
     * 提交任务到线程池执行
     * @param task 要执行的任务
     */
    public static void execute(Runnable task) {
        threadPool.execute(task);
    }

    /**
     * 提交有返回值的任务到线程池执行
     * @param task 要执行的任务
     * @param <T> 任务返回值的类型
     * @return 表示任务的 Future 对象
     */
    public static <T> Future<T> submit(Callable<T> task) {
        return threadPool.submit(task);
    }

    /**
     * 关闭线程池
     */
    public static void shutdown() {
        threadPool.shutdown();
    }

    /**
     * 立即关闭线程池
     */
    public static void shutdownNow() {
        threadPool.shutdownNow();
    }

    /**
     * 检查线程池是否已关闭
     * @return 如果线程池已关闭则返回 true，否则返回 false
     */
    public static boolean isShutdown() {
        return threadPool.isShutdown();
    }

    /**
     * 检查线程池是否已终止
     * @return 如果线程池已终止则返回 true，否则返回 false
     */
    public static boolean isTerminated() {
        return threadPool.isTerminated();
    }


    /**
     * 使用方式
     * @param args
     */
    public static void main(String[] args) {
        // 提交一个简单的任务
        CustomThreadPoolExecutor.execute(() -> {
            System.out.println("执行简单任务：当前线程 " + Thread.currentThread().getName());
        });

        // 提交一个有返回值的任务
        Future<String> future = CustomThreadPoolExecutor.submit(() -> {
            System.out.println("执行有返回值的任务：当前线程 " + Thread.currentThread().getName());
            return "任务执行结果";
        });

        try {
            // 获取任务的返回值
            String result = future.get();
            System.out.println("任务返回值：" + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // 关闭线程池
        CustomThreadPoolExecutor.shutdown();
    }
}