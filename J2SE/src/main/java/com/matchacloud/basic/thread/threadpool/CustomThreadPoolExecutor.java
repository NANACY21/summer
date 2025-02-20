package com.matchacloud.basic.thread.threadpool;
import java.util.concurrent.*;

/**
 * 线程池
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
    private CustomThreadPoolExecutor() {
    }

    /**
     * 一些说明：
     * 每次调用该方法能确保线程池对象存在
     * Java 中静态代码块的特性是在类被加载的时候就会执行，而且只会执行一次
     * CustomThreadPoolExecutor类被卸载了 线程池对象才会消失
     * CustomThreadPoolExecutor 类通常是由系统类加载器加载的，
     * 系统类加载器在应用程序的整个运行期间都存在，不会被垃圾回收，
     * 所以 CustomThreadPoolExecutor 类也不会被卸载，线程池对象会一直存在，直到应用程序结束。
     * <p>
     * <p>
     * 提交任务到线程池执行
     *
     * @param task 要执行的任务
     */
    public static void execute(Runnable task) {
        threadPool.execute(task);
    }

    /**
     * 提交有返回值的任务到线程池执行
     *
     * @param task 要执行的任务
     * @param <T>  任务返回值的类型
     * @return 表示任务的 Future 对象
     */
    public static <T> Future<T> submit(Callable<T> task) {
        return threadPool.submit(task);
    }

    /**
     * 关闭线程池
     */
    public static void shutdown() {
        //调用 shutdown() 后，线程池不会再接受新任务，但会把已经提交的两个任务执行完毕，之后线程池才会真正关闭
        //和shutdownNow()都是 不再接收新任务 不再使用该线程池 但并不直接影响线程池对象在内存中的存在
        threadPool.shutdown();
    }

    /**
     * 立即关闭线程池
     */
    public static void shutdownNow() {
        //该方法会尝试立即停止线程池。
        // 它会向正在执行任务的线程发送中断信号（通过调用线程的 interrupt() 方法），
        // 尝试终止正在执行的任务，并且返回等待队列中尚未开始执行的任务列表。
        // 不过，对于正在执行的任务，不一定能保证立即停止，
        // 因为线程是否响应中断取决于任务代码中是否对中断进行了处理。
        threadPool.shutdownNow();
    }

    /**
     * 检查线程池是否已关闭
     *
     * @return 如果线程池已关闭则返回 true，否则返回 false
     */
    public static boolean isShutdown() {
        return threadPool.isShutdown();
    }

    /**
     * 检查线程池是否已终止
     *
     * @return 如果线程池已终止则返回 true，否则返回 false
     */
    public static boolean isTerminated() {
        return threadPool.isTerminated();
    }


    /**
     * 使用方式
     *
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