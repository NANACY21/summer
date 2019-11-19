package base.io;

import java.io.*;

/**
 * 输入输出字节流
 */
public class Demo2 {

    public static final String A_TXT = "D:\\allproject\\projects\\J2SE\\src\\base\\io\\a.txt";
    public static final String B_TXT = "D:\\allproject\\projects\\J2SE\\src\\base\\io\\b.txt";

    public static void main(String[] args) {
        InputStream is = null;
        OutputStream os = null;
        try {
            /**
             * 文件拷贝，读入a文件到内存，内存数据写出到b文件
             */
            is = new FileInputStream(A_TXT);

            //无第二个参数覆盖原有内容，有第二个参数不动原有内容，追加
            os = new FileOutputStream(B_TXT, true);
            int len = 0;

            /**
             * 缓冲区，要设置合理的缓冲区
             */
            byte[] buffer = new byte[512];

            //原有A字节，读了B字节，读多了，会有空格
//            while ((len=is.read())!=-1){
//                System.out.println((char) len);
//            }
            while ((len = is.read(buffer)) != -1) {
                System.out.println(len);
                os.write(buffer, 0, len);//读多长，写多长，最长512字节
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
