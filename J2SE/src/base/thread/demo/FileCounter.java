package base.thread.demo;
import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**文件计数器
 * 多线程计算文件数
 */
public class FileCounter {

    public static final String PATHNAME = "D:\\university\\2_2";
    //队列
    private static LinkedBlockingQueue<File> lbq = new LinkedBlockingQueue<>();
    //计数器
    private static AtomicInteger count = new AtomicInteger(0);

    //使用递归法读取
    public static void readFile(File dir) {
        if (!dir.isDirectory()) {
            System.out.println(dir.getName());
            count.addAndGet(1);
            return;
        }
        for (File file : dir.listFiles()) {
            readFile(file);
        }
    }

    //使用多线程读取
    public static void readFileByThread(File dir) {
        long t1 = System.currentTimeMillis();

        try {
            lbq.put(dir);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        File file = lbq.take();
                        File[] files = file.listFiles();
                        for (File F : files) {
                            if (!F.isDirectory()) {
                                System.out.println(F.getName());
                                int a = count.addAndGet(1);
                                if (a == 1893) {//1893为指定目录下文件数
                                    long t2 = System.currentTimeMillis();
                                    System.out.println("总耗时：" + (t2 - t1));
                                }
                                System.out.println("文件数：" + a);
                                continue;
                            }
                            lbq.put(F);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r, "t1").start();
        new Thread(r, "t2").start();
        new Thread(r, "t3").start();

    }

    public static void main(String[] args) {
        //递归遍历文件
//        long t1=System.currentTimeMillis();
//        readFile(new File("D:\\大学\\大三(上)"));
//        long t2=System.currentTimeMillis();
//        System.out.println("总耗时："+(t2-t1));
//        System.out.println("文件数："+count.get());

        //多线程遍历文件
        readFileByThread(new File(PATHNAME));
    }
}
