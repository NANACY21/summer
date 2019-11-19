package partition1;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//分词
public class Main {
    public static void main(String[] args) {

        String a = "apple 最近 推出 了 新版 iphone";
        String[] s1 = a.split(" ");
        System.out.println(s1[0]);
        System.out.println(s1[1]);
        String c = s1[0];
        for (int i = 0; i < s1.length-1; i++) {
            if ((check(s1[i]) && check(s1[i + 1])) == false) {
                c = c + " " + s1[i + 1];
            } else {
                c = c + s1[i + 1];
            }
        }
        System.out.println(c);


    }

    public static boolean check(String countname) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(countname);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
