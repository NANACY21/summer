package com.matchacloud.basic.thread.createthread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable
 * <p>
 * Runnable接口有实现类FutureTask,FutureTask构造可以传递Callable!!!FutureTask对象就是一个线程
 * <p>
 * callable接口创建线程在线程结束时可以返回结果 callable接口的线程会抛出异常 callable是call()
 * <p>
 * FutureTask原理：
 * 在线程A中用未来任务开启一个线程 未来任务结束后 线程A能获取到未来任务结束信息
 * 主线程开启4个未来任务 之后主线程汇总未来任务结果!!!汇总一次
 * 未来任务get()获取执行返回值 如果还没执行完获取不到!!!主线程会等未来任务都结束后才结束
 */
public class CallableTest implements Callable<Integer> {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //未来任务
        FutureTask<Integer> futureTask = new FutureTask<>(new CallableTest());

        //线程
        Thread thread = new Thread(futureTask, "lichi");
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
