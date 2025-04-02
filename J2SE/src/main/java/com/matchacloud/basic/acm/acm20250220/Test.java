package com.matchacloud.basic.acm.acm20250220;

import java.util.*;

/**
 * 回溯算法
 * 1.10选5 多少种选法
 * 2.一个序列 有多少种排列方式
 */
public class Test {
    public static void test() {
        Scanner scanner = new Scanner(System.in);
        //期望的楼层
        int room = scanner.nextInt();
        //序列总数
        int count = scanner.nextInt();
        int[] xulie = new int[count];
        //总共多少种序列
        int totalDiff = 1;
        for (int i = 0; i < count; i++) {
            xulie[i] = scanner.nextInt();
        }
        int temp = xulie.length;
        while (temp != 1) {
            totalDiff = totalDiff * temp;
            temp--;
        }


        //所有可能的序列
        List<List<Integer>> allXulie = new ArrayList<>();

        for (int i = 0; i < totalDiff; i++) {
            List<Integer> oneXulie = new ArrayList<>();
            allXulie.add(oneXulie);
        }



        int needXunHuan = xulie.length;



        for (int i = 0; i < xulie.length; i++) {
            //以 xulie[i]打头
            needXunHuan--;

            //这是一个序列
            List<Integer> oneXulie = new ArrayList<>();
        }
        //1423 6574 8749 3455
        //24个序列 遍历
        int index = 0;
        Queue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < allXulie.size(); i++) {
            //填充这些序列的第 index 个数字

            //取输入的序列的第 index个数字

            int jishu = allXulie.size();
            while (jishu > 0) {
                int needindex = 0;
                int cishu = totalDiff / xulie.length;
                int jishi = 0;
                if (jishi == cishu) {
                    needindex++;
                }
                while (cishu > 0) {

                    allXulie.get(i).set(index,xulie[needindex]);
                    if (jishi == 6) {

                    }
                }

                jishu--;
            }
            index++;

        }






        while (needXunHuan != 1) {

            for (int i = 0; i < needXunHuan; i++) {

            }
            needXunHuan--;
        }

        //  126 162 216 261 612 621  2 6
        //c32
    }
}
