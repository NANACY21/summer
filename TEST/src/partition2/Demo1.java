package partition2;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

// STOPSHIP: 2019/9/6 华夏满天星笔试
public class Demo1 {
    float f = 3.3f;
    //as扩充0次
    ArrayList al = new ArrayList(20);

    String str = new String("good");
    char[] ch = {'a', 'b', 'c'};

    public static void main(String[] args) {
//        Demo1 demo1 = new Demo1();
//        demo1.change(demo1.str, demo1.ch);
//        System.out.print(demo1.str + "and");
//        System.out.print(demo1.ch);


        String a = new String("ab");
        String b = new String("ab");
        String aa = "ab";
        String bb = "ab";
        if (aa == bb) {
            System.out.println("aa == bb");

        }
        if (a == b) {
            System.out.println("a==b");

        }
        if (a.equals(b)) {
            System.out.println("aEQb");

        }
        if (42 == 42.0) {
            System.out.println("true");
        }
    }

    public void change(String str, char ch[]) {
        str = "test ok";
        ch[0] = 'g';
    }

    //实现字符串替换，输入bbbwlirbbb，输出bbbhhtccc
    //取得从1970年到现在的毫秒数
    //将GB2312编码的字符串转成ISO-8859-1编码的字符串
    //Java有没有goto？goto是java中的保留字，但是没有在java中使用。
}
