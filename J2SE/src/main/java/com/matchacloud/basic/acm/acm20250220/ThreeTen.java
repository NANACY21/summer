package com.matchacloud.basic.acm.acm20250220;

import java.util.*;

/**
 * @since 2025-03-10
 */
public class ThreeTen {

    /**
     * 最大嵌套括号深度(简单)
     */
    public static void test() {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        if (s.equals("")) {  // 字符串为空
            System.out.println(0);
            return;
        }
        Stack<Character> stack = new Stack<>();
        int i = 0;
        int max = 0;//([]{()})
        for (i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
                max = Math.max(max, stack.size());
            } else {  // 如果是右括号
                if (stack.size() == 0) {  // 第一个是右括号，直接break
                    break;
                }
                if (c == ')') {  // 判断括号是否匹配，匹配，则continue，不匹配直接break(这里放到最后整体break)
                    if (stack.pop() == '(') {
                        continue;
                    }
                } else if (c == ']') {
                    if (stack.pop() == '[') {
                        continue;
                    }
                } else {
                    if (stack.pop() == '{') {
                        continue;
                    }
                }
                break;
            }
        }
        if (i == s.length() && stack.size() == 0) {
            System.out.println(max);
        } else {
            System.out.println(0);
        }

    }

    /**
     * 虚拟理财游戏
     */
    public static void test1() {

    }

    /**
     * 开放日活动、取出尽量少的球
     */
    public static void getLittleBall() {
        //小球总数如果超过该值则拿出多余的球
        int sum;
        //桶个数
        int bucketSize;
        //桶集合
        int[] bucketList;
        Scanner in = new Scanner(System.in);
        String s1 = in.nextLine();
        sum = Integer.parseInt(s1.split(" ")[0]);
        bucketSize = Integer.parseInt(s1.split(" ")[1]);
        bucketList = new int[bucketSize];
        String[] s = in.nextLine().split(" ");
        for (int i = 0; i < bucketList.length; i++) {
            bucketList[i] = Integer.parseInt(s[i]);
        }
        //从每个桶拿出来的小球个数
        int[] result = new int[bucketList.length];
        //当前总数
        int currentSum = 0;
        for (int i = 0; i < bucketList.length; i++) {
            currentSum += bucketList[i];
        }
        //每个桶小球数量不能超过这个值
        int maxCapacity;
        //当前每个桶中数量最多的球的个数
        int currentMax = bucketList[0];
        int minCapacity = sum / bucketSize;
        //初始化
        maxCapacity = minCapacity;
        //设置该最大容量时应该拿出的球的数量
        int needGetCount = 0;
        for (int i = 0; i < bucketList.length; i++) {
            if (bucketList[i] > maxCapacity) {
                needGetCount += bucketList[i] - maxCapacity;
            }
        }
        for (int i = 1; i < bucketList.length; i++) {
            if (bucketList[i] > currentMax) {
                currentMax = bucketList[i];
            }
        }
        currentMax = currentMax - 1;
        //需要拿出多余的球
        if (currentSum > sum) {
            if (minCapacity >= currentMax) {
                maxCapacity = currentMax;
            } else {
                //遍历 i 以选取最大容量应该是多少
                for (int i = minCapacity + 1; i <= currentMax; i++) {
                    int temp = 0;
                    for (int j = 0; j < bucketList.length; j++) {
                        if (bucketList[j] > i) {
                            temp += bucketList[j] - i;
                        }
                    }
                    if (currentSum - temp <= sum && needGetCount > temp) {
                        needGetCount = temp;
                        maxCapacity = i;
                    }

                }
                for (int i = 0; i < bucketList.length; i++) {
                    if (bucketList[i] > maxCapacity) {
                        result[i] = bucketList[i] - maxCapacity;
                    }
                }
            }
        } else {
            result = new int[0];
        }
        for (int i = 0; i < bucketList.length; i++) {
            System.out.print(result[i] + "\t");
        }
    }

    public static void main(String[] args) {
        getLittleBall();
    }
}
