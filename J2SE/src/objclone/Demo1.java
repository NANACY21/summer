package objclone;//对象clone

import java.util.Scanner;
import java.util.Vector;
/**
 * 该包记载遇到的坑、易错点、笔试题
 */
public class Demo1 {

    public static void main(String[] args) {
        /*
        深复制(clone)、浅复制问题
         */
        Vector<Dog> dogs=new Vector<Dog>();
        for(int i=0;i<3;i++){
            Dog dog=new Dog(1,"欢欢");
            dogs.addElement(dog);
        }
        Vector<Dog> temp= (Vector<Dog>) dogs.clone();//另开辟地址
        for(int i=0;i<temp.size();i++){
            temp.elementAt(i).setName("狗子");
        }
        System.out.println(dogs);

        /*
        输入问题
         */
        Scanner scanner = new Scanner(System.in);
        //next遇空格会被截断
        String str = scanner.next();
        str=scanner.nextLine();//nextLine接收一整行，会吃掉回车，造成还没输入就执行下一行代码的假象
//        int mm=scanner.nextInt();
        System.out.println("哈哈哈");
        scanner.close();

        /*
        new JButton("计算\t器");
        不显示空格，这时只能用空格
         */


        /*
        内存地址
         */
        Integer i1=new Integer(10);
        Integer i2=new Integer(10);//i1、i2一定不是同一内存地址
        Integer i3=128;//换成128以内
        Integer i4=128;//换成128以内
        System.out.println(i3==i4);

    }
}
