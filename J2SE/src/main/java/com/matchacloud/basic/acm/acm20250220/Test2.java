package com.matchacloud.basic.acm.acm20250220;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Test2 {
    public static void test() {
        //服务总量
        int n = 0;
        int[][] server;//服务启动时间矩阵
        //服务k启动需要多少时间
        int k;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        server = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                server[i][j] = scanner.nextInt();

            }
        }
        k = scanner.nextInt();

        int totalTime=0;
        //服务k自身需要的时间
        int self = server[k - 1][k - 1];
        totalTime = totalTime + self;
        Queue<Integer> needcheckserver = new PriorityQueue<>();

        for (int i = n-1; i >= 0; i--) {
            if (k - 1 == i) {
                continue;
            }
            if (server[k - 1][i] != 0 &&!needcheckserver.contains(i)) {
                needcheckserver.add(i);
            }
        }
        while (!needcheckserver.isEmpty()) {
            int service = needcheckserver.poll();
            totalTime = totalTime + server[service][service];
            for (int i = n-1; i >= 0; i--) {
                if (service == i) {
                    continue;
                }
                if (server[service][i] != 0 && !needcheckserver.contains(i)) {
                    needcheckserver.add(i);
                }
            }
        }
        System.out.println(totalTime);
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new PriorityQueue<>();
        queue.add(1);
        queue.add(2);
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}
