package com.matchacloud.basic.io;

import java.io.File;
import java.io.IOException;

/**
 * File类讲解
 */
public class FileDemo {

    public static final String A_TXT_PATH = PathPool.A_TXT_PATH;
    public static final String DIR_PATH = PathPool.DIR_PATH;
    public static final String DOC = "D:\\university\\3_2\\bankms\\规范_论文模板_中伦理专题.doc";

    public static void main(String[] args) {

        //这不会创建文件，输出File对象为该文件指针的路径
        File file = new File(A_TXT_PATH);

        //URI uri = URI.create("http://java2s.com");

        try {
            //文件不存在时：创建新文件 返回是否已创建。文件已存在时：不覆盖，不创建
            boolean newFile = file.createNewFile();

            //在指定目录下创建临时txt文件，否则，创建在系统的临时目录
            File tempFile = File.createTempFile("temp", ".txt", new File(DIR_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //输出jdk安装路径
        System.out.println(System.getenv("JAVA_HOME"));

        //确保file变量使用完了，否则会覆盖
        file = new File(DIR_PATH);
        boolean exists = file.exists();
        //file.delete();
        //得到file目录下所有目录和文件的名字
        String[] list = file.list();
        System.out.println();
        for(int i=0;i<list.length;i++){
            System.out.println(list[i]);
        }
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
