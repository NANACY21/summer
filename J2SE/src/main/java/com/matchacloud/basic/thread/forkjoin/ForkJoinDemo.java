package com.matchacloud.basic.thread.forkjoin;

import java.util.concurrent.ForkJoinPool;

/**
 * 分支合并框架(分治的思想)
 * 1. Fork/join 它可以将一个大的任务拆分成多个子任务进行并行处理，最后将子任务结果合并成最后的计算结果，并进行输出
 *
 * Fork/Join框架 要完成两件事情：
 * 1. fork:把一个复杂任务进行分拆，大事化小
 * 2. Join：把分拆任务的结果进行合并
 *
 * 分支合并框架涉及的类：ForkJoinPool，Future接口，ForkJoinTask，RecursiveTask
 * 一个分支子任务就是一个线程
 */
public class ForkJoinDemo {
    public static void main(String[] args) {
        // 构造测试数组 1~1000000
        long[] array = new long[1_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        // 创建ForkJoin线程池
        ForkJoinPool pool = new ForkJoinPool();
        // 提交根任务
        SumTask rootTask = new SumTask(array, 0, array.length);
        Long total = pool.invoke(rootTask);

        System.out.println("数组总和：" + total);
        // 校验：1~1000000 等差数列和 = (1+1000000)*1000000/2 = 500000500000
    }
}
