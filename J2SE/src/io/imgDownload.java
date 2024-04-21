package io;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 使用流下载图片
 * 把图片用输入流放里面，用输出流输出
 * 上传需要服务端
 */
public class imgDownload {

    //待下载图片地址
    public static final String PATH = "http://pic26.nipic.com/20121221/9252150_142515375000_2.jpg";

    public static void main(String[] args) {

        InputStream is = null;
        DataInputStream dis = null;
        OutputStream os = null;
        DataOutputStream dos = null;
        try {
            URL url = new URL(PATH);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(6000);
            con.setReadTimeout(6000);
            int code = con.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                throw new Exception("网络中的文件读取失败");
            }
            is = con.getInputStream();//把源图片放输入流里
            dis = new DataInputStream(is);

            os = new FileOutputStream(new File("nancy.jpg"));//下载到项目里
            dos = new DataOutputStream(os);
            //缓冲区，就是一个变量
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = dis.read(buffer)) > 0) {
                dos.write(buffer, 0, len);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }
                if (dis != null) {
                    dis.close();
                }
                is.close();
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
