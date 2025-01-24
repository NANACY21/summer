package com.matchacloud.basic.thread.demo;

/**ok
 * join方法示例
 */
public class TestJoin {

    private static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("MyRunnable() begin...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("MyRunnable() end...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主函数开始...");
        Thread.currentThread().setPriority(10);

        //创建线程t1
        Thread t1 = new Thread(new MyRunnable(), "t1");
        t1.start();//启动线程"t1"

        /**join方法的重载方法
         * 等待t1线程终止，最多等2秒，join()则会一直等待线程终止，
         * 可以避免死锁
         * 使用join方法可以使main线程和t1线程合并，即线性执行，等待t1结束，main再执行
         */
        t1.join(2000);
        System.out.println("主函数结束...");
    }
}
