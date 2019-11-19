package base.dataType;

import java.util.Random;

/**
 * 数据类型转换，随机数
 */
public class Demo {

    public static void main(String[] args) {

        int a = 90;
        byte b1 = 20;
        short s = 90;
        char c1 = ' ';
        double d;
        //基本数据类型赋值是把值复制一份，赋给变量，不是引用
        a = b1;//自动数据类型转换：范围更小，数据类型要兼容
        a = s;
        a = c1;
        d = a;//整型可以赋给浮点型

        //需要强制类型转换
        s = (short) a;
        b1 = (byte) a;

        //short类型在数值运算时自动提升为int
        s = (short) (s + 1);
        s += 1;//


        int x = (int) (Math.random() * 10 + 90);//90-100之间的随机数
        int xx = (int) (Math.random() * 3 + 1);//1-3之间的随机数

        Random random = new Random();
        int i = random.nextInt(4) + 18;//18-22之间随机数
        i = (int) (Math.random() * 11 + 1);//1-12之间随机数
        i = (int) (Math.random() * 30 + 1);//1-31之间随机数

        //1-100奇数相加，调试以查看summ值
        int n = 1;
        int summ = 0;
        while (n < 101) {//这样比if判断奇数效率高
            summ = summ + n;
            n = n + 2;
        }
    }
}
