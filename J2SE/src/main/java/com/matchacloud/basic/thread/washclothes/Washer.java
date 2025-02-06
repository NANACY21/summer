package com.matchacloud.basic.thread.washclothes;

/**
 * 洗衣工
 */
public class Washer extends Thread {
    /**
     * 已创建的该类对象的个数
     */
    private static int ObjNum = 0;

    /**
     * 这些洗衣工总共要洗的衣服数量
     */
    public static int needWashNum = 10;

    /**
     * 这些洗衣工已洗的衣服数
     */
    public static int washedNum = 0;

    /**
     * 洗衣工从这个桶里拿没洗的衣服去洗
     */
    public Tub tubNoWash;

    /**
     * 洗完放这个桶里
     */
    private Tub tub;

    public Washer(Tub tubNoWash, Tub tub) {
        this.tubNoWash = tubNoWash;
        this.tub = tub;
        Washer.ObjNum++;
    }

    /**
     * 这些洗衣工已洗的衣服数+1
     */
    public static void washedAdd() {
        Washer.washedNum++;
    }

    public void wash() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (washedNum < needWashNum) {
            //从没洗的衣服桶里拿衣服
            Clothes clothes = tubNoWash.remove();
            if (clothes == null) {
                break;
            }
            else {
                //每2秒洗一件衣服
                wash();
                //洗完放桶里
                tub.put(clothes);
                System.out.println("洗衣工("+Thread.currentThread().getName() + ")洗完了一件衣服(" + clothes.getCode() + ")");
                System.out.println("这些洗衣工总共已洗的衣服数：" + Washer.washedNum);
            }
        }
        System.out.println("当前线程名(洗衣工名):" + Thread.currentThread().getName() + "已经完成洗衣任务");
    }
}
