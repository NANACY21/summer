package com.matchacloud.basic.io;

import java.io.*;

/**
 * 处理流
 * 仅使用节点流读写效率低，只能按一些字节、一些字符读，
 * 不能一行一行读写
 */
public class Demo4 {

    public static final String A_TXT = "D:\\allproject\\projects\\J2SE\\src\\base\\io\\a.txt";
    public static final String B_TXT = "D:\\allproject\\projects\\J2SE\\src\\base\\io\\b.txt";

    public static void main(String[] args) {
        try {
            InputStream is = new FileInputStream(A_TXT);
            BufferedInputStream bis = new BufferedInputStream(is);//封装成处理流
            //System.out.println(bis.read());

            /**
             * 文件拷贝
             */
            Reader r = new FileReader(A_TXT);

            /**处理流
             * 自带缓冲区
             * 封装成读入到内存的处理流
             */
            BufferedReader br = new BufferedReader(r);

            Writer w = new FileWriter(B_TXT);

            /**使用该类，即使设置的缓冲区很小也没关系
             * 封装成写出到外存的处理流
             */
            BufferedWriter bw = new BufferedWriter(w);

            /**读一行，以\r或\n分隔
             * 会读空行，空行不占字符
             */
            String s = br.readLine();
            while (s != null) {
                System.out.println(s);
                bw.write(s, 0, s.length());

                //换行，即写出一个行分隔符
                bw.newLine();
                s = br.readLine();

                //读一行，写一行
                bw.flush();
            }
            bw.close();
            w.close();
            br.close();
            r.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
