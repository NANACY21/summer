package com.matchacloud.basic.acm.acm20250220;

import java.util.*;

/**
 * @since 2025-03-04
 */
public class ThreeFour {

    public static void test(String word) {
        if ("".equals(word)) {
            return;
        }
        String[] s1 = word.split(" ");
        if(s1.length == 0) {
            return;
        }
        for (int i = 0; i < s1.length; i++) {
            Stack<Character> stack = new Stack<>();
            for (int j = 0; j < s1[i].length(); j++) {
                stack.push(s1[i].charAt(j));
            }
            String a = "";
            while (!stack.empty()) {
                a += stack.pop();
            }
            System.out.print(a+" ");
        }
    }

    /**
     * 寻找最优的路测线路
     * 一个二维整数数组 从左上角到右下角的路线 不能斜这走 路线的评分为路线中最小的数 找出评分最高的路线
     * 为什么只向右或下走就能保证得出结果？
     * 因为是左上到右下 所有线路能保证得出结果
     * 在二维数组或网格中，从左上角的起点到右下角的终点，向右或向下移动是一种合理且有效的策略，能够覆盖从起点到终点的所有可能路径中的一部分有效路径
     */
    public static void test2() {
        Scanner scanner = new Scanner(System.in);
        int row;
        int col;
        int[][] table = null;
        row = Integer.parseInt(scanner.nextLine());
        col = Integer.parseInt(scanner.nextLine());
        table = new int[row][col];
        for (int i = 0; i < row; i++) {
            String s = scanner.nextLine();
            String[] s1 = s.split(" ");
            for (int j = 0; j < s1.length; j++) {
                table[i][j] = Integer.parseInt(s1[j]);
            }
        }
        //入队后自动调整顺序
        PriorityQueue<Route> queue = new PriorityQueue<>((s1, s2) -> s2.getScore() - s1.getScore());
        queue.add(new Route(0, 0, table[0][0]));
        //最优线路的评分
        int result=0;
        while (!queue.isEmpty()) {
            //出队
            Route route = queue.poll();
            if(route.getX()==row-1 && route.getY()==col-1) {
                result = route.getScore() > result ? route.getScore() : result;
                continue;
            }

            //扩展两条线路 向下 向右
            Route newRoute = new Route(route.getX(), route.getY() + 1, 0);

            if(newRoute.getX()<=row-1 && newRoute.getY()<=col-1) {
                newRoute.setScore(Math.min(route.getScore(), table[route.getX()][route.getY() + 1]));
                queue.add(newRoute);
            }


            newRoute = new Route(route.getX() + 1, route.getY(), 0);
            if(newRoute.getX()<=row-1 && newRoute.getY()<=col-1) {
                newRoute.setScore(Math.min(route.getScore(), table[route.getX() + 1][route.getY()]));
                queue.add(newRoute);
            }
        }
        System.out.println(result);
    }
    public static int test(){
        int i = 0;
        try {
            while (i<10){
                if (i==5){
                    return i;
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            i = i+1;
        }
        return i;
    }

    public static void main(String[] args) {
        System.out.println(test());
    }


    /**
     * 篮球比赛分队问题(200分)
     */
    public static void basketball() {
        Scanner scanner = new Scanner(System.in);
        //这10个运动员的战斗力
        Integer[] personSkill = new Integer[10];
        String[] s = scanner.nextLine().split(" ");
        for (int i = 0; i < s.length; i++) {
            personSkill[i] = Integer.parseInt(s[i]);
        }
        //总数
        int sum = sum(Arrays.asList(personSkill));
        //5人一队 怎么分队 使两队战斗力差值最小
        List<List<Integer>> combine = combine(personSkill, 5);

        int minDiff = sum;
        for (int i = 0; i < combine.size(); i++) {
            //其中一队的战斗力
            int sum1 = sum(combine.get(i));
            //另一队的战斗力
            int i1 = sum - sum1;
            if (i1 >= sum1) {
                minDiff = Math.min(minDiff, i1 - sum1);
            }
            else {
                minDiff = Math.min(minDiff, sum1 - i1);
            }
        }
        System.out.println(minDiff);

    }

    /**
     * 10个人中选 k 个人
     * @param nums
     * @param k
     * @return 所有选法
     */
    public static List<List<Integer>> combine(Integer[] nums, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return result;
        }
        backtrack(nums, k, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(Integer[] nums, int k, int start, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);
            backtrack(nums, k, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }


    private static int sum(List<Integer> data) {
        return data.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }


}
