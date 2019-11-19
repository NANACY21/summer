package base.str;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**字符串
 * 字符串内存地址问题
 */
public class Demo2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String s1 = "abc";
        String s2 = "abc";
//        String intern = s1.intern();
        /*String是不可变字符串，引用类型
        "abc"在常量池中，s1、s2都指向常量池中的"abc"，因此s1、s2是同一内存地址*/

        s1 = "def";
        //String是不可变字符串 --> "def"是新建的（s1指向新内存地址），s1原来的"abc"丢了，s2还是指向"abc"


        s2 = scanner.next();//若输入def，此时s1、s2非同一地址


        s1 = "abc";
        s2 = "abc";
        s1 += s2;//结果不在常量池中找
        String s3 = "abcabc";
        /*此时s1在堆空间了 <=> s1=new String(s1+s2) <=> s1=s1.concat(s2)
        因此s1和s3非同一地址*/

        System.out.println(5 + 6 + 'A');
        System.out.println(5 + "A");


        String name = "张三";
        try {
            byte[] bytes = name.getBytes("utf-8");//编码
            String gbk = new String(bytes, "gbk");//解码
            System.out.println(gbk);//这会乱码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        //2者用法一样

        /**
         * 数组扩容问题
         * 线程安全，多线程适用
         */
        StringBuffer sbf1 = new StringBuffer("abc");
        StringBuffer sbf2 = new StringBuffer("abcd");

        sbf1.append("d");//不生成新空间新字符串，节省内存了
        if ((sbf1.toString()).equals(sbf2.toString())) {

            //StringBuffer比较相等先tostring，再比
        }


        /**
         * 非线程安全，单线程适用
         * "li"+"chi" 在底层使用StringBuilder进行拼接
         */
        StringBuilder sbd = new StringBuilder();

    }
}
