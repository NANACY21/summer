package partition2;

import java.util.Scanner;

public class Wangyi {
    public static void main(String[] args) {
        int x;
        int n;
        Scanner scanner = new Scanner(System.in);
        x = scanner.nextInt();
        //给定一个数x，求一个最小的数n，S(n)>=x

    }

    /**
     * @param n
     * @return n各位数字和
     */
    public static int S(int n) {

        int sum = 0;//结果
        while (n > 0) {
            sum += n % 10;
            n /= 10; //为下一步计算准备
        }
        return sum;
    }
}
