package com.matchacloud.basic.thread.washclothes;
import java.util.ArrayList;
import java.util.List;

/**
 * 同步锁：两个方法锁住同一个对象->这两个方法不能同时执行
 * 洗完放桶里的作用（无需给洗()、晾()的衣服参数加同步锁）：
 * 不能晾还未洗的衣服
 * 一件衣服不可能同时正在洗又正在晾、两个洗衣工不能同时洗同一件衣服、两个晾衣工不能同时晾同一件衣服
 * 避免一个洗衣工洗完一件衣服，晾衣工还没来得及晾这件衣服，另一个洗衣工又洗了一遍这件衣服
 * <p>
 * <p>
 * 给洗()、晾()都锁上当前洗衣工、晾衣工对象的作用：
 * <p>
 * 锁对象是谁：衣服
 * 报错
 */
public class Test {

    public static void main(String[] args) {

        //一个桶 用来装洗好的衣服
        Tub tub = new Tub();

        List<Clothes> clothesList = new ArrayList<>();
        for (int i = 0; i < Washer.needWashNum; i++) {
            clothesList.add(new Clothes("衣服" + i));
        }

        //一个桶 全是没洗的衣服
        Tub tubNoWash = new Tub(clothesList, 2);

        //有2个洗衣工
        List<Washer> washers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            washers.add(new Washer(tubNoWash, tub));
        }

        //有2个晾衣工
        List<Hanger> hangers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            hangers.add(new Hanger(tub));
        }

        //4个工人开始工作 -- 4个线程启动
        for (int i = 0; i < washers.size(); i++) {
            washers.get(i).start();
            hangers.get(i).start();
        }
    }
}
