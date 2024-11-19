package base;

/**
 * 本类示例内容:
 * static关键字、权限修饰符:
 * 实例变量,实例方法,类变量,类方法,静态代码块(静态代码块执行时机)
 *
 * 方法重载:
 * 多个方法的方法名相同,但这些方法的参数必须不同,即参数的个数不同,参数的类型不同,或者两者都不同
 * Java编译器会根据传入参数的类型和个数来确定应该调用哪一个方法
 *
 * 哈希:
 * 为什么Vector对象add之后,hashCode不一样了
 *
 * Java spi:
 *
 * Serializable接口:
 * 可序列化的接口,凡是实现此接口的类的对象就可以通过网络或本地流进行数据的传输
 *
 * Java nio:
 *
 * 线程安全的全局唯一id:
 *
 * 一个类如果没有显式继承类,则隐式继承Object类
 */
public class OOPDemo {

    /**
     * 修饰局部变量/成员变量:
     * 此时变量变成常量,一旦赋值不能更改
     * 全局常量:final+static 修饰成员变量
     */
    private final int age = 10;

    private String name;

    /**
     * final修饰类:表示此类不能被继承 比如String不能被继承,表示String不需要去扩展
     * final修饰方法:表示此方法不能被重写,重写方法之后就只调重写之后的方法了
     */
    public final void test() {
        //static不能修饰局部变量,只能修饰成员变量
        int a = 0;
    }

    /**
     * Object类的方法
     *
     * 重写equals()目的:判断俩对象是否逻辑相等
     * 规定:逻辑相同的对象 => 哈希码一定也必须相同(怎么写以保证)!!!
     * 对象的哈希码相同时不一定逻辑相同,哈希冲突(hash码相同)时才调用equals()判断对象是否逻辑相同
     *
     * 为什么重写equals必须同时重写hashcode?
     * 若只重写equals没重写hashCode,可能会导致两个逻辑相等的对象计算出的哈希值不同,哈希值不同hashset会直接放入,
     * 从而出现hashset存在逻辑相同的对象,
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Object类的方法
     * @return
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Object类的方法
     * 对象哈希值
     * 哈希值是对象的标识,但不是唯一标识
     * 两个内存地址不一样的对象(不同的对象)的哈希值可能相同
     * @return
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Object类的方法
     * 堆空间中新分配一个内存,两个对象内存地址不同
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Object类的方法
     * 当GC要回收此对象时(还没真正被回收掉呢)调用此方法
     * finalize()可能导致内部出现循环引用,导致此对象不能被回收
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
