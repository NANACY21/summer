package com.matchacloud.basic.thread.washClothes;

/**晾衣工
 * 晾衣工如果判断衣服的状态以晾衣，则体现不出同步锁了
 */
public class Hanger extends Thread{
    private boolean flag;
    private Tub tub;
    private static int hanged=0;//已晾的衣服数
    public Hanger(Tub tub) {
        this.tub=tub;
        this.flag=false;
    }

    /**
     * 已晾衣服数+1
     */
    public static void HangedAdd() {
        Hanger.hanged++;
    }

    @Override
    public void run() {
//        while (true){
//            System.out.println(Hanger.hanged+"vv");
//            if(Hanger.hanged==Washer.getClothesNum())
//                break;
//            tub.get();
//        }
        for(int i=0;i<3;i++){
//            System.out.println(i+"pp");
            tub.get();
        }
    }
}
