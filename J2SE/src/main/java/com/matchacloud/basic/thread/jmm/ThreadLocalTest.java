package com.matchacloud.basic.thread.jmm;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * ThreadLocal案例
 * <p>
 * 任何情况下线程都不能直接操作主存
 * 多个线程操作共享变量，读到自己本地内存(虚拟机栈)后操作并写回主存
 * <p>
 * 而threadLocal不写回主存，不和别的线程共享，自己线程独有，threadLocal 对应 共享变量
 * <p>
 * ThreadLocal对象可以定义为共享变量，定义成静态常量最常用，ThreadLocal 对象本身是多线程共享的
 * 某线程给ThreadLocal对象set数据时，
 * 当前线程对象 Thread 内部有 ThreadLocalMap，以当前 ThreadLocal 实例为 key(弱引用)，存入 value(强引用)；
 * threadlocal弱引用(juc深入104 - 111 未看)
 * <p>
 * 注意事项：
 * 1. ThreadLocal 对象优先定义常量，ThreadLocal 对象存的value避免大对象，因为这些变量是线程私有，会占很多内存
 * 2. 线程池场景下，线程经常复用，如果不清理自定义threadlocal变量会影响后续业务逻辑或造成内存泄漏
 * 3. 为什么会内存泄漏：
 * 线程池任务执行完毕后没有ThreadLocal对象.remove()，线程对象存活，因此强引用的ThreadLocalMap存活，
 * 因此ThreadLocalMap里引用的entry数组存活 -> entry数组里每个entry存活，entry里的value强引用，
 * 因此value即使没用了，也不会回收->内存泄漏
 * ThreadLocal对象.remove() 会把map的对应key的value置为null -> 就会切断value的强引用
 */
public class ThreadLocalTest implements Runnable {


    // SimpleDateFormat 不是线程安全的，所以每个线程都要有⾃⼰独⽴的副本
    private static final ThreadLocal<SimpleDateFormat> formatter =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd HHmm"));

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalTest obj = new ThreadLocalTest();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(obj, "" + i);
            Thread.sleep(new Random().nextInt(1000));
            t.start();
        }
    }

    @Override
    public void run() {
        System.out.println("Thread Name=" + Thread.currentThread().getName() + "default Formatter = " + formatter.get().toPattern());
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //formatter pattern is changed here by thread, but it won'treflect to other threads
        formatter.set(new SimpleDateFormat());
        System.out.println("Thread Name=" + Thread.currentThread().getName() + " formatter = " + formatter.get().toPattern());
    }
}
