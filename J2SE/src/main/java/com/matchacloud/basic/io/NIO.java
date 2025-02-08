package com.matchacloud.basic.io;

import java.io.IOException;
import java.nio.file.*;

/**
 * nio简化File...，适合网络编程
 */
public class NIO {

    public static void test() {
        //路径对象
        Path path = Paths.get(PathPool.MATCHA_DIR);
        //文件名/最后一级目录名
        System.out.println(path.getFileName());
        //路径中元素数量 a/b/c->3
        System.out.println(path.getNameCount());
        //根路径
        System.out.println(path.getRoot());
        //上级目录
        System.out.println(path.getParent());
        //指定路径元素 a/b/c 索引0:a
        System.out.println(path.getName(0));


        //文件监控，观察者模式 监听该目录
        try {
            //创建文件监控对象
            WatchService watchService = FileSystems.getDefault().newWatchService();

            //注册监听事件：监听在该目录下创建、删除时
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE);

            //循环监测文件
            while (true) {
                //注册监听的事件未发生时 该线程会一直等待 有事件发生时会返回WatchKey对象
                WatchKey watchKey = watchService.take();

                //迭代触发事件的所有文件
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    System.out.println("变化的文件/目录名称："+event.context().toString() + "\t事件类型：" + event.kind());
                }
                //重置 WatchKey对象 使其能够继续监听后续的事件
                //返回false：表示 WatchKey 已经失效，通常是因为注册的 Path 对象不再有效（例如目录被删除）
                if (!watchKey.reset()) {
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
