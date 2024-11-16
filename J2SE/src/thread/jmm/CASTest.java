package thread.jmm;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
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

