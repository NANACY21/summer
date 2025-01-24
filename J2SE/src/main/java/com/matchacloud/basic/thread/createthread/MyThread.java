package com.matchacloud.basic.thread.createthread;

/**ok
 * 创建线程（Thread对象）的3种方式
 * 线程的生命周期
 */
public class MyThread extends Thread {

    /**
     * 要重写该方法
     * 不要手动调用该方法，start以自动调用
     * 线程对象.run()是Runnable接口中的run()
     */
    @Override
    public void run() {
        super.run();
    }

    public static void main(String[] args) throws Exception {

        /**
         * 创建线程方式1
         * 单继承 优点:代码少 快
         * t1现在是新建状态
         */
        Thread t1 = new Thread();

        //也可以创建继承Thread的对象
        Thread myThread = new MyThread();


        /**
         * 设置优先级，
         * 优先级高度依赖OS，不同的OS可能不同，
         * 优先级高的线程获得调用的几率大
         */
        t1.setPriority(MAX_PRIORITY);

        /**
         * 创建线程方式2
         * 参数t2为线程名，通常应该给线程起名
         */
        Thread t2 = new Thread("t2");

        /**创建线程方式3
         * 构造方法第1个参数也可以是实现了Runnable接口的对象，
         * 可以有多个 缺点:代码多
         */
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }, "t3");

        //启动线程，线程被激活，t3现在是就绪（可运行）状态，但不意味着会立即运行
        t3.start();

        /**t3运行状态
         * 该方法执行完毕 -> t3终止状态
         */
        t3.run();

        /**处于阻塞状态，
         * 线程对象进入阻塞池以停止运行一段时间，让其它线程先来，下一个线程执行，
         * 阻塞完回到就绪态，
         * 如果阻塞时间很短，未必下一个线程执行，因为俩线程都是就绪态
         * 该方法执行完毕之后再调用本线程亦未必会立即执行，因为回到就绪态了
         */
        Thread.sleep(2000);

        //让出线程，回到就绪态，都是就绪态，下一个可能还是自己
        t3.yield();

        try {
            //进入等待池 处于等待状态
            t3.wait();
            //进入锁池 找锁，有锁就回到就绪状态，没锁就等着直到锁等到
            t3.notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
