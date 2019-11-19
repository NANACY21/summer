package partition2;
import java.util.Scanner;

/**
 * 有一个人遮挡了车牌号
 * 寻找可能的作弊车牌号
 */
public class chepaihao {
    public static void main(String[] args)
    {
        Scanner cin = new Scanner(System.in);
        String str = cin.next();
        str = str.replace('*','.');

        int cnt = 0;
        int n = cin.nextInt();
        String arr[] = new String[n];
        for(int i=0;i<n;++i)
        {
            arr[i] = cin.next();
            if(arr[i].matches(str))cnt++;
        }

        System.out.println(cnt);
        for(int i=0;i<n;++i)
            if(arr[i].matches(str))
                System.out.println(arr[i]);
    }
}