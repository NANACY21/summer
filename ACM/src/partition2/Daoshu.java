package partition2;

import java.util.Scanner;

/**
 * 随意输入一个数，若为偶数，输出2-这个数之间所有偶数倒数和；
 * 若为偶数，输出1-这个数之间所有奇数倒数和
 */
public class Daoshu
{
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        System.out.println("请输入一个数");
        double shu=scan.nextDouble(),sum=0;
        if(shu%2==0)
        {
            for(int i=2;i<=shu;i++)
            {
                if(i%2==0)
                    sum+=(double)1/i;
            }
        }
        else
        {
            for(int i=1;i<=shu;i++)
            {
                if(i%2!=0)
                    sum+=(double)1/i;
            }
        }
        System.out.println(sum);
    }
}
