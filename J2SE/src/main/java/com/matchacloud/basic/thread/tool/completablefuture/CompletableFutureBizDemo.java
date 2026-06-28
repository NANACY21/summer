package com.matchacloud.basic.thread.tool.completablefuture;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CompletableFuture异步回调概述：
 * `同步`：线程a一直阻塞 等待线程b
 * `异步`：线程a继续执行，线程b到时候通知线程a
 * CompletableFuture用于实现异步回调 实现了Future接口 (未来任务)  弥补 Future 的缺陷
 * 主线程中创建有/无返回值异步任务，主线程可以在异步任务结束时获取异步任务的结束返回值以及异常信息
 * <p>
 * CompletableFuture vs ForkJoin
 * 1. ForkJoin：适合CPU 密集分治计算（大数据求和、排序）
 * 2. CompletableFuture：适合IO 密集异步编排（RPC、数据库、多接口并行调用）
 * 3. 并行流 parallelStream 底层是 ForkJoin，异步业务编排优先用 CompletableFuture
 * <p>
 * 需求：并行查询用户、积分，合并数据，异常兜底
 */
public class CompletableFutureBizDemo {

    // 自定义业务线程池
    private static final ExecutorService bizPool = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        // 1. 并行两个查询任务
        CompletableFuture<String> userFuture = CompletableFuture.supplyAsync(() -> {
            // 模拟rpc查询用户
            return "姓名：小明";
        }, bizPool);

        CompletableFuture<Integer> scoreFuture = CompletableFuture.supplyAsync(() -> {
            // 模拟积分查询
            return 1280;
        }, bizPool);

        // 2. 合并结果，异常兜底
        CompletableFuture<String> resultFuture = userFuture.thenCombine(scoreFuture,
                        (user, score) -> user + "，积分：" + score)
                .handle((res, ex) -> {
                    if (ex != null) {
                        System.err.println("查询失败：" + ex.getMessage());
                        return "数据查询异常";
                    }
                    return res;
                });

        // 3. 回调打印最终结果
        resultFuture.thenAccept(System.out::println);

        // 等待异步完成
        resultFuture.join();
        bizPool.shutdown();
    }
}
