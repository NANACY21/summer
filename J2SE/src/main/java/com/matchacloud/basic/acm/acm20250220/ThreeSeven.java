package com.matchacloud.basic.acm.acm20250220;

import com.matchacloud.basic.acm.common.LIS;

import java.util.*;

/**
 * @since 2025-03-07
 */
public class ThreeSeven {
    /**
     * 流浪地球
     */
    public static void liulangdiqiu() {
        Scanner scanner = new Scanner(System.in);
        String[] s = scanner.nextLine().split(" ");
        //发动机总个数 这些发动机按位置顺序编好号0 - N-1
        int N = Integer.parseInt(s[0]);
        //所有发动机实际启动的时刻 发动机编号(下标) 的实际启动时刻
        int[] startTime= new int[N];
        //初始化 发动机均未启动
        Arrays.fill(startTime, -1);
        //手动启动发动机的总个数
        int E = Integer.parseInt(s[1]);
        //时刻有序队列 队列里的值为某时刻 对头的时刻最早
        PriorityQueue<Integer> queue = new PriorityQueue<>((s1, s2) -> s1 - s2);
        for (int i = 0; i < E; i++) {
            String[] s1 = scanner.nextLine().split(" ");
            startTime[Integer.parseInt(s1[1])] = Integer.parseInt(s1[0]);
            queue.add(Integer.parseInt(s1[0]));
        }



        while (!queue.isEmpty()) {
            //当前时刻
            Integer poll = queue.poll();
            //能确定下一时刻启动哪些发动机
            for (int i = 0; i < N; i++) {
                if (startTime[i] == poll) {
                    //把 i号发动机相邻的发动机启动
                    if (i == 0) {
                        if (startTime[1] == -1) {
                            startTime[1] = poll + 1;
                            queue.add(poll+1);
                        }
                        if(startTime[N-1] == -1) {
                            startTime[N-1] = poll + 1;
                            queue.add(poll+1);
                        }
                    }
                    else if(i==N-1){
                        if (startTime[0] == -1) {
                            startTime[0] = poll + 1;
                            queue.add(poll+1);
                        }
                        if(startTime[N-2] == -1) {
                            startTime[N-2] = poll + 1;
                            queue.add(poll+1);
                        }
                    }
                    else {
                        if (startTime[i + 1] == -1) {
                            startTime[i + 1] = poll + 1;
                            queue.add(poll+1);
                        }
                        if(startTime[i-1] == -1) {
                            startTime[i-1] = poll + 1;
                            queue.add(poll+1);
                        }
                    }
                }
            }
        }

        //最后时刻被启动的发动机的个数
        int lastCount = 0;
        //最后时刻被启动的发动机的位置编号 小->大排序
        //找出最大的启动时刻
        int maxTime=startTime[0];
        for (int i = 0; i < N; i++) {
            if(startTime[i] >maxTime) {
                maxTime = startTime[i];
            }
        }
        List<Integer> codeList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (startTime[i] == maxTime) {
                codeList.add(i);
            }
        }
        codeList.sort(Integer::compareTo);
        System.out.println(codeList.size());
        System.out.println(codeList);

    }

    /**
     * 勾股数中找出勾股元组
     */
    public static void gouguTuple() {
        Scanner scanner = new Scanner(System.in);
        int begin = Integer.parseInt(scanner.nextLine());
        int end = Integer.parseInt(scanner.nextLine());
        boolean found = false;
        for (int i = begin; i <= end; i++) {
            for (int j = i + 1; j <= end; j++) {
                int result = i * i + j * j;
                int c = (int) Math.sqrt(result);
                if (c * c == result && c <= end && areCoprime(i, j) && areCoprime(i, c) && areCoprime(j, c)) {
                    System.out.println(i + "\t" + j + "\t" + c);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("NA");
        }
    }

    public static void main(String[] args) {
        //liulangdiqiu();
        gouguTuple();
    }

    /**
     * 计算两个整数的最大公约数
     * @param a 偏小
     * @param b 偏大
     * @return
     */
    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * 判断两个整数是否互质
     * @param a 偏小
     * @param b 偏大
     * @return
     */
    public static boolean areCoprime(int a, int b) {
        return gcd(a, b) == 1;
    }

    /**
     * 学生方阵问题
     */
    public static void studentFangZhen() {
        Scanner scanner = new Scanner(System.in);
        String[] s = scanner.nextLine().split(" ");
        //矩阵行数
        int row = Integer.parseInt(s[0]);
        //矩阵列数
        int col = Integer.parseInt(s[1]);
        String[][] students = new String[row][col];
        for (int i = 0; i < row; i++) {
            String[] s1 = scanner.nextLine().split(",");
            for (int j = 0; j < s1.length; j++) {
                students[i][j] = s1[j];
            }
        }
        //矩阵中最长的位置相连的男生个数
        int maxLength = 0;
        //https://blog.csdn.net/nezhajava/article/details/146190630
        System.out.println(maxLength);
    }
}
