package com.matchacloud.basic.thread.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * 求和任务：有返回值继承 RecursiveTask
 */
public class SumTask extends RecursiveTask<Long> {
    // 阈值：数组长度小于该值直接计算，不再拆分
    private static final int THRESHOLD = 1000;
    private long[] arr;
    private int start;
    private int end;

    public SumTask(long[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        // 1. 任务足够小，直接计算
        if (end - start <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += arr[i];
            }
            return sum;
        }

        // 2. 拆分任务：二分
        int mid = (start + end) / 2;
        SumTask leftTask = new SumTask(arr, start, mid);
        SumTask rightTask = new SumTask(arr, mid, end);

        // 3. fork 异步执行左任务
        leftTask.fork();
        // 4. 右任务当前线程直接执行（优化，减少线程抢占）
        Long rightResult = rightTask.compute();
        // 5. join 等待左任务完成，获取结果
        Long leftResult = leftTask.join();

        // 6. 合并结果
        return leftResult + rightResult;
    }
}
