package net.game;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.Vector;
import static str.Tool.getFileData;

public class ServerThread extends Thread{
    private Socket socket;//关闭该对象->不想和该客户端通信了
    private DataInputStream is;
    private DataOutputStream os;
    private String send;//每次服务器要发送的信息。服务器端需不需要这样写，以使发送方法、接收方法共享该变量？

    public ServerThread(Socket socket) throws IOException {
        super();
        this.socket=socket;

        //在构造方法中为每个套接字连接输入和输出流
        this.is=new DataInputStream(this.socket.getInputStream());//接收
        this.os=new DataOutputStream(this.socket.getOutputStream());//发送

        this.start();//启动run方法
    }

    public void setSend(String send) {
        this.send = send;
    }

    /**
     * 接收信息
     * @return 接收到的信息
     */
    public String receive(){
        String receive="";
        try {
            //接收，receive是文件名（客户端发数据时才会立即触发该函数，客户端不发数据则该函数不执行）
            receive = this.is.readUTF();
            File file=new File(ConstPool.SERVER_DIR+"\\"+receive+".txt");
            if(receive.indexOf(' ')!=-1){//用户名和密码
                try {
                    ServerThread.Database();
                    String[] s = receive.split(" ");


                    Connection conn = ServerThread.Database();


                    System.out.println(s[0]+s[1]+"99");
                    ResultSet res=(conn.prepareStatement("select * from normaluser where name='"+s[0]+"' and password='"+s[1]+"'")).executeQuery();
                    if(res.next()){
                        this.send="登录成功";
                    }
                    else {
                        this.send="登录失败";
                    }
                }  catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(!file.exists()){
                if(receive.length()==1){//用户发一个数字
                    char ch=receive.toCharArray()[0];
                    if(Character.isDigit(ch)){//若字符ch是数字字符
                        String[] nodeNames={"net1","net2","net3","net4","net5","net6",
                                "R1","R2","R3","R4","R5","R6"};//一定按此顺序
                        String[][] edges={{"R1","net1"},{"R1","net2"},{"R1","net3"},{"net1","R5"},
                                {"net2","R4"},{"net3","R2"},{"net5","R5"},{"net5","R4"},
                                {"net4","R2"},{"net4","R3"},{"net6","R3"},{"net6","R6"},
                                {"net5","R6"} };
                        UndirectedGraph u=new UndirectedGraph(nodeNames,edges);
                        u.displayRs();
                        int Ch=Integer.parseInt(String.valueOf(ch));//char转int
                        for(int i=0;i<Ch;i++){
                            u.updateRoutingTable();
                        }
                        System.out.println("\n\n"+Ch+"轮更新之后\n\n");
                        u.displayRs();
                        this.send="网络中的所有路由表已完成"+Ch+"轮更新";
                    }
                }
                else{//用户发一个单词
                    this.send=receive.toUpperCase();
                }
            }
            else{//用户要下载文件
                Vector<String> temp=getFileData(ConstPool.SERVER_DIR+"\\"+receive+".txt");
                this.send="";
                for(int i=0;i<temp.size();i++){
                    this.send = this.send + temp.elementAt(i)+" ";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receive;
    }

    /**
     * 服务端发送信息
     */
    public void send(){
        try {
            this.os.writeUTF(this.send);
            this.os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭相对应的资源
     */
    public void closeResource(){
        //关闭相应的资源
        try {
            this.is.close();
            this.os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        此时客户端main函数已经退出，因此那一个客户端的Socket类对象已经被销毁，
        由于传参是引用类型，因此this.socket对象此时已经关闭，但不为null
         */
        //this.socket.shutdownOutput();//关闭输出流，即服务端不再发送
        //this.socket.close();
    }

    //与客户端通信
    public void run(){
        try {
            boolean NotEnd=true;
            while (NotEnd){
                //随机生成0 - 90之间的数字
//                int flag=(int) (Math.random() * 90);
//                if(flag>-1 && flag<3){//帧丢失即超时
//                    this.receive();
//                    sleep(5000);
//                }
//                else if(flag>2 && flag<6){//错误帧
//                    this.receive();
//                    this.setSend("数据错误，请重新发送");
//                    this.send();
//                }
//                else if(flag>5){//正常
//
//                }
                String receive = this.receive();
                //System.out.println("来自客户端(ip:"+this.socket.getInetAddress().getHostAddress()+")的消息:"+receive);
                if(!"EXIT".equals("exit")){

                }
                else{
                    NotEnd=false;
                    this.send="bye";
                }
                this.send();
            }
            this.closeResource();
        }
        catch (Exception e){

        }
    }

    public static Connection Database() {

        Connection conn=null;//连接
        PreparedStatement pres;
        ResultSet res;

        String url="jdbc:mysql://localhost:3306/bankcard?serverTimezone=UTC";
        String username="root";
        String password="xy201619";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动

            conn = DriverManager.getConnection(url, username, password);//建立连接

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
