package designpattern.singleInstance;

public class Main {

    public static void main(String[] args) {
        Dog star1 = Dog.getInstance();//调用类中无参数的构造方法
        star1.name = "星星";
        Dog star2 = Dog.getInstance();
        Dog star3 = Dog.getInstance();
        //如果打印的结果star2，star3不是空的，就是单例了
        System.out.println(star1.name);
        System.out.println(star2.name);
        System.out.println(star3.name);

        Cat cat1 = Cat.getCat();
        Cat cat2 = Cat.getCat();
        System.out.println(cat1 == cat2);

        Tiger tiger1 = Tiger.INSTANCE;
        Tiger tiger2 = Tiger.INSTANCE;
        System.out.println(tiger1 == tiger2);
    }
}
