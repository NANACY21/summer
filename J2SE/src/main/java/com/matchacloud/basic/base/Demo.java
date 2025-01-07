package com.matchacloud.basic.base;//Java基础 一些其余比较杂的知识

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Base64;
import java.util.Random;
import java.util.Vector;

/**
 * 本类示例内容:一些Java基础知识
 * <p>
 * static关键字、权限修饰符:
 * 实例变量,实例方法,类变量,类方法,静态代码块(静态代码块执行时机)
 * <p>
 * 方法重载:
 * 多个方法的方法名相同,但这些方法的参数必须不同,即参数的个数不同,参数的类型不同,或者两者都不同
 * Java编译器会根据传入参数的类型和个数来确定应该调用哪一个方法
 * <p>
 * 哈希:
 * 为什么Vector对象add之后,hashCode不一样了
 * <p>
 * Java spi:
 * <p>
 * Serializable接口:
 * 可序列化的接口,凡是实现此接口的类的对象就可以通过网络或本地流进行数据的传输
 * <p>
 * Java nio:
 * <p>
 * 线程安全的全局唯一id:
 * <p>
 * 一个类如果没有显式继承类,则隐式继承Object类
 */
public class Demo {

    /**
     * final修饰局部变量/成员变量:
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
     * Object类的方法:equals()
     * <p>
     * 重写equals()目的:判断俩对象是否逻辑相等
     * 规定:逻辑相同的对象 => 哈希码一定也必须相同(怎么写以保证)
     * 对象的哈希码相同时不一定逻辑相同,哈希冲突(hash码相同)时才调用equals()判断对象是否逻辑相同
     * <p>
     * 为什么重写equals必须同时重写hashcode?
     * 若只重写equals没重写hashCode,可能会导致两个逻辑相等的对象计算出的哈希值不同,哈希值不同hashset会直接放入,
     * 从而出现hashset存在逻辑相同的对象,
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Object类的方法:toString()
     *
     * @return
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Object类的方法:hashCode() 对象哈希值
     * <p>
     * 哈希值是对象的标识,但不是唯一标识
     * 两个内存地址不一样的对象(不同的对象)的哈希值可能相同
     *
     * @return
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Object类的方法:clone() 浅克隆
     * 浅克隆：实体类有引用类型属性，当调用该方法时，会在堆新创建一个对象即不同内存地址，
     * 但是其引用类型的属性不会再被创建，即还是只有一个对象
     * 深克隆：对象和属性对象都新创建一份，要在重写的clone()里新创建属性对象并赋值
     * <p>
     * 深/浅克隆的克隆对象本身(不是其属性)都是堆空间中新分配一个内存,两个对象内存地址不同
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Object类的方法:finalize()
     * <p>
     * 当GC要回收此对象时(还没真正被回收掉呢)调用此方法
     * finalize()可能导致内部出现循环引用,导致此对象不能被回收
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    /**
     * Java基础-运算符示例
     */
    public void operator() {
        int b = 20;

        //会发生短路，后一条件不执行
        System.out.println(20 > 10 && (b = 10) > 0);

        //左右都算
        System.out.println(true & false);
        System.out.println(0 & 1);

        //这会报错
        //System.out.println(0 && 1);

        //先输出，再+1
        System.out.println(b++);

        //先+1，再输出
        System.out.println(++b);

        System.out.println(b++ + ++b + b++ + b++);
        System.out.println("b=" + b);

        //4右移一位
        System.out.println(4 >> 1);
        System.out.println(2 << 3);
        System.out.println("cc");

        //三目运算符
        System.out.println(b > 0 ? "haha" : b);
    }

    /**
     * Java数据类型转换、随机数
     */
    public void dataType() {
        int a = 90;
        byte b = 20;
        short s = 90;
        char c = ' ';
        double d;

        // 基本数据类型赋值是把值复制一份，赋给变量，不是引用
        // 自动数据类型转换：范围更小，数据类型要兼容
        a = b;
        a = s;
        a = c;

        //整型可以赋给浮点型
        d = a;

        //需要强制类型转换
        s = (short) a;
        b = (byte) a;

        //short类型在数值运算时自动提升为int
        s = (short) (s + 1);
        s += 1;

        //90-100之间的随机数
        int num1 = (int) (Math.random() * 10 + 90);
        //1-3之间的随机数
        int num2 = (int) (Math.random() * 3 + 1);

        Random random = new Random();

        //18-22之间随机数
        int i = random.nextInt(4) + 18;
        //1-12之间随机数
        i = (int) (Math.random() * 11 + 1);
        //1-31之间随机数
        i = (int) (Math.random() * 30 + 1);

        //1-100奇数相加，调试以查看sum值
        int n = 1;
        int sum = 0;
        //这样比if判断奇数效率高
        while (n < 101) {
            sum = sum + n;
            n = n + 2;
        }
    }

    /**
     * 深克隆浅克隆问题讲解
     */
    public void objCloneTest() {
        Vector<Dog> dogs = new Vector<>();
        for (int i = 0; i < 3; i++) {
            Dog dog = new Dog(1, "欢欢");
            dogs.addElement(dog);
        }
        //另开辟地址
        Vector<Dog> temp = (Vector<Dog>) dogs.clone();
        System.out.println(dogs == temp);

        for (int i = 0; i < temp.size(); i++) {
            temp.elementAt(i).setName("狗子");
        }
        //dogs的属性还是那同一个 这是浅克隆
        System.out.println(dogs);
    }

    /**
     * 内存地址的问题
     */
    public void memoryAddress() {
        Integer i1 = new Integer(10);
        //i1、i2一定不是同一内存地址
        Integer i2 = new Integer(10);

        //换成128以内
        Integer i3 = 128;
        //换成128以内
        Integer i4 = 128;
        System.out.println(i3 == i4);
    }

    /**
     * Base64编码解码
     */
    public void testBase64() {
        try {
            //按照 UTF-8 编码方式获取其字节数组，然后再传递给 Base64 编码器进行编码处理
            String s = Base64.getEncoder().encodeToString("这是一串字符串".getBytes("utf-8"));
            System.out.println("加密后的字符串" + s);

            //s解码 s1即为原来的字符串
            String s1 = new String(Base64.getDecoder().decode(s));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void testBigDecimal() {
        BigDecimal b1 = new BigDecimal("1.0");
        BigDecimal b2 = new BigDecimal("0.9");
        BigDecimal b3 = new BigDecimal("0.8");
        BigDecimal res = b1.divide(b2, 10, RoundingMode.HALF_UP);

    }
}
