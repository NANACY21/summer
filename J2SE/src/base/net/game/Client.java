package base.net.game;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

/**该传输是TCP
 * 该类一个实例 -- 一个客户端
 * 创建该类实例 -- 启动客户端
 * 用户可以下载文件、发一个数字、发一个单词
 */
public class Client {
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;
    private String dir;//该客户端所有文件放在该目录
    private String send;//每次客户端要发送的信息
    public Client(String hostName,int ServerPort,String dir) {
        try {
            //创建一个流套接字（参数也可以手动输入），并将其连接到指定主机上的指定端口号
            this.socket = new Socket(hostName,ServerPort);//连接到服务器
//            this.socket.setSoTimeout(5000);
            this.is=new DataInputStream(socket.getInputStream());//接收
            this.os=new DataOutputStream(socket.getOutputStream());//发送
            this.dir=dir;
            this.send="";

            Scanner scanner=new Scanner(System.in);
            while (true){
//                System.out.println("请输入要下载的文件名");//输入要发送给服务器的数据
                System.out.println("请输入用户名和密码");//输入要发送给服务器的数据
                this.setSend(scanner.nextLine());
                this.send();
                if("BYE".equals(this.receive().toUpperCase())){
                    this.closeResource();
                    scanner.close();
                    return;
                }
            }

        } catch (ConnectException e1){
            System.out.println("连接服务器被拒绝，可能是服务器未启动");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSend(String send) {
        this.send = send;
    }

    /**
     * 客户端发送信息
     */
    public void send(){
        try {
            this.os.writeUTF(this.send);//发送
            this.os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收信息
     * @return 接收到的信息
     */
    public String receive(){
        String receive="";
        try {
            receive=this.is.readUTF();//自发送完5秒之内接收不到则报错
            System.out.println(receive);
//            if(receive.indexOf(' ')!=-1){//有空格
//                Vector<String> v=new Vector<String>();
//                receive=receive.trim();
//                String []infoPart=receive.split(" ");
//                for(int i=0;i<infoPart.length;i++){
//                    v.addElement(infoPart[i]);
//                }
//
//                File file=new File(dir+"\\"+this.send+".txt");
//                file.createNewFile();
//                setFileData(v,dir+"\\"+this.send+".txt");
//                System.out.println("文件下载成功");
//            }
//
//            else{
//                //System.out.println("文件："+receive+"不存在，下载失败");//info为来自服务器端的消息
//            }
        } catch (SocketTimeoutException e1) {//捕捉发送超时异常
            System.out.println("服务器接收该信息超时");
            this.send();
            this.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receive;
    }

    /**
     * 关闭相对应的资源
     */
    public void closeResource(){
        try {
            this.is.close();
            this.os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        此.java文件的Socket类对象在创建后被服务器的accept接收，并且在new线程时作为实参，
        传参是引用类型，在线程run()中该Socket类对象已被关闭
        因此，此时不可再次关闭
         */
        //socket.shutdownOutput();//关闭输出流
        //socket.close();
    }

    public static void main(String[] args) {
        new Client("localhost",8888, ConstPool.getClientDir());
    }
}
