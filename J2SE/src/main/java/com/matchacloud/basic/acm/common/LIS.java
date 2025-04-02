package com.matchacloud.basic.acm.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2025-03-05
 * 算法就是通一小块就全通了
 * 最长递增子序列 （动态规划思想）
 *
 * 动态规划思想：
 * 1.将问题分解为一系列子问题，通过记录这些子问题的解，避免重复计算，从而高效地找到原问题的最优解。
 *
 * 动态规划思想就是原问题不好解决 限制其中一个条件后变成子问题 这个子问题就好解决了
 * 原问题的解就在子问题的解中
 * 把原问题的解拆成多个子问题，子问题的解之间又有关联
 *
 */
public class LIS {

    public static List<Integer> test(int[] nums) {
        int n = nums.length;

        //以对应下标元素结尾的lis长度
        int[] dp = new int[n];

        //最终的 以对应下标元素结尾的lis的前一个元素的下标
        int[] prev = new int[n];

        // 初始化 dp 和 prev 数组
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            prev[i] = -1;
        }

        // 动态规划计算 dp 和 prev 数组
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
        }

        // 找到最长递增子序列的结尾元素索引
        int maxIndex = 0;
        for (int i = 1; i < n; i++) {
            if (dp[i] > dp[maxIndex]) {
                maxIndex = i;
            }
        }

        // 回溯重构最长递增子序列
        List<Integer> lis = new ArrayList<>();
        while (maxIndex != -1) {
            lis.add(0, nums[maxIndex]);
            maxIndex = prev[maxIndex];
        }

        return lis;
    }

    public static void main(String[] args) {
        test(new int[]{1, 3, 6, 7, 9, 4, 10, 5, 6});
    }
}
