package partition1;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// STOPSHIP: 2019/9/5 大数相乘 阿里笔试
public class LargeNum {
    public static List<Integer> multiply(List<Integer> s1, List<Integer> s2) {
        Collections.reverse(s1);//传入集合，先倒置
        Collections.reverse(s2);//传入集合，先倒置
        int[] a = new int[s1.size() + s2.size()];//开辟一个结果空间

        for (int i = 0; i < s1.size(); i++) {//相乘的过程
            for (int j = 0; j < s2.size(); j++) {
                a[i + j] += (s1.get(i) - '0') * (s2.get(j) - '0');
            }
        }
        for (int i = 0; i < a.length - 1; i++) {//也是相乘的过程
            a[i + 1] += a[i] / 10;
            a[i] %= 10;
        }

        ArrayList<Integer> sb = new ArrayList<>();

        for (int i = 0; i < a.length - 1; i++) {
            sb.add(a[i]);
        }
        if (a[a.length - 1] != 0) {
            sb.add(a[a.length - 1]);
        }
        Collections.reverse(sb);
        return sb;
    }

    public static void main(String[] args) {
        List<Integer> s1 = Arrays.asList(1,3,1,5);
        List<Integer> s2 = Arrays.asList(1,3,1,5);

        System.out.println(s1.size()+"665");


        List<Integer> multiply = multiply(s1, s2);

        System.out.println(multiply);

    }
}
