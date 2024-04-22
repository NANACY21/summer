package designpattern.singleton;

/**
 * 单例写法2:懒汉式(饱汉式) 即用的时候再new 延迟加载 懒加载 非线程安全(因为可能因多线程new多次实例!!!)
 */
public class Fish {

    private static Fish fish = null;

    private Fish() {

    }

    public static Fish getFish() {
        if (null == fish) {
            fish = new Fish();
        }
        return fish;
    }
}
