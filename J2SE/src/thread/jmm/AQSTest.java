package thread.jmm;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AQS是AbstractQueuedSynchronizer的简称，它是Java并发包中的一个基础组件
 * AQS是用于实现锁和同步器的公共基础部分的抽象实现
 * 主要解决锁分配给谁的问题
 * AQS是实现juc并发编程的基石。AQS是抽象类 是基础父类
 *
 * Java并发大神 Doug Lea 设计AQS 目的是为了实现锁和同步器框架，
 * 提出统一规范并简化了锁的实现方式，AQS是模版设计思想 相当于是实现了公共需求!!!
 *
 * 自旋锁是线程自己尝试获取锁 而ReentrantLock可能是线程获取不到锁线程本身先暂停等待
 * 由AQS来自旋获取锁 来协调线程的暂停和唤醒
 *
 * AQS同步队列的结构：双向链表(方便插、删元素) 有头指针、尾指针
 * 同步队列每个节点结构：1 请求锁的线程 2 有一个属性等待状态 是描述每个线程的等待状态!!!
 * 同一个同步队列中的线程都是抢同一把锁 这同一把锁的占用(获取)状态 用volatile修饰 表示是共享变量!!!
 * 一把锁(就是一个对象实例) 对应 一个同步队列(CLH队列)
 *
 * 一开始，线程a上锁，这时锁没有被占用，线程a直接获取到锁并执行，
 * 这时 线程b上锁 利用cas机制尝试获取锁 发现失败 把线程b封装成一个Node节点入队到同步队列
 * 此时队列长这样：头节点(哨兵节点 不存储任何线程)  头节点的next是线程b
 * 此时线程b开始自旋 尝试获取锁 获取不到 则挂起 同时把头节点的waitstatus改为-1(表示头节点的next即线程b处于暂停状态)
 * 一段时间后线程a执行完毕 释放锁 释放成功后看头节点的waitstatus是-1说明有线程在等待(就是线程b) 这时唤醒线程b b再次自旋就获取锁了
 *
 * 进入同步队列的节点 如果前驱不是头节点 一律阻塞线程，如果前驱是头节点 先自旋获取锁 失败了也阻塞
 * 也就是说 前驱是头节点的线程会自旋或挂起 其余节点全是挂起!
 *
 *
 *
 *
 */
public class AQSTest {
    //这些都和AQS有关
    ReentrantLock lock = new ReentrantLock(false);

    public static void main(String[] args) {
        AQSTest aqsTest = new AQSTest();
        aqsTest.lock.lock();
        aqsTest.lock.unlock();
    }
    CountDownLatch latch;
    //锁 面向锁的使用者
    //同步器 面向锁的实现者

}
