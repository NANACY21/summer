package com.matchacloud.basic.thread.threadpool;
import java.util.concurrent.*;

/**
 * 线程池
 *
 * 线程池7大参数的解释以及合理设置参数
 * 核心线程数：对于cpu密集型任务，cpu利用率高，核心线程数=cpu核心数+1，如果核心线程数很小，
 * 再来线程到阻塞队列了，cpu会有空闲的，+1是为什么如果有任务阻塞，不利用cpu了，新来任务还能用上cpu
 * 对于i/o密集型任务，线程多阻塞，cpu利用率不高，设置成cpu核心数，cpu会有很多空闲，设置成
 * cpu核心数*2，会提高cpu利用率
 *
 * 最大线程数：对于cpu密集型任务，设置成=核心线程数，如果设置太大，会有很多线程使用cpu，
 * cpu过度使用，会频繁线程切换，降低性能
 * 对于io密集型任务，cpu利用率低，最大线程数可以大一些，不会使cpu超负荷，但不能过大
 */
public class CustomThreadPoolExecutor {

    /**
     * 核心线程数(常驻线程数量 默认可运行线程数量)
     * 核心线程在创建线程池对象后不一定会立即创建
     * 合理设置核心线程数：
     * 1.对于CPU 密集型任务：这类任务主要消耗 CPU 资源，如进行大量的计算。
     *   核心线程数可以设置为 CPU 核心数 + 1，这样可以充分利用 CPU 资源，
     *   避免过多的线程切换开销。
     *   可以通过 Runtime.getRuntime().availableProcessors() 方法获取 CPU 核心数
     * 2.IO 密集型任务：这类任务主要涉及到大量的 I/O 操作，
     *   如文件读写、网络请求等，线程在进行 I/O 操作时会处于等待状态，CPU 处于空闲状态。
     *   因此，核心线程数可以设置得大一些，通常为 CPU 核心数的 2 倍，
     *   以充分利用 CPU 在 I/O 等待期间的空闲时间
     */
    private static final int CORE_POOL_SIZE = 5;

    /**
     * 最大线程数
     * 合理设置最大线程数：
     * 1.对于 CPU 密集型任务，最大线程数可以和核心线程数设置为相同的值，
     *   因为过多的线程会导致频繁的线程切换，降低性能。
     * 2.对于 IO 密集型任务，最大线程数可以适当增大，但也不宜过大，
     *   否则会占用过多的系统资源。可以根据系统的承受能力和任务的特点进行调整。
     */
    private static final int MAX_POOL_SIZE = 10;

    /**
     * 空闲线程存活时间
     * 线程这么长时间不用就结束 空闲线程超过该时间就关闭结束线程
     * 合理设置空闲线程存活时间：
     * 1.对于任务比较频繁且执行时间较短的场景，可以将 keepAliveTime 设置得短一些，
     *   以避免过多的空闲线程占用系统资源。
     * 2.对于任务执行时间较长且不频繁的场景，可以将 keepAliveTime 设置得长一些，
     *   这样当有新任务到来时，不需要频繁地创建新线程。
     */
    private static final long KEEP_ALIVE_TIME = 60L;

    /**
     * 时间单位
     */
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    /**
     * 任务队列容量
     */
    private static final int QUEUE_CAPACITY = 20;

    /**
     * 线程工厂 用于创建线程
     */
    private static ThreadFactory threadFactory = Executors.defaultThreadFactory();

    // 单例的线程池实例
    private static final ThreadPoolExecutor threadPool;


    static {
        /**
         * 创建一个阻塞队列用于存放阻塞的任务
         * 合理的设置阻塞队列对象：
         * 1.ArrayBlockingQueue：这是一个有界队列，需要指定队列的容量。
         *   当任务数量可以预估，并且不希望线程池创建过多的线程时，可以使用该队列。
         *   例如，在对系统资源使用有严格限制的场景下，
         *   使用有界队列可以避免任务无限堆积导致系统资源耗尽。
         * 2.LinkedBlockingQueue：这是一个无界队列（也可以指定容量），
         *   如果不指定容量，队列可以无限增长。
         *   当任务数量不确定，且希望线程池按照核心线程数来处理任务时，可以使用该队列。
         *   但需要注意，如果任务提交速度远大于处理速度，可能会导致内存溢出。
         * 3.SynchronousQueue：这是一个不存储元素的队列，
         *   每个插入操作必须等待另一个线程的移除操作，反之亦然。
         *   当任务执行时间较短，且希望尽快处理任务，避免任务在队列中等待时，可以使用该队列。
         *   使用该队列时，通常需要将最大线程数设置得大一些，以处理可能到来的大量任务。
         */
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
        /**
         * 创建线程池
         *
         * 合理指定线程池的参数：有利于充分利用系统资源、提高程序性能以及避免资源耗尽
         *
         */
        threadPool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                workQueue,
                threadFactory,
                //拒绝策略 该线程池无法再执行新线程任务时的拒绝策略
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     * 私有构造函数，防止外部实例化
     */
    private CustomThreadPoolExecutor() {
    }

    /**
     * 线程池4种拒绝策略
     */
    public static void setRejectMode() {
        /******线程池拒绝策略(再提交任务时线程池拒绝执行)******/

        /**
         * 1.默认，抛出异常，execute方法所在的线程a会捕获异常，如果catch里throw，线程a会因此终止执行
         * 适用于：对任务丢失比较敏感的场景，开发者可以通过捕获异常来进行相应的处理。
         */
        RejectedExecutionHandler abortPolicy = new ThreadPoolExecutor.AbortPolicy();

        /**
         * 2.调用者直接执行任务，主线程不会阻塞
         * 适用于：这种策略可以减缓任务提交的速度，适用于不希望任务丢失，且对系统性能要求不是特别高的场景。
         */
        RejectedExecutionHandler callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();

        /**
         * 3.丢弃队列中等待最久的任务(出队)，该任务入队阻塞队列
         * 适用于：对任务的时效性要求不高的场景。
         */
        RejectedExecutionHandler discardOldestPolicy = new ThreadPoolExecutor.DiscardOldestPolicy();

        /**
         * 4.直接丢弃任务 也不会抛异常
         * 适用于：对任务丢失不太敏感的场景。
         */
        RejectedExecutionHandler discardPolicy = new ThreadPoolExecutor.DiscardPolicy();

        //设置线程池拒绝策略
        threadPool.setRejectedExecutionHandler(abortPolicy);
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