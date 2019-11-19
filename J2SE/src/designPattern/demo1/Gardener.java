package designPattern.demo1;

/**设计模式
 *      工厂模式
 *          简单工厂模式
 *          工厂方法模式
 *          抽象工厂模式
 *
 *
 *
 *
 *
 *
 *
 * 园丁
 * 工厂模式可以结合单例模式构建对象
 */
public class Gardener {
    public void plant(Fruit fruit){//封装
        fruit.grow();
    }

    public static void main(String[] args) {
        Gardener g=new Gardener();
        g.plant(new Pear());
        /*
        利用工厂模式得到产品
        这样可以不通过new 指定对象，
        应用变量控制即可，
        把变量弄到配置文件，以后只改配置文件，不改代码，更大的解耦合了 --> 框架的原理
         */
        g.plant(FruitFactory.create("apple"));
    }
}
