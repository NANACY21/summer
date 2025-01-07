package com.matchacloud.basic.thread.jmm;



import com.matchacloud.basic.thread.threadpool.ThreadPoolFactory;

import java.util.concurrent.ExecutorService;

/**
 * 资源类 房子
 */
public class House {
    // 房子总销售数量(单位: 套) 共享变量
    int saleCount = 0;
    //每个销售员自己卖出的房子数 其实就是一个int类型变量!!!每个销售员自己初始卖0套
    //每个线程只要操作了该变量 就会记录到该线程自己独有的ThreadLocal中
    // 每个线程各自有一份
    ThreadLocal<Integer> saleCountLocal = ThreadLocal.withInitial(() -> 0);

    /**
     * 回写到共享变量的方式
     */
    public synchronized void saleHouse() {
        ++saleCount;
    }

    public void saleHouse(boolean self) {
        saleCountLocal.set(saleCountLocal.get() + 1);
        //在finally代码块进行remove
        saleCountLocal.remove();
    }

    public static void main(String[] args) {
        ExecutorService fixedThreadPool = ThreadPoolFactory.getFixedThreadPool(3);
        House house = new House();
        for (int i = 0; i < 10; i++) {

            fixedThreadPool.submit(() -> {
                try {
                    house.saleHouse(true);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    house.saleCountLocal.remove();
                }
            });
        }
    }


}
