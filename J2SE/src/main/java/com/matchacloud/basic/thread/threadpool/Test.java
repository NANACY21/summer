package thread.threadpool;

import java.util.concurrent.ExecutorService;

public class Test {
    public static void main(String[] args) {
        // 创建自定义线程池
        ExecutorService threadPool = ThreadPoolFactory.getCustomThreadPool(2, 4, 10, 1);
        try {
            for (int i = 0; i < 10; i++) {
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
