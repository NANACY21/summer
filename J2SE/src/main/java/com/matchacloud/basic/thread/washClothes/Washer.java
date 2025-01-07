package com.matchacloud.basic.thread.washClothes;

/**洗衣工
 */
public class Washer extends Thread{
    private static int ObjNum=0;//已创建的该类对象的个数
    private static int clothesNum;//这些洗衣工总共要洗的衣服数量
    private static int washedNum=0;//已洗的衣服数
    private Tub tub;
    private boolean flag;

    public Washer(Tub tub,int clothesNum) {
        this.tub=tub;
        this.flag=false;
        Washer.ObjNum++;
        Washer.clothesNum=clothesNum;
    }

    public static int getClothesNum() {
        return clothesNum;
    }
    public static void washedAdd() {
        Washer.washedNum++;
    }
    @Override
    public void run() {
//        for(int i=0;i<clothesNum/ObjNum+1;i++){
//            if(washedNum==clothesNum){
//                break;
//            }
//            tub.put(new Clothes("衣服"+i));
//        }
        for(int i=0;i<3;i++){
            tub.put(new Clothes("衣服"+i));
        }
    }
}
