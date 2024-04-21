package net.game2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.println("【客户端】");
        Scanner scanner=new Scanner(System.in);
        Socket socket= null;//这是不同主机之间通信的关键
        try {
            //创建一个流套接字，并将其连接到指定主机上的指定端口号
            //也可以输入服务端的ip地址、服务端的端口号以作为参数创建一个流套接字
            socket = new Socket("localhost",8888);

            InputStream inputStream=null;
            InputStreamReader isr=null;
            BufferedReader br=null;
            OutputStream outputStream=null;
            PrintWriter pw=null;
            inputStream=socket.getInputStream();//获取一个输入流，接收服务器端的消息
            isr=new InputStreamReader(inputStream);//包装成字符流
            br=new BufferedReader(isr);//缓冲区
            outputStream=socket.getOutputStream();//获取一个输出流，向服务器发送消息
            pw=new PrintWriter(outputStream);//将输出流包装成打印流

            System.out.println("请输入要发送给服务器的数据");
            String info="";//用于发送和接收
            String temp=null;

            while(!info.equals("bye")){
                info=scanner.nextLine();
                if("bye".equals(info)){
                    return;
                }
                pw.print(info);//发送info
                pw.flush();
                socket.shutdownOutput();//关闭输出流
                while ((temp=br.readLine())!=null){
                    info=info+temp;
                }
                System.out.println("来自服务器端的消息："+info);//客户端接收服务端发送的信息
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
