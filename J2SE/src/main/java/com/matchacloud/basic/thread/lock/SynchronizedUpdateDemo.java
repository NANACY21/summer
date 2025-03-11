package com.matchacloud.basic.thread.lock;

/**
 * synchronized 锁升级过程：
 * synchronized 所使用的锁存在锁升级的过程，这一机制是为了在不同的并发场景下尽可能地减少锁带来的性能开销
 * 其锁升级的过程依次是：无锁状态 -> 偏向锁 -> 轻量级锁 -> 重量级锁
 * <p>
 * 无锁状态：一开始对象创建没有线程竞争该锁，这时是无锁状态
 * 偏向锁：偏向锁的目的就是在只有一个线程访问同步块时，通过在对象头中记录当前线程的 ID，让该线程下次进入同步块时无需再进行锁的获取和释放操作，从而提高性能
 * 轻量级锁：当有其他线程尝试竞争已经被偏向的锁时，偏向锁就会升级为轻量级锁，轻量级锁适用于多个线程交替访问同步块的场景
 * 重量级锁：当多个线程同时竞争轻量级锁，且某个线程自旋等待一定次数后仍未获取到锁时，轻量级锁就会升级为重量级锁
 */
public class SynchronizedUpdateDemo {

    private static final Object lock = new Object();

    /**
     * 一开始对象 lock 处于无锁状态
     * 线程 1 首次获取锁时，可能会将锁升级为偏向锁
     * 线程 2 在 500 毫秒后尝试获取锁，此时若线程 1 还未释放锁，可能会使偏向锁升级为轻量级锁
     * 如果线程 2 自旋等待一段时间后仍无法获取锁，轻量级锁就会升级为重量级锁
     * 锁升级机制通过根据不同的并发场景动态调整锁的状态，尽可能减少了锁的开销，提高了系统的性能
     *
     * @param args
     */
    public static void main(String[] args) {
        // 线程 1 先获取锁
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Thread 1 acquired the lock.");
                try {
                    // 模拟线程 1 持有锁一段时间
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 线程 2 在一段时间后尝试获取锁
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                System.out.println("Thread 2 acquired the lock.");
            }
        });

        t1.start();
        t2.start();
    }
}
