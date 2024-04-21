package operator;

/**
 * 运算符
 */
public class Demo {

    public static void main(String[] args) {
        int b = 20;
        System.out.println(20 > 10 && (b = 10) > 0);//会发生短路，后一条件不执行
        System.out.println(true & false);//左右都算
        System.out.println(0 & 1);//左右都算
//        System.out.println(0 && 1);//这会报错

        System.out.println(b++);//先输出，再+1
        System.out.println(++b);//先+1，再输出
        System.out.println(b++ + ++b + b++ + b++);//
        System.out.println("b=" + b);

        //4右移一位
        System.out.println(4 >> 1);
        System.out.println(2 << 3);
        System.out.println("cc");

        //三目运算符
        System.out.println(b > 0 ? "haha" : b);//真：返回b
    }

    Object o;//该类的方法
}
