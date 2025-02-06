package com.matchacloud.basic.thread.washclothes;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 桶 洗衣工洗完一件衣服放到这个桶里 晾衣工从这个桶里拿衣服晾
 */
@AllArgsConstructor
public class Tub {

    /**
     * 这个桶里的衣服
     */
    private List<Clothes> clothesList;

    /**
     * 1:全是洗好的衣服 2:全是没洗的衣服
     */
    private int flag;

    public Tub() {
        this.clothesList = new ArrayList<>();
        this.flag = 1;
    }

    /**
     * 洗完往里放
     * @param clothes
     */
    public synchronized void put(Clothes clothes) {
        clothesList.add(clothes);
        Washer.washedAdd();
        //唤醒某个晾衣工
        this.notify();
    }


    /**
     * 从桶里拿，晾
     */
    public synchronized Clothes get() {
        if(flag == 2) {
            return null;
        }
        if (Hanger.hanged == Washer.needWashNum) {
            return null;
        }
        while (this.clothesList.size() == 0) {
            System.out.println("现在没有洗好的衣服，晾衣工歇一会儿");
            try {
                //当前线程等待 进入等待池
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Clothes clothes = clothesList.remove(0);
        Hanger.hangedAdd();
        return clothes;
    }

    /**
     * 从没洗的衣服的桶里拿 开始洗
     *
     * @return
     */
    public synchronized Clothes remove() {
        if (flag == 1) {
            return null;
        }
        //如果都拿完了
        if (this.clothesList.size() == 0) {
            return null;
        }
        return clothesList.remove(0);
    }
}
