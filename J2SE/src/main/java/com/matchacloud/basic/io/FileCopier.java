package com.matchacloud.basic.io;

import java.io.*;

/**
 * 文件输入/输出字节/字符流示例
 */
public class FileCopier {

    /**
     * 文件复制 读入源文件到内存，内存数据写出到目标文件
     * 文件复制速度限制最高10KB/s
     *
     * @param srcPath  源文件绝对路径
     * @param destPath 目标文件绝对路径
     * @return
     */
    public static boolean fileCopyLimitSpeed(String srcPath, String destPath) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            //该对象和外存上的该文件之间建立一个连接通道 数据是外存流向内存
            inputStream = new FileInputStream(srcPath);

            //内存与外存文件建立连接通道 数据是内存流向外存 没有第2个参数则会覆盖原有内容，有第2个参数则不动原有内容，追加
            outputStream = new FileOutputStream(destPath, true);
            int len = 0;

            //缓冲区，要设置合理的缓冲区
            byte[] buffer = new byte[512];

            long startTime = System.currentTimeMillis();
            long totalBytesWritten = 0;

            //读外存数据到内存 流经这个通道 读取到的数据存储在 buffer 变量，len变量是返回的实际读取的字节数
            //原有A字节，读了B字节，读多了，会有空格
            while ((len = inputStream.read(buffer)) != -1) {
                //System.out.println(len);
                //off:把buffer数组写入外存 从数组的指定下标开始写数据 len:要写入的该数组的长度
                //读多长，写多长，最长512字节
                outputStream.write(buffer, 0, len);
                totalBytesWritten += len;

                // 计算当前已经过去的时间（秒）
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime > 0) {
                    // 计算当前的写入速度（字节/秒）
                    double currentSpeed = (totalBytesWritten * 1000.0) / elapsedTime;
                    if (currentSpeed > Downloader.MAX_SPEED) {
                        // 如果速度超过了最高速度，计算需要休眠的时间
                        long sleepTime = (totalBytesWritten / Downloader.MAX_SPEED * 1000) - elapsedTime;
                        if (sleepTime > 0) {
                            try {
                                // 线程休眠以降低速度
                                Thread.sleep(sleepTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            long endTime = System.currentTimeMillis();
            long thisSpeed = (totalBytesWritten * 1000) / (1024 * (endTime - startTime));
            System.out.println("此次文件复制速度:" + thisSpeed + "KB/秒");
            System.out.println("此次文件复制用时:" + (endTime - startTime) / 1000 + "秒");
            System.out.println("此次文件大小:" + totalBytesWritten / 1024 + "KB");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                //关闭连接
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param srcPath
     * @param destPath
     * @return
     */
    public static boolean fileCopyNew(String srcPath, String destPath) {
        Reader reader = null;
        Writer writer = null;
        try {
            //建立外存流向内存的通道
            reader = new FileReader(srcPath);
            //建立内存流向外存的通道
            writer = new FileWriter(destPath);
            char[] buffer = new char[1024];
            int len = reader.read(buffer);
            while (len != -1) {
                writer.write(buffer, 0, len);
                len = reader.read(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            //流的关闭顺序和打开顺序相反
            try {
                writer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 文件复制
     * 处理流 仅使用节点流读写效率低，只能按一些字节、一些字符读，不能一行一行读写
     * @param srcPath
     * @param destPath
     * @return
     */
    public static boolean fileCopy2(String srcPath, String destPath) {
        try {
            //建立外存流向内存的连接
            InputStream inputStream = new FileInputStream(srcPath);
            //封装成处理流
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //System.out.println(bis.read());


            //建立外存流向内存的连接
            Reader reader = new FileReader(srcPath);

            //处理流 自带缓冲区 封装成读入到内存的处理流
            BufferedReader bufferedReader = new BufferedReader(reader);

            //建立内存流向外存的连接
            Writer writer = new FileWriter(destPath);

            //使用该类，即使设置的缓冲区很小也没关系 封装成写出到外存的处理流
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            //返回读取的这一行的数据 读一行，以\r或\n分隔 会读空行，空行不占字符
            String lineData = bufferedReader.readLine();
            while (lineData != null) {
                System.out.println(lineData);
                //读一行，写一行到缓冲区
                //这不会立即写入外存设备 而是保存在缓冲区 目的是为了减少与外存设备的交互
                bufferedWriter.write(lineData, 0, lineData.length());

                //换行，即写出一个行分隔符
                bufferedWriter.newLine();
                lineData = bufferedReader.readLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            writer.close();
            bufferedReader.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        fileCopyLimitSpeed(PathPool.A_TXT_PATH, PathPool.B_TXT_PATH);

    }
}
