package com.matchacloud.basic.io;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * 下载器
 * <p>
 * 下载请求 有时下载任务放在线程池的原因：
 * 1.提高效率：下载任务并发执行 多核处理器 同时下载多个文件 减少下载时间
 * 2.线程池的线程对象重复利用 避免频繁创建线程带来的开销 提升性能
 * 3.通过设置线程池参数控制同时下载的任务数量 避免同时下载任务过多引发系统性能问题
 * 4.可以方便地对下载任务进行管理和监控。例如，可以方便地统计下载任务的完成情况、获取下载进度信息、暂停或取消下载任务等
 * 5.下载任务时间长时 可以异步执行 及时返回前端结果 避免前端请求一直转圈
 * <p>
 * 下载文件的请求多了怎么办：
 * 1.异步处理 下载任务放入线程池 前端及时返回结果
 * springboot jvm进程 比如同时最多处理200个请求 建立200个连接 如果同步请求 请求时间很长 长时间占用连接
 * 导致请求阻塞 如果采用异步 请求时间会变短 及时释放连接 这就提高了jvm处理请求的效率
 * 2.负载均衡 微服务集群时 请求分摊到每个节点 避免单个节点处理请求太多导致压力过大
 * 3.优化下载算法代码 允许用户在网络中断等情况下继续下载未完成的文件，而不是重新开始
 * 4.限制下载速度
 * 5.下载监控执行到位
 * 6.优化硬件资源 CPU、内存、网络带宽 提高服务器处理能力和数据传输速度(这和代码中的限制下载速度不是一回事)
 */
public class Downloader {

    //待下载图片地址
    public static final String IMAGE_LOCATION = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&hs=0&pn=32&spn=0&di=7456461798821068801&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=715608868%2C120386985&os=2644361476%2C499078582&simid=3594895925%2C372448008&adpicid=0&lpn=0&ln=530&fr=&fmq=1739008264574_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=https%3A%2F%2Finews.gtimg.com%2Fom_bt%2FOZ4uJUzOr-RgpNiB_zg4x8Zi1PYW02_D9hQeOhKhyq0GAAA%2F641&fromurl=ippr_z2C%24qAzdH3FAzdH3Fgjo_z%26e3Bqq_z%26e3Bv54AzdH3F6wtgAzdH3FwAzdH3Fdad9a98mAaaJRVaa&gsm=1e&rpstart=0&rpnum=0&islist=&querylist=&nojc=undefined&dyTabStr=MCwxMiwzLDEsMiwxMyw3LDYsNSw5&lid=10009991195646139385";

    /**
     * 规定文件复制最高速度为 30KB/s
     */
    public static final int MAX_SPEED = 30 * 1024;

    /**
     * @param imageUrl    要下载的图片url
     * @param downloadDir 下载的目录位置
     * @param imgFileName 下载到本地的图片文件名称
     * @return
     */
    public static boolean downloadImage(String imageUrl, String downloadDir, String imgFileName) {
        InputStream inputStream = null;
        DataInputStream dis = null;
        OutputStream outputStream = null;
        DataOutputStream dos = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(6000);
            con.setReadTimeout(6000);
            int code = con.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                throw new Exception("网络中的文件读取失败");
            }
            //把源图片放输入流里 建立内存和网络中的图片的连接 数据流向：网络图片流向内存
            inputStream = con.getInputStream();
            dis = new DataInputStream(inputStream);

            outputStream = new FileOutputStream(new File(downloadDir + "/" + imgFileName));
            dos = new DataOutputStream(outputStream);
            //缓冲区，就是一个变量
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = dis.read(buffer)) > 0) {
                dos.write(buffer, 0, len);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }
                if (dis != null) {
                    dis.close();
                }
                inputStream.close();
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 下载文件
     *
     * @param srcPath          源文件地址
     * @param downloadDir      下载目标目录
     * @param downloadFileName 下载的文件名
     * @return
     */
    public static boolean downloadFile(String srcPath, String downloadDir, String downloadFileName) throws IOException, NoSuchAlgorithmException {
        String destPath = downloadDir + "/" + downloadFileName;
        boolean result = FileCopier.fileCopyLimitSpeed(srcPath, destPath);
        if (result) {
            String srcMd5 = SecurityChecker.calculateMD5(srcPath);
            String destMd5 = SecurityChecker.calculateMD5(destPath);
            return Objects.equals(srcMd5, destMd5);
        }
        return false;
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        //Downloader.downloadImage(IMAGE_LOCATION, PathPool.DOWNLOAD_PATH, "adc.jpg");
        //Downloader.downloadFile(PathPool.A_TXT_PATH, PathPool.DOWNLOAD_PATH, "b.txt");
        //System.out.println("a.txt的md5:" + SecurityChecker.calculateMD5(PathPool.A_TXT_PATH));
        //System.out.println("b.txt的md5:" + SecurityChecker.calculateMD5(PathPool.B_TXT_PATH));
    }
}
