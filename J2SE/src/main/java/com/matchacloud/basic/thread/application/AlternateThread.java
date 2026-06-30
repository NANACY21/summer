package com.matchacloud.basic.thread.application;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 顺序执行：A->B->C A 全部执行完，再执行 B；B 全部执行完，最后执行 C
 * 主线程启动 A，调用A.join()阻塞主线程，A 结束后启动 B，B.join()，最后启动 C
 * <p>
 * 同时执行 消除线程启动时差，三个线程同时开始干活，不是依次 start
 * 利用CountDownLatch new CountDownLatch(1) 三个线程都await暂停
 * 然后过一会主线程countDown 同时唤醒三个线程->三个线程同时执行
 * <p>
 * 交错执行(本案例)（有序交替：A->B->C->A->B->C 循环打印）
 * state=0 -> A 执行，执行完 state=1，唤醒 B
 * state=1 -> B 执行，执行完 state=2，唤醒 C
 * state=2 -> C 执行，执行完 state=0，唤醒 A
 */
public class AlternateThread {

    //同一把锁
    static ReentrantLock lock = new ReentrantLock();

    static Condition condA = lock.newCondition();
    static Condition condB = lock.newCondition();
    static Condition condC = lock.newCondition();
    static int state = 0; // 0:A 1:B 2:C

    public static void main(String[] args) {
        // A线程
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                lock.lock();
                try {
                    while (state != 0) condA.await();
                    System.out.print("A ");
                    state = 1;
                    condB.signal();
                } catch (InterruptedException e) {
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        // B线程
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                lock.lock();
                try {
                    while (state != 1) condB.await();
                    System.out.print("B ");
                    state = 2;
                    condC.signal();
                } catch (InterruptedException e) {
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        // C线程
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                lock.lock();
                try {
                    while (state != 2) condC.await();
                    System.out.print("C ");
                    state = 0;
                    condA.signal();
                } catch (InterruptedException e) {
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }
}
