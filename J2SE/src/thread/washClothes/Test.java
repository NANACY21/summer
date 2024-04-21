package thread.washClothes;
import java.util.Vector;

/**同步锁：两个方法锁住同一个对象->这两个方法不能同时执行
 * 洗完放桶里的作用（无需给洗()、晾()的衣服参数加同步锁）：
 * 不能晾还未洗的衣服
 * 一件衣服不可能同时正在洗又正在晾、两个洗衣工不能同时洗同一件衣服、两个晾衣工不能同时晾同一件衣服
 * 避免一个洗衣工洗完一件衣服，晾衣工还没来得及晾这件衣服，另一个洗衣工又洗了一遍这件衣服
 *
 *
 * 给洗()、晾()都锁上当前洗衣工、晾衣工对象的作用：
 *
 * 锁对象是谁：衣服
 * 报错
 */
public class Test {

    public static void main(String[] args) {
        Tub tub=new Tub();

        //有2个洗衣工
        Vector<Washer> washers=new Vector<>();
        for(int i=0;i<2;i++)
            washers.addElement(new Washer(tub,6));

        //有2个晾衣工
        Vector<Hanger> hangers=new Vector<>();
        for(int i=0;i<2;i++)
            hangers.addElement(new Hanger(tub));

        //4个工人开始工作 -- 4个线程启动
        for(int i=0;i<washers.size();i++) {
            washers.elementAt(i).start();
            hangers.elementAt(i).start();
        }

    }
}
/*
sleep放前放后
 */
