package thread.threadpool;


import java.util.concurrent.*;

/**
 * 线程池工厂
 *
 * 线程池拒绝策略:
 * 1 ThreadPoolExecutor.AbortPolicy()(默认) 抛出异常 主线程会执行异常从而阻塞 主线程捕获异常!!!
 * 2 ThreadPoolExecutor.CallerRunsPolicy() 调用者直接执行任务 主线程不会阻塞
 * 3 ThreadPoolExecutor.DiscardOldestPolicy() 丢弃队列中等待最久的任务(出队) 该任务入队阻塞队列
 * 4 ThreadPoolExecutor.DiscardPolicy() 直接丢弃任务 也不会抛异常
 */
public class ThreadPoolFactory {

    //缓存线程池
    private static ExecutorService cachedThreadPool = null;
    //固定线程池
    private static ExecutorService fixedThreadPool = null;
    //单线程池
    private static ExecutorService singleThreadPool = null;

    //自定义线程池
    private static ExecutorService customThreadPool = null;


    /**
     * 获取缓存线程池
     * @return
     */
    public static ExecutorService getCachedThreadPool() {
        if (null == cachedThreadPool) {
            cachedThreadPool = Executors.newCachedThreadPool();
        }
        return cachedThreadPool;
    }

    /**
     * 获取固定线程池
     * @return
     */
    public static ExecutorService getFixedThreadPool(int corePoolSize) {
        if (null == fixedThreadPool) {
            fixedThreadPool = Executors.newFixedThreadPool(corePoolSize);
        }
        return fixedThreadPool;
    }

    /**
     * 获取单线程池
     * @return
     */
    public static ExecutorService getSingleThreadPool() {
        if (null == singleThreadPool) {
            singleThreadPool = Executors.newSingleThreadExecutor();
        }
        return singleThreadPool;
    }


    /**
     * 自定义线程池
     * @param corePoolSize 核心线程数
     * @param maxPoolSize 最大线程数
     * @param keepAliveTime 空闲时间(秒)
     * @param blockQueueSize 任务队列长度
     * @return
     */
    public static ExecutorService getCustomThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime,int blockQueueSize) {

        if (null == customThreadPool) {
            // 创建自定义线程池
            customThreadPool = new ThreadPoolExecutor(
                    corePoolSize,
                    maxPoolSize,
                    keepAliveTime,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(blockQueueSize),
                    Executors.defaultThreadFactory(), // 线程工厂
                    new ThreadPoolExecutor.AbortPolicy() // 拒绝策略
            );
        }
        return customThreadPool;


    }




}
