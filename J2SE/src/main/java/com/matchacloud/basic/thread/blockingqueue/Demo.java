package com.matchacloud.basic.thread.blockingqueue;

import java.util.concurrent.*;

/**
 * 阻塞队列：
 * 1. 线程通过操作阻塞队列实现暂停/自动唤醒!!! 应用于线程池
 * 2. 共享队列 多个线程操作 阻塞队列对象在堆中 线程共享
 * 3. 当队列是空的，从队列中获取元素的操作(一个线程干的事)将会被阻塞
 * 4. 当队列是满的，从队列中添加元素的操作(一个线程干的事)将会被阻塞，阻塞的线程可以的时候会自动唤醒
 * 5. 使用阻塞队列好处：不需要关心什么时候需要阻塞线程，什么时候需要唤醒线程，因为这一切BlockingQueue都给你一手包办了
 */
public class Demo {

    //BlockingQueue<E>是接口 其实现类：

    //由数组结构组成的有界阻塞队列
    private ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<>(10);

    //由链表结构组成的有界阻塞队列
    private LinkedBlockingQueue<String> lbq = new LinkedBlockingQueue<>();

    /**
     * 该队列中的元素只有当其指定的延迟时间到了，才能够从队列中获取到该元素
     * DelayQueue 是一个没有大小限制的队列，因此往队列中插入数据的操作(生产者)永远不会被阻塞
     * 而只有获取数据的操作(消费者)才会被阻基
     * 使用优先级队列实现的延迟无界阻塞队列
     */
    private DelayQueue dq = new DelayQueue();

    //内部控制线程同步的锁采用的是公平锁 支持优先级排序的无界阻塞队列
    private PriorityBlockingQueue pbq = new PriorityBlockingQueue();

    //不存储元素的阻塞队列，也即单个元素的队列
    private SynchronousQueue sq = new SynchronousQueue();

    //由链表组成的无界阻塞队列
    private LinkedTransferQueue ltq = new LinkedTransferQueue();

    //由链表组成的双向阻塞队列
    private LinkedBlockingDeque lbdeq = new LinkedBlockingDeque();

    /**
     * 阻塞队列中的方法
     */
    public void test() throws InterruptedException {
        //失败抛错
        abq.add("1");

        //失败抛错
        abq.remove();

        //添加 失败返回false 设置超时时间 会阻塞一段时间 超时后仍阻塞 阻塞的线程会结束退出
        abq.offer("1");

        //取数据 失败返回null 设置超时时间 会阻塞一段时间 超时后仍阻塞 阻塞的线程会结束退出
        abq.poll();

        //添加 失败 阻塞当前线程
        abq.put("1");

        //取数据 失败 阻塞当前线程
        abq.take();
    }
}