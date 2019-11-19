package pit;

import java.util.Vector;

/*
静态成员变量属于类，
 */
public class Demo2 {
    private static int x=100;

    public static void main(String[] args) {
        Demo2 d1=new Demo2();
        d1.x++;
        Demo2 d2=new Demo2();
        System.out.println(d2.x);
        //d2.x++;
        d1=new Demo2();
        d1.x++;
        System.out.println(d1.x);
        Demo2.x--;
        System.out.println(d1.x);
        //System.out.println("x="+x);


        /**
         * 加完项哈希地址不一样了？？
         */
        Vector<String> v=new Vector<>();
        System.out.println(v.hashCode()+"an");
        v.addElement("aa");
        System.out.println(v.hashCode()+"annn");
    }
}
