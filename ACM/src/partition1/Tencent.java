package partition1;

import java.util.Scanner;
import java.util.Stack;

// STOPSHIP: 2019/9/1   腾讯笔试
public class Tencent {
    private static int dayNum;//这学期天数
    private static int[] score;//这学期每天评分
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String DayNum = scanner.nextLine();
        dayNum = Integer.parseInt(DayNum);
        score = new int[dayNum];
        for (int i = 0; i < dayNum; i++) {
            score[i] = scanner.nextInt();
        }
//        incr_stack();


        int result = incr_stack();
        System.out.println(result);
    }

    //计算一个区间的数的和
    public static int sum(int i, int j) {
        int sum = 0;
        for (int k = i; k <= j; k++) {
            sum += score[k];
        }
        return sum;
    }

    //INT_MIN
    public static int incr_stack() {
        Stack<Integer> s = new Stack<>();
        int sum = 0;
        int maxSum = 0;
        int n = score.length;
        for (int i = 0; i<n; i++) {
            if (s.empty() || score[i] >=score[s.lastElement()]) {//规则1
                s.push(i);
            }
            else {
                while (!s.empty() && score[s.lastElement()] >=score[i]) {//规则2
                    int top = s.lastElement();
                    s.pop();
                    int tmp=s.empty()? sum( 0, i-1) : sum(s.lastElement()+ 1, i - 1);
                    int curSum = score[top]*tmp;

                    maxSum = curSum>maxSum?curSum:maxSum;
                }
                s.push(i);
            }
        }
        while (!s.empty()) {//规则3
            int top = s.lastElement();
            s.pop();
            int tmp=s.empty()? sum(0, n-1): sum(s.lastElement()+ 1, n - 1);
            int curSum =  score[top]*tmp;
            maxSum = curSum>maxSum?curSum:maxSum;
        }
        return maxSum;
    }
}
