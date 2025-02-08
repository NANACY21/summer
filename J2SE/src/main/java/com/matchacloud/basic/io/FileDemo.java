package com.matchacloud.basic.io;

import java.io.File;
import java.io.IOException;

/**
 * File类讲解
 */
public class FileDemo {

    public void test() {
        //file对象:该对象代表了指定路径的文件或目录 不一定该文件或目录在文件系统中实际存在
        File file = new File(PathPool.A_TXT_PATH);

        //将传入的字符串解析为一个 URI 对象
        //URI uri = URI.create("http://java2s.com");

        try {
            //文件不存在时：创建新文件 返回是否已创建。文件已存在时：不覆盖，不创建
            boolean newFile = file.createNewFile();

            //在指定目录下创建临时txt文件，否则，创建在系统的临时目录
            File tempFile = File.createTempFile("temp", ".txt", new File(PathPool.DIR_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //输出jdk安装路径
        System.out.println(System.getenv("JAVA_HOME"));

        //确保file变量使用完了，否则会覆盖
        file = new File(PathPool.DIR_PATH);
        boolean exists = file.exists();
        //file.delete();

        if (file.isDirectory() && file.exists()) {
            //得到file目录下所有目录和文件的名字
            String[] list = file.list();
            System.out.println(PathPool.DIR_PATH + "目录下所有目录和文件的名字");
            for (int i = 0; i < list.length; i++) {
                System.out.println(list[i]);
            }
            for (String name : list) {
                File f1 = new File(file, name);//文件指针
                System.out.println(file + "\t\t" + f1.getAbsoluteFile());
            }
        }

        //重命名文件
        file.renameTo(new File(""));
        //file如果是目录->返回值不确定
        file.length();
        File f2 = new File(PathPool.A_TXT_PATH);
        //如果是文件，返回文件大小
        System.out.println("f2大小:" + f2.length() / 1024 + "KB");
        System.out.println("file指针指向目录吗？" + file.isDirectory());
        System.out.println("file指针指向文件吗？" + file.isFile());
        file.canExecute();

        //存在则删
        //file.deleteOnExit();
    }

    public static void main(String[] args) {
        //new FileDemo().test();
    }
}
