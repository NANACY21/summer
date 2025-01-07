package com.matchacloud.basic.io;

import java.io.*;

/**
 * 输入输出字符流
 */
public class Demo3 {

    public static final String A_TXT = "D:\\allproject\\projects\\J2SE\\src\\base\\io\\a.txt";
    public static final String B_TXT = "D:\\allproject\\projects\\J2SE\\src\\base\\io\\b.txt";

    public static void main(String[] args) {
        Reader r = null;
        Writer w = null;
        try {
            r = new FileReader(A_TXT);
            w = new FileWriter(B_TXT);
            char[] buffer = new char[1024];
            int rd = r.read(buffer);
            while (rd != -1) {
                w.write(buffer, 0, rd);
                rd = r.read(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //流的关闭顺序和打开顺序相反
            try {
                w.close();
                r.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
