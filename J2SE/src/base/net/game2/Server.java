package base.net.game2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 该类的一个实例为一个服务器
 * 运行主函数以启动服务器
 */
public class Server {
    public static void main(String[] args) {
        System.out.println("【服务器端】");
        try {
            //创建绑定到特定端口的服务器套接字，这是不同主机之间通信的关键
            ServerSocket server=new ServerSocket(8888);
            System.out.println("服务器已启动，服务器的通信端口号：8888");
            System.out.println("正在随时等待客户端连接到此服务器...");//等待接收客户端发送的数据

            Scanner scanner=new Scanner(System.in);
            Socket socket=null;
            System.out.println("该服务器提供一次性服务 or 多次服务？[y or n]");
            char choose=scanner.next().toCharArray()[0];
            switch (choose){
                case 'y':
                    //侦听并接受到此套接字的连接，返回一个Socket类对象
                    socket=server.accept();
                    new ServerThread(socket);
                    break;
                case 'n':
                    while (true){
                        //等待客户端的请求
                        socket=server.accept();
                        //每次请求都启动一个线程来处理
                        new ServerThread(socket);//一次while循环结束后该对象被JVM销毁
                    }
                default:
                    break;
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
