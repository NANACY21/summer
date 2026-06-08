package com.matchacloud.basic.acm.acm20250220;

import java.util.ArrayList;
import java.util.List;

/**
 * 最小质因数
 * 120=2*2*2*3*5
 *
 * @author lichi
 */
public class MinZhiYinNum {

    public static void minZhiYinNum(int number) {
        int newNumber = number;
        //最小质因数列表
        List<Integer> zhiYinNumList = new ArrayList<>();
        for (int i = 2; i <= number; i++) {
            if (newNumber == 1) {
                break;
            }
            if (!isZhiNum(i)) {
                continue;
            }
            while (newNumber % i == 0) {
                zhiYinNumList.add(i);
                newNumber = newNumber / i;
                if (newNumber == 1) {
                    break;
                }
            }
        }
        System.out.print(number+" = ");
        for (int i = 0; i < zhiYinNumList.size(); i++) {
            if (i == zhiYinNumList.size() - 1) {
                System.out.print(zhiYinNumList.get(i));
            }
            else {
                System.out.print(zhiYinNumList.get(i)+" * ");
            }
        }
    }

    public static void main(String[] args) {
        minZhiYinNum(50);
    }


    /**
     * 判断是否是质数
     * 只能被1和自身整除
     *
     * @param number
     * @return
     */
    private static boolean isZhiNum(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
