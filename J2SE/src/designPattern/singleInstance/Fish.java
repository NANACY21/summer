package designPattern.singleInstance;

/**单例写法3 懒汉式，也叫饱汉式 即用的时候再new 延迟加载 懒加载
 * 非线程安全
 */
public class Fish {

    private static Fish fish;

    private Fish() {

    }

    public static Fish getFish() {
        if (null == fish) {
            fish = new Fish();
        }
        return fish;
    }
}
