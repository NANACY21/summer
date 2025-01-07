package com.matchacloud.basic.io;

import java.io.File;
import java.io.IOException;

/**
 * 别运行
 */
public class Demo1 {

    public static final String A_TXT = "D:\\allproject\\projects\\J2SE\\src\\base\\io\\a.txt";
    public static final String UNIVERSITY_3_2 = "D:\\university\\3_2";
    public static final String DOC = "D:\\university\\3_2\\bankms\\规范_论文模板_中伦理专题.doc";

    public static void main(String[] args) {

        /**
         * 这不会创建文件，
         * 输出File对象为该文件指针的路径
         */
        File file = new File(A_TXT);

        //URI uri = URI.create("http://java2s.com");

        try {
            /**
             * 文件不存在时->创新文件->返回是否已创建。
             * 文件已存在时->不覆盖，不创建
             */
            boolean newFile = file.createNewFile();

            //在指定目录下创建临时txt文件，否则，创建在系统的临时目录
            File f5 = File.createTempFile("aaa", ".txt", new File(UNIVERSITY_3_2));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(System.getenv("JAVA_HOME"));//输出jdk安装路径

        //确保file变量使用完了，否则会覆盖了
        file = new File(UNIVERSITY_3_2);
        file.exists();
        //file.delete();

        String[] list = file.list();//得到file目录下所有目录和文件的名字
//        for(int i=0;i<list.length;i++){
//            System.out.println(list[i].toString()+"565");
//        }
        for (String name : list) {
            File f1 = new File(file, name);//文件指针
            System.out.println(file + "\t\t" + f1.getAbsoluteFile());
        }

        file.renameTo(new File(""));//重命名文件

        file.length();//file如果是目录->返回值不确定
        File f2 = new File(DOC);
        System.out.println("f2" + f2.length() / 1024);//如果是文件，返回文件大小
        System.out.println("file指针指向目录吗？" + file.isDirectory());
        System.out.println("file指针指向文件吗？" + file.isFile());
        file.canExecute();//

        //存在则删
        //file.deleteOnExit();
    }
}
