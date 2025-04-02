package com.matchacloud.basic.acm.acm20250220;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @since 2025-03-05
 */
public class ThreeFive {

    /**
     * 密码输入检测
     */
    public static void passwordInputCheck() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        //解析真实密码
        ArrayList<Character> password = new ArrayList<>();
        for (char c : input.toCharArray()) {
            if (c != '<') {
                password.add(c);
            } else {
                if (password.size() > 0) {
                    password.remove(password.size() - 1);
                }
            }
        }
        //检查密码
        boolean result = true;
        //长度>=8
        if (password.size() < 8) {
            result = false;
        }
        //至少有一个大写字母
        if (!password.stream()
                .anyMatch(c -> Character.isUpperCase(c))) {
            result = false;
        }
        //至少有一个小写字母
        if (!password.stream()
                .anyMatch(c -> Character.isLowerCase(c))) {
            result = false;

        }
        //至少包含一个数字
        if (!password.stream()
                .anyMatch(c -> Character.isDigit(c))) {
            result = false;
        }
        if (!password.stream().anyMatch(c -> !Character.isLetter(c) && !Character.isDigit(c))) {
            result = false;
        }

        System.out.println(password.stream().map(String::valueOf).collect(Collectors.joining()) + "," + result);

    }

    /**
     * 转盘寿司
     */
    public static void shousi() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        //每盘寿司的价格
        String[] temp = input.split(" ");
        int[] priceList = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            priceList[i] = Integer.parseInt(temp[i]);
        }
        //依次选择每一盘寿司
        //对应实际得到的寿司总价格
        int[] realPrice = new int[priceList.length];
        for (int i = 0; i < priceList.length; i++) {
            int totalPrice = priceList[i];
            boolean yiyouhui = false;
            for (int j = i + 1; j < priceList.length; j++) {
                if (priceList[j] < priceList[i]) {
                    totalPrice = priceList[i] + priceList[j];
                    yiyouhui = true;
                    break;
                }
            }
            if (!yiyouhui) {
                for (int j = 0; j < i; j++) {
                    if (priceList[j] < priceList[i]) {
                        totalPrice = priceList[i] + priceList[j];
                        break;
                    }
                }
            }

            realPrice[i] = totalPrice;
        }
        for (int i = 0; i < realPrice.length; i++) {
            System.out.print(realPrice[i] + " ");
        }
    }

    /**
     * 汽车运输时间
     */
    public static void runTime() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int n = 0;//n辆车
        int juli = 0;//起点终点距离
        String[] s1 = s.split(" ");
        n = Integer.parseInt(s1[0]);
        juli = Integer.parseInt(s1[1]);
        //每辆车的速度
        double[] v = new double[n];
        for (int i = 0; i < n; i++) {
            String s2 = scanner.nextLine();
            v[i] = Integer.parseInt(s2);
        }
        //速度2 0时出发  速度4 1时出发
        double[] times = new double[n];//计算每辆车到达终点所用的时间
        for (int i = 0; i < n; i++) {
            int time;
            if (i == 0) {
                times[i] = juli / v[i];
            } else {
                if (v[i] < v[i - 1]) {
                    times[i] = juli / v[i];
                }
                //什么时候追上
                else {
                    //设当前车从开始动经过 x 时 追上前车
                    double x = 0;
                    x = v[i - 1] / (v[i] - v[i - 1]);
                    //始终没追上前车
                    if (x * v[i] >= juli) {
                        times[i] = juli / v[i];
                    }
                    //到终点前追上了
                    else {
                        times[i] = x + (juli - (v[i] * x)) / v[i - 1];
                    }
                }
            }
        }
        System.out.println(times[times.length - 1]);
    }

    /**
     * 最长无重复子串
     * 不包含重复字符的最长子字符串（滑动窗口算法思想）
     */
    public static int longestSubstringWithoutRepeating(String s) {
        Set<Character> set = new HashSet<>();
        int left = 0;
        int maxLength = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            while (set.contains(c)) {
                set.remove(s.charAt(left));
                left++;
            }
            set.add(c);
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        //passwordInputCheck();
        shousi();
        //runTime();
    }
}
