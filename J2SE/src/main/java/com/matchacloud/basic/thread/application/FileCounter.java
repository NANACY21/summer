package com.matchacloud.basic.thread.application;
import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程实际应用之 文件计数器 多线程计算文件数
 * 知识点：多线程 Java文件 阻塞队列 线程join()
 */
public class FileCounter {

    /**
     * 目录路径
     */
    public static final String DIR_PATH = "/Users/lichi/projectList/self/matcha";

    /**
     * 队列
     */
    private static LinkedBlockingQueue<File> blockingQueue = new LinkedBlockingQueue<>();

    /**
     * 计数器
     */
    private static AtomicInteger count = new AtomicInteger(0);

    /**
     * 使用递归法读取 单线程
     *
     * @param dir
     */
    public static void readFile(File dir) {
        if (dir.isHidden()) {
            return;
        }
        if (!dir.isDirectory()) {
            System.out.println(dir.getName());
            count.addAndGet(1);
            return;
        }
        for (File file : dir.listFiles()) {
            readFile(file);
        }
    }

    /**
     * 多线程读取
     *
     * @param dir
     */
    public static void readFileByMulThread(File dir) {
        long begin = System.currentTimeMillis();

        try {
            // 将初始目录放入队列
            blockingQueue.put(dir);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        // 从队列中取出一个文件或目录
                        File file = blockingQueue.poll();
                        if (file == null || file.isHidden()) {
                            break;
                        }
                        File[] files = file.listFiles();
                        if (files != null) {
                            for (File f : files) {
                                if (f.isHidden()) {
                                    continue;
                                }
                                if (!f.isDirectory()) {
                                    System.out.println(f.getName());
                                    int a = count.incrementAndGet();
                                } else {
                                    blockingQueue.put(f);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        Thread t1 = new Thread(r, "t1");
        Thread t2 = new Thread(r, "t2");
        Thread t3 = new Thread(r, "t3");
        t1.start();
        t2.start();
        t3.start();

        try {
            // 等待所有线程执行完毕
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        long end = System.currentTimeMillis();
        System.out.println("文件数：" + count.get());
        // 输出总耗时 1秒=1000毫秒
        System.out.println("总耗时(毫秒)：" + (end - begin));
    }

    public static void main(String[] args) {
        //递归遍历文件
//        long t1 = System.currentTimeMillis();
//        readFile(new File(DIR_PATH));
//        long t2 = System.currentTimeMillis();
//        System.out.println("总耗时(毫秒)：" + (t2 - t1));
//        System.out.println("文件数：" + count.get());

        //多线程遍历文件
        readFileByMulThread(new File(DIR_PATH));
    }
}
