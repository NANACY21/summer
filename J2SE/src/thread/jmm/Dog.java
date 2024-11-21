package thread.jmm;

/**
 * 狗
 */
public class Dog {

    private String name;

    private int age;

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        Dog dog = new Dog("旺财", 3);
        /**
         * 多个线程对age成员变量做修改，JMM工作机制:
         * 共享变量age存储在主存，线程a 读主存数据到线程a自己的 本地内存a(jvm虚拟机栈) 中生成 共享变量副本a 同理线程b也一样
         * 线程a、b各自在自己的线程本地内存里修改变量再写回主存，让其他线程马上知道可以到主存读最新数据，这就是可见性(共享变量被修改这件事对所有线程透明)
         * 两个线程都对成员变量+1 不使用原子类修饰成员变量且不加同步锁的时候线程不安全 这就不保证了JMM-原子性
         * 线程写操作一定要在最新数据基础上改这才能保证原子性
         * 为了提升性能，编译器和处理器通常会对指令序列进行重新排序 只要程序最终结果和代码顺序执行结果一样，那么允许指令重排
         *
         *
         */
    }
}
