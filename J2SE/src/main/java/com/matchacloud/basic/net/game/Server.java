package com.matchacloud.basic.net.game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**该传输是TCP
 * 该类一个实例 -- 一个服务器
 * 运行主函数以启动服务器
 * 传输String（还可以传对象）
 * 服务器端有文件
 * 思考：客户端之间通过服务器传输数据
 */
public class Server {
    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            //创建绑定到特定端口的服务器套接字
            this.serverSocket = new ServerSocket(port);
            System.out.println("LiChi's Game");
            System.out.println("服务器已启动  服务器通信端口号:8888");
            System.out.println("正在随时等待客户端连接到此服务器");

            Socket socket=null;

            while (true){
                //等待客户端的请求，侦听并接受到此套接字的连接，返回一个Socket类对象
                /*
                获取客户端的socket对象
                 */
                socket=serverSocket.accept();//该语句在客户端启动的那一刻执行
                //每次请求都启动一个线程来处理
                //一个客户端 -- 启动一个线程
                new ServerThread(socket);//socket为空时线程才被终止
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server(8888);
    }
}