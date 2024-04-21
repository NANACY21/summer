package net.game2;

import java.io.*;
import java.net.Socket;

public class ServerThread {
    private Socket socket;

    private InputStream inputStream;
    private InputStreamReader isr;
    private BufferedReader br;

    private OutputStream outputStream=null;
    private PrintWriter pw=null;

    public ServerThread(Socket socket) throws IOException {
        super();
        this.socket=socket;

        //根据输入输出流和客户端连接
        this.inputStream=this.socket.getInputStream();//得到一个输入流，接收客户端传递的信息
        this.isr=new InputStreamReader(this.inputStream);//提高效率，将字节流转为字符流
        this.br=new BufferedReader(this.isr);//加入缓冲区

        this.outputStream=this.socket.getOutputStream();//获取一个输出流，向服务器发送消息
        this.pw=new PrintWriter(this.outputStream);//将输出流包装成打印流
    }

    //与客户端通信
    public void run(){
        String info="";
        String temp=null;
        boolean NotEnd=true;
        try {
            while (NotEnd){
                while ((temp=this.br.readLine())!=null){
                    info=info+temp;
                    //收到客户端的消息 证明 已接收到客户端连接
                    System.out.println("来自客户端(ip："+socket.getInetAddress().getHostAddress()+")的消息："+info);
                }

                //获取一个输出流，向客户端发送信息
                this.pw.print("来自服务器端的消息：已接收到你的信息");
                this.pw.flush();
            }

            this.pw.close();
            this.outputStream.close();
            this.br.close();
            this.isr.close();
            this.inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
