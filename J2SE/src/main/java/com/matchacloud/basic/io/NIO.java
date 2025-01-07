package com.matchacloud.basic.io;

import java.io.IOException;
import java.nio.file.*;

/**
 * nio简化File...，适合网络编程
 */
public class NIO {

    public static final String UNIVERSITY_2_2 = "D:\\university\\2_2";
    public static final String UNIVERSITY = "D:\\university";

    public static void main(String[] args) {
        Path path = Paths.get(UNIVERSITY_2_2);
        System.out.println(path.getFileName());
        System.out.println(path.getNameCount());
        System.out.println(path.getRoot());
        System.out.println(path.getParent());
        System.out.println(path.getName(0));

        //拷贝文件

        /**
         * 文件监控，观察者模式
         */
        path = Paths.get(UNIVERSITY);//监听该目录
        try {
            //创建文件监控对象
            WatchService ws = FileSystems.getDefault().newWatchService();

            //监听在该目录下创建、删除时
            path.register(ws, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE);//不定参数

            //循环监测文件
            while (true) {
                WatchKey wk = ws.take();

                //迭代触发事件的所有文件
                for (WatchEvent<?> event : wk.pollEvents()) {
                    System.out.println(event.context().toString() + "\t" + event.kind());
                }
                if (!wk.reset()) {
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
