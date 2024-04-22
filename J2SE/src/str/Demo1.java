package str;

import java.io.UnsupportedEncodingException;

/**
 *
 * String类:引用类型
 * 1 不能被继承
 * 2 不可变字符串是因为 其属性final修饰char数组!!!
 * 3 字符串常量池不允许存放相同字符串常量
 *
 * StringBuffer:
 * 1 可变字符串
 * 2 线程安全
 * StringBuilder:
 * 1 可变字符串
 * 2 线程不安全
 * StringBuffer/StringBuilder底层的char数组没有加final，两者用法一样!!!
 * 如果字符串拼接对象还是当前地址，如果char数组需要扩容，对象的属性char数组地址也换一下!!!
 * StringBuffer比较相等先toString，再比
 * "li"+"chi" 在底层使用StringBuilder进行拼接
 */
public class Demo1 {

    private String str1 = "6";
    //如果常量池没有"good" 则new String方式会创建2个对象:String对象和字符串常量池对象"good"
    private String str2 = new String("good");
    private char[] ch = {'a', 'b', 'c'};

    public static void main(String[] args) {
        Demo1 demo1 = new Demo1();
        demo1.change(demo1.str1, demo1.str2, demo1.ch);
        System.out.print(demo1.str1 + "\t" + demo1.str2 + "\t");
        System.out.println(demo1.ch);


        String a = "a";
        String b = "a";

        //"a"在常量池中，a、b都指向常量池中的"a"
        //a b变量指向的内存地址相同 因为String底层是常量 所以不可修改 只能读取 所以共有一个内存地址没问题!!!
        b += "bbbb";
        //此时b指向新地址 a仍指向原来地址
        //String是不可变字符串，"abbbb"是新建的(b指向新内存地址)，b原来的"a"丢了，a还是指向"a"
    }

    /**
     * 传参问题
     *
     * @param str1 String类特殊，不是一般对象的引用传递，是值传递
     * @param str2
     * @param ch 数组每个元素按引用传递
     */
    public void change(String str1, String str2, char[] ch) {
        str1 = "666";
        str2 = "10";
        ch[0] = 'g';
    }


    public void test() {
        String name = "张三";
        try {
            byte[] bytes = name.getBytes("utf-8");//编码
            String gbk = new String(bytes, "gbk");//解码
            System.out.println(gbk);//这会乱码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
