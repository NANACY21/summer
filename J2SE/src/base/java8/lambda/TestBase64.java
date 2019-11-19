package base.java8.lambda;


import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class TestBase64 {

    public static void main(String[] args) {
        try {
            //把字符串按utf-8编码
            String s = Base64.getEncoder().encodeToString("的v啥的VS".getBytes("utf-8"));
            //s为加密后的字符串
            System.out.println(s + "66");

            String s1 = new String(Base64.getDecoder().decode(s));
            //s解码 s1即为原来的字符串
            System.out.println(s1);

            String ss = "的v啥的VS";
            System.out.println(Base64.getEncoder().encodeToString(ss.getBytes("utf-8")) + "66");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
