package com.matchacloud.basic.thread.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable
 * Runnable接口有实现类FutureTask,FutureTask构造可以传递Callable!!!FutureTask对象就是一个线程
 */
public class CallableTest implements Callable<Integer> {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //未来任务
        FutureTask<Integer> futureTask = new FutureTask<>(new CallableTest());

        //线程
        Thread thread = new Thread(futureTask,"lichi");
        thread.start();
        //获取新开线程结果 当前线程会因此而阻塞!!!
        System.out.println(futureTask.get());

        System.out.println("主线程继续执行");
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("未来任务正在执行");
        Thread.sleep(5000);
        return 1024;
    }
}
