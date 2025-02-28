package com.matchacloud.basic.thread.application;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock多Condition实现线程精细化通信，适用场景：
 * 1.多类型任务协调：当有多种不同类型的任务需要线程间协作完成，
 * 并且不同类型的任务有不同的等待和唤醒条件时，可以使用多个 Condition 实现精细化控制。
 * 例如，在一个仓库管理系统中，有进货、出货和盘点三种不同的操作，
 * 每种操作可能需要在特定条件下进行等待或唤醒
 * 2.复杂的状态管理：当系统的状态较为复杂，不同的状态变化需要不同的线程响应时，
 * 多个 Condition 可以分别对应不同的状态，从而实现更精确的线程通信
 * <p>
 * 说明：
 * 1.精细化线程通信 多condition时可能唤醒多个同类型任务的线程
 * 2.signal()只会随机唤醒一个等待的消费者线程来处理这个产品
 * 3.signalAll()会唤醒在该 Condition 上等待的所有线程。所有被唤醒的线程都会从 await() 方法处恢复执行，然后竞争获取锁，获取到锁的线程会继续执行后续代码，其他未获取到锁的线程会进入阻塞状态，等待锁被释放后再次竞争
 * 4.ReentrantLock 和 Condition 进行线程的唤醒和等待操作时，必须是同一个 Condition 对象才会起作用
 */
public class ProductManager {

    private static final int MAX_PRODUCTS = 5;

    private final Queue<Integer> products = new LinkedList<>();

    private final ReentrantLock lock = new ReentrantLock();

    // 产品非满条件
    private final Condition producer = lock.newCondition();

    // 产品非空条件
    private final Condition consumer = lock.newCondition();

    // 产品达到阈值条件
    private final Condition checker = lock.newCondition();

    private static final int THRESHOLD = 3;

    // 生产者生产产品
    public void produce() throws InterruptedException {
        lock.lock();
        try {
            // 当产品队列已满时，生产者线程等待
            while (products.size() == MAX_PRODUCTS) {
                System.out.println("产品队列已满，生产者等待...");
                producer.await();
            }
            int product = (int) (Math.random() * 100);
            products.add(product);
            System.out.println("生产者生产了产品: " + product);
            // 生产后产品非空，唤醒消费者线程
            consumer.signal();
            // 如果产品数量达到阈值，唤醒检查者线程
            if (products.size() >= THRESHOLD) {
                checker.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    // 消费者消费产品
    public void consume() throws InterruptedException {
        lock.lock();
        try {
            // 当产品队列为空时，消费者线程等待
            while (products.isEmpty()) {
                System.out.println("产品队列为空，消费者等待...");
                consumer.await();
            }
            int product = products.poll();
            System.out.println("消费者消费了产品: " + product);
            // 消费后产品非满，唤醒生产者线程
            producer.signal();
        } finally {
            lock.unlock();
        }
    }

    // 检查者检查产品数量
    public void check() throws InterruptedException {
        lock.lock();
        try {
            // 当产品数量未达到阈值时，检查者线程等待
            while (products.size() < THRESHOLD) {
                System.out.println("产品数量未达到阈值，检查者等待...");
                checker.await();
            }
            System.out.println("检查者检查到产品数量达到阈值: " + products.size());
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        ProductManager manager = new ProductManager();

        // 生产者线程
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    manager.produce();
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 消费者线程
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    manager.consume();
                    Thread.sleep(800);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 检查者线程
        Thread checker = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    manager.check();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        checker.start();

        try {
            producer.join();
            consumer.join();
            checker.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
