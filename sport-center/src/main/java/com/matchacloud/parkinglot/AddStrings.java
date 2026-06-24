package com.matchacloud.parkinglot;

/**
 * 字符串大数相加
 */
public class AddStrings {
    public String addStrings(String num1, String num2) {
        // i: num1指针 j: num2指针 carry:进位
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int carry = 0;
        StringBuilder sb = new StringBuilder();

        // 只要还有数字未遍历 或 还有进位就继续循环
        while (i >= 0 || j >= 0 || carry > 0) {
            int n1 = i >= 0 ? num1.charAt(i) - '0' : 0;
            int n2 = j >= 0 ? num2.charAt(j) - '0' : 0;
            int sum = n1 + n2 + carry;

            // 当前位存入
            sb.append(sum % 10);
            // 更新进位
            carry = sum / 10;

            i--;
            j--;
        }
        // 逆序反转得到正确数字
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        AddStrings solution = new AddStrings();
        String a = "21543655";
        String b = "4332656442";
        String res = solution.addStrings(a, b);
        System.out.println(res); // 输出 4354200097
    }
}
