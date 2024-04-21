package thread.washClothes;

import java.util.Vector;

/**
 * 桶
 * 洗衣工洗完一件衣服放到这个桶里
 */
public class Tub {
    private Vector<Clothes> v;
    private int index;//当前向量最末索引
    public Tub() {
        this.v = new Vector<>();
        this.index=-1;
    }

    /**
     * @param clothes
     */
    public synchronized void put(Clothes clothes){
        //每2秒洗一件衣服
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("当前线程名:"+Thread.currentThread().getName());
        v.addElement(clothes);
        index++;
        System.out.println(v.toString());
        System.out.println(index+"ccc");
        Washer.washedAdd();
        System.out.println("已洗完一件衣服("+clothes.getName()+")");
        this.notify();//唤醒晾衣工
    }

    /**
     * 从桶里拿，晾
     */
    public synchronized Clothes get(){
        while (this.v.size()==0){
            System.out.println(v.size()+"555666");
            try {
                this.wait();//促使当前线程进入等待池
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            //每1秒晾一件衣服
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(v.size()+"123");
        Clothes clothes=v.remove(index);
        this.index--;
        System.out.println(v);
        System.out.println(index+"ddd");
        //System.out.println("当前线程名:"+Thread.currentThread().getName());
        Hanger.HangedAdd();
        System.out.println("晾上一件衣服了("+clothes.getName()+")");
        return clothes;
    }
}
