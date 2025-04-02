package com.matchacloud.basic.acm.acm20250220;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 刷题
 * 不要把算法想的太复杂
 */
public class LastString {

    public static void lastString() {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] wordList = line.split(" ");
            System.out.println(wordList[wordList.length - 1].length());
        }
    }

    /**
     * 16进制转10进制
     * 注意char转int
     */
    public static void hj5() {
        Scanner in = new Scanner(System.in);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("A", 10);
        map.put("B", 11);
        map.put("C", 12);
        map.put("D", 13);
        map.put("E", 14);
        map.put("F", 15);
        while (in.hasNextLine()) {
            //输入的16进制
            String line = in.nextLine();
            line = line.substring(2);
            int length = line.length();
            int j = length - 1;

            int result = 0;
            for (int i = 0; i < length; i++) {
                char c = line.charAt(i);
                if (map.containsKey(c + "")) {
                    double v = Math.pow(16, j) * map.get(c + "");
                    result += v;
                } else {
                    double v = Math.pow(16, j) * Character.getNumericValue(c);
                    result += v;
                }
                j--;
            }
            System.out.println(result);
        }
    }


    /**
     * 3个空瓶换一个汽水 可以借空瓶 但是必须还  输入已有空瓶数量 输出最多能喝多少瓶汽水
     */
    public static void drinkCoco() {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            //手上有 emptyBottles 个空瓶
            int emptyBottles = Integer.parseInt(line);
            //一共喝 drunk 瓶汽水
            int drunk = 0;
            while (emptyBottles >= 2) {
                if (emptyBottles == 2) {
                    drunk++;
                    break;
                }
                //这次能喝 temp 瓶
                int temp = emptyBottles / 3;
                drunk += temp;
                emptyBottles = emptyBottles % 3 + temp;
            }
            if (drunk > 0) {
                System.out.println(drunk);
            }
        }
    }


    public static void generateNumber() {
        Scanner in = new Scanner(System.in);
        List<Integer> numbers = new ArrayList<>();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            int n = Integer.parseInt(line);

            for (int i = 0; i < n; i++) {
                numbers.add(Integer.parseInt(in.nextLine()));
            }
            break;
        }
        numbers = numbers.stream().distinct().sorted().collect(Collectors.toList());
        for (int i = 0; i < numbers.size(); i++) {
            System.out.println(numbers.get(i) + " ");
        }
    }

    /**
     * 给定字符串 根据字符出现频率降序排序
     * 如果出现频率一样 ascii码从小到大排序
     */
    public static void sort() {
        Scanner in = new Scanner(System.in);
        HashMap<String, Integer> map = new HashMap<>();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            char[] charArray = line.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                if (map.containsKey(charArray[i] + "")) {
                    map.put(charArray[i] + "", map.get(charArray[i] + "") + 1);
                } else {
                    map.put(charArray[i] + "", 1);
                }
            }
            break;

        }
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(entries);
        Collections.sort(list, (entry1, entry2) -> {
            int countCompare = entry2.getValue().compareTo(entry1.getValue());
            if (countCompare != 0) {
                return countCompare;
            }
            //如果字符出现频率一样则ascii码从小到大排序
            return entry1.getKey().compareTo(entry2.getKey());
        });

        for (Map.Entry<String, Integer> entry : list) {
            System.out.print(entry.getKey());
        }
    }

    /**
     * 字符串的漂亮度
     */
    public static void stringBeautiful() {
        Scanner in = new Scanner(System.in);
        String [] words = null;
        while (in.hasNextLine()) {
            String line = in.nextLine();
            int count = Integer.parseInt(line);
            words=new String[count];
            for (int i = 0; i < count; i++) {
                words[i] = in.nextLine();

            }
            break;
        }
        for (int i = 0; i < words.length; i++) {
            HashMap<Character, Integer> map = new HashMap<>();
            int maxNumber = 26;
            String word = words[i];
            char[] charArray = word.toCharArray();
            for (int j = 0; j < charArray.length; j++) {
                if (map.containsKey(charArray[j])) {
                    map.put(charArray[j], map.get(charArray[j]) + 1);
                }
                else {
                    map.put(charArray[j], 1);
                }
            }
            List<Integer> values = new ArrayList<>(map.values());
            //降序
            values=values.stream()
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
            int sum = 0;
            for (int j = 0; j < values.size(); j++) {
                sum += maxNumber * values.get(j);
                maxNumber--;
            }
            System.out.println(sum);
        }
    }


    /**
     * 螺旋数字矩阵
     */
    public static void luoxuanNumberjuzhen() {

        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        // count个数
        int count;
        // row行
        int row;
        String[] s = line.split(" ");
        count = Integer.parseInt(s[0]);
        row = Integer.parseInt(s[1]);

        //算出列数
        int col = (int) Math.ceil(((double) count) / row);
        System.out.println(Math.ceil(count / row));

        //输出
        String[][] array = new String[row][col];
        //初始右上边界
        int right = col - 1;
        //初始右下边界
        int bottom = row - 1;
        //初始左下边界
        int left = 0;
        //初始左上边界
        int top = 0;
        int i = 1;
        while (i <= row * col) {
            for (int j = left; j <= right; j++) {
                array[top][j] = i > count ? "*" : i + "";
                i++;
            }
            top++;
            for (int j = top; j <= bottom; j++) {
                array[j][right] = i > count ? "*" : i + "";
                i++;
            }
            right--;
            for (int j = right; j >= left; j--) {
                array[bottom][j] = i > count ? "*" : i + "";
                i++;
            }
            bottom--;
            for (int j = bottom; j >= top; j--) {
                array[j][left] = i > count ? "*" : i + "";
                i++;
            }
            left++;


        }

        for (i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        luoxuanNumberjuzhen();
    }
}
