package objclone;//对象clone
import java.util.Vector;

/**
 *
 */
public class Demo1 {

    public static void main(String[] args) {
        //深复制(clone)、浅复制问题
        Vector<Dog> dogs = new Vector<>();
        for (int i = 0; i < 3; i++) {
            Dog dog = new Dog(1, "欢欢");
            dogs.addElement(dog);
        }
        Vector<Dog> temp = (Vector<Dog>) dogs.clone();//另开辟地址
        for (int i = 0; i < temp.size(); i++) {
            temp.elementAt(i).setName("狗子");
        }
        System.out.println(dogs);




        /*
        内存地址
         */
        Integer i1 = new Integer(10);
        Integer i2 = new Integer(10);//i1、i2一定不是同一内存地址
        Integer i3 = 128;//换成128以内
        Integer i4 = 128;//换成128以内
        System.out.println(i3 == i4);

    }
}
