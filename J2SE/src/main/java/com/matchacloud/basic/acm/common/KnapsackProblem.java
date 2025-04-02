package com.matchacloud.basic.acm.common;

/**
 * @since 2025-03-05
 * 背包问题（动态规划思想）
 */
public class KnapsackProblem {


    //问题 怎么选择宝物 不超过自身能承受最大重量下使法力最大

    public static int maxMagicPower(int[] weights, int[] values, int n) {
        int m = weights.length;
        // 创建二维数组 dp，dp[i][j] 表示前 i 个宝物，在重量不超过 j 的情况下的最大法力
        int[][] dp = new int[m + 1][n + 1];

        // 填充 dp 数组
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 前i个宝物 重量不超过j
                if (weights[i - 1] <= j) {
                    // 选择当前宝物和不选择当前宝物中的较大值
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weights[i - 1]] + values[i - 1]);
                } else {
                    // 无法选择当前宝物
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // 返回最大法力值
        return dp[m][n];
    }

    public static void main(String[] args) {
        //宝物名称
        String[] name = {"金箍棒", "九齿钉耙", "风火轮", "乾坤圈", "火尖枪"};
        //宝物重量
        int[] weight = {2, 2, 6, 5, 4};
        //宝物法力
        int[] value = {6, 3, 5, 4, 6};
        //自身可以承受的最大重量
        int  content = 10;

        // 调用方法计算最大法力值
        int result = maxMagicPower(weight, value, content);
        System.out.println("在不超过最大重量的情况下能获得的最大法力值是: " + result);
    }
}
