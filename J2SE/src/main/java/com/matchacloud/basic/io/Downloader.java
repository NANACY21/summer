package com.matchacloud.basic.io;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 *
 * 下载器
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
