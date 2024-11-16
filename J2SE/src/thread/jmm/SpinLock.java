package thread.jmm;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁(加不上锁就再尝试加锁!!!)：好处不会阻塞线程 线程切换引起开销
 * 不好的是：cpu一直占用，一直自旋，消耗cpu资源
 * <p>
 * 适用场景：同步代码块执行时间非常短，线程持有锁时间非常短
 */
public class SpinLock {
    //其实就是一个线程类型变量!!!
    AtomicReference<Thread> owner = new AtomicReference<>();

    public void lock() {

        Thread current = Thread.currentThread();
        for (; ; ) {
            if (owner.compareAndSet(null, current)) {
                return;
            }
        }
    }

    public void unlock() {

        Thread current = Thread.currentThread();
        owner.compareAndSet(current, null);
    }

    public static void main(String[] args) {
        SpinLock lock = new SpinLock();
        new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lock.unlock();
        }, "A").start();

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            lock.lock();
            lock.unlock();
        }, "B").start();
    }
}
