package thread.jmm;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * cas:compare and swap 比较并交换
 * CAS思想的落地工具类：原子类相关
 * 案例：多个线程对同一个共享变量+1操作 不用同步锁 使用原子类：工作机制就是 读共享变量数据到本地内存变量a a在本地内存+1之后
 * 看这时主存的值和原本地内存a是否一样 一样则写回主存 不一样则重来一遍上述操作(重来操作就是自旋行为)
 * 原子类工作机制和mysql乐观锁机制类似(时间戳机制  被改过时间戳会更新 到时候对比时间戳!!!)
 * 原子类如何保证原子性和线程安全:原子类=自旋锁+CAS思想=乐观锁思想!!!!!
 * 原语由若干条指令组成 这若干条指令具有原子性不可被中断;CAS比较预期值是否一样一样则回写到主存这个过程是cpu原语(这个过程可能是获取了一个cpu方面的锁)!!!
 * 也就是说CAS这个行为是有原子性的
 *
 * CAS
 * CAS缺点：1 长时间自旋导致cpu飙高 2 ABA问题(由于cas原则只比较主内存的值是否是预期值，
 * 如果期间主内存的值被其他线程修改过，那么cas却认为主内存的值没有变化，从而导致问题
 * 这就需要弄成类似于数据行有改动就更新时间戳)
 *
 */
public class CASTest {
    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(5);
        //compareAndSet()参数 期望值:回写主存时期望的主存值!!!
        //CAS是非阻塞原子性操作
        System.out.println(integer.compareAndSet(5, 2024) + "\t" + integer.get());
        System.out.println(integer.compareAndSet(5, 2024) + "\t" + integer.get());
        //重要 需要理解!!!
        integer.getAndIncrement();
    }


    /**
     * 原子引用
     */
    public void test() {
        AtomicReference<Dog> reference = new AtomicReference<>();
        Dog wangcai = new Dog("wangcai", 3);
        Dog huanhuan = new Dog("huanhuan", 4);

        reference.set(wangcai);
        System.out.println(reference.compareAndSet(wangcai, huanhuan) + "\t" + reference.get().toString());
    }

    /**
     * 避免了CAS的ABA问题的原子引用
     */
    public void test2() {
        Dog wangcai = new Dog("wangcai", 3);
        AtomicStampedReference<Dog> reference = new AtomicStampedReference<>(wangcai, 0);
        //多线程中出现CAS的ABA问题的场景!!!
    }
}

