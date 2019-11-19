package base.array;

import java.util.Arrays;

/**
 * 遍历数组4种方式
 */
public class TestArray {

    public static void main(String[] args) {
        String[] arr = {"2", "4", "6", "8", "10"};

//        方式1：
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
        System.out.println();
//        方式2：
        for (String i : arr) {
            System.out.print(i + "\t");
        }
        System.out.println();
//        方法3：
        String s = Arrays.toString(arr);
        System.out.println(s);
//        方法4：lambda表达式，仅适用String数组
        Arrays.asList(arr).stream().forEach(x -> System.out.print(x + "\t"));
        System.out.println();
        Arrays.asList(arr).stream().forEach(System.out::print);
    }
}
