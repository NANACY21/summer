package com.matchacloud.basic.acm.acm20250220;

import java.util.*;

/**
 * @since 2025-03-06
 */
public class ThreeSix {

    /**
     * 数据分类
     * 一个数 int类型 大小是4个字节 1字节=8位
     */
    public static void dataClass() {
        Scanner scanner = new Scanner(System.in);
        String[] s = scanner.nextLine().split(" ");
        int[] a = new int[s.length];
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(s[i]);
        }
        //key:数值的类型 value:该类型的数值集合
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        int C = a[0];
        int B = a[1];
        //遍历这些数值
        for (int i = 2; i < a.length; i++) {
            //该数值的类型
            int type = sumOfBytes(a[i]) % B;
            if (!map.containsKey(type)) {
                map.put(type, new ArrayList<>());
                map.get(type).add(i);
            } else {
                map.get(type).add(i);
            }
        }

        int havetype = 0;
        int size = 0;
        for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {
            if (entry.getKey() >= C) {
                continue;
            }
            havetype = entry.getKey();
            size = entry.getValue().size();
            break;
        }
        for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {
            if (entry.getKey() >= C) {
                continue;
            }
            if (entry.getValue().size() > size) {
                havetype = entry.getKey();
                size = entry.getValue().size();
            }
        }
        System.out.println(size);

    }

    /**
     * 输出100-999之间的所有水仙花数
     */
    public static void narcissus() {
        for (int i = 100; i < 1000; i++) {
            //百位
            int baiwei = i / 100;
            //十位
            int shiwei = (i - baiwei * 100) / 10;
            //各位
            int gewei = i - baiwei * 100 - shiwei * 10;
            if (baiwei * baiwei * baiwei + shiwei * shiwei * shiwei + gewei * gewei * gewei == i) {
                System.out.print(i + "\t");
            }
        }
    }

    /**
     * 数字序列比大小
     * 贪心算法
     * 让 A 每次都尽可能地选择比 B 当前能选的最大数字更大的最小数字
     * A，B各自从数字序列中挑选出一个数字进行大小比较，赢的人得1分，输的人扣1分，相等则各自的分数不变。用过的数字需要丢弃
     * A可能赢B的最大分数
     */
    public static void compare() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        String[] s = scanner.nextLine().split(" ");
        int[] aSeq = new int[n];
        int[] bSeq = new int[n];
        for (int i = 0; i < n; i++) {
            aSeq[i] = Integer.parseInt(s[i]);
        }
        String[] s1 = scanner.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            bSeq[i] = Integer.parseInt(s1[i]);
        }
        Arrays.sort(aSeq);
        Arrays.sort(bSeq);

        int aScore = 0;
        int j = bSeq.length - 1;  // 指向 B 序列当前最大数字的索引

        for (int i = aSeq.length - 1; i >= 0; i--) {
            if (aSeq[i] > bSeq[j]) {
                aScore++;
                j--;  // B 序列中已被比较的最大数字不再考虑
            } else {
                aScore--;
            }
        }

        System.out.println(aScore);

    }

    /**
     * int类型数值4个字节相加
     *
     * @param num
     * @return
     */
    public static int sumOfBytes(int num) {
        int sum = 0;
        // 循环 4 次，每次提取一个字节
        for (int i = 0; i < 4; i++) {
            // 通过右移和按位与操作提取当前字节
            int byteValue = (num >> (i * 8)) & 0xFF;
            sum += byteValue;
        }
        return sum;
    }

    public static void main(String[] args) {
        //narcissus();
        //compare();
        dataClass();
    }
}
