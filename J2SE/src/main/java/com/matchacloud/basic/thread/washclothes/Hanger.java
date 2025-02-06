package com.matchacloud.basic.thread.washclothes;

/**
 * 晾衣工
 * 晾衣工如果判断衣服的状态以晾衣，则体现不出同步锁了
 */
public class Hanger extends Thread {

    /**
     * 晾衣工操作的桶 里面都是洗好的衣服
     */
    private Tub tub;

    /**
     * 这些晾衣工总共已晾的衣服数
     */
    public static int hanged = 0;

    public Hanger(Tub tub) {
        this.tub = tub;
    }

    /**
     * 已晾衣服数+1
     */
    public static void hangedAdd() {
        Hanger.hanged++;
    }

    /**
     * 晾衣服
     */
    public void hang() {
        try {
            //每1秒晾一件衣服
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (hanged < Washer.needWashNum) {
            Clothes clothes = tub.get();
            if (clothes == null) {
                break;
            } else {
                hang();
                System.out.println("晾衣工("+Thread.currentThread().getName() + ")晾上了一件衣服(" + clothes.getCode() + ")");
                System.out.println("这些晾衣工总共已晾的衣服数：" + Hanger.hanged);
            }
        }
        System.out.println("当前线程名(晾衣工名):" + Thread.currentThread().getName() + "已经完成晾衣任务");
    }
}
