package thread.jmm;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * ThreadLocal案例!!!
 * 任何情况下线程都不能直接操作主存
 *
 * 多个线程操作共享变量，读到自己本地内存(虚拟机栈)后操作并写回主存
 * 而threadLocal不写回主存，不和别的线程共享，自己线程独有，threadLocal 对应 共享变量
 * 线程池场景下，线程经常复用，如果不清理自定义threadlocal变量会影响后续业务逻辑或造成内存泄漏
 * thread对象里有一个threadlocal成员变量，线程操作threadlocal变量时就是操作该线程自己的那一份变量
 * threadlocalmap类似hashmap，key:threadlocal对象，value:这个threadlocal对象的值
 * threadlocal弱引用(juc深入104 - 111 未看)
 *
 *
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
