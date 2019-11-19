package base.net.game;

import java.util.Vector;

/**
 * 网络中的节点
 */
public class Node {
    private String name;//名
    private int flag;//0 -> 网络，1 -> 路由器(Router)
    private Vector<RoutingTableTuple> r;//路由表
//    路由表使用数据结构 - HashMap不合适
//    private Vector<String> nearNodeName;//所有与该节点邻接的节点的名字（不使用该属性）
    private Vector<String> nearRouterName;//所有与该路由器相邻的路由器的名字（2个路由器之间只隔一个网络即为相邻）
//    private Socket socket;


    public Node(String name) {
        this.name = name;
        if(this.name.indexOf("R")!=-1){
            this.flag=1;
            this.r=new Vector<RoutingTableTuple>();
        }
        else{
            this.flag=0;
            this.r=null;
        }
    }

    public String getName() {
        return name;
    }

    public int getFlag() {
        return flag;
    }

    public Vector<RoutingTableTuple> getR() {
        return r;
    }



    public Vector<String> getNearRouterName() {
        return nearRouterName;
    }

    public void setR(Vector<RoutingTableTuple> r) {
        this.r = r;
    }



    public void setNearRouterName(Vector<String> nearRouterName) {
        this.nearRouterName = nearRouterName;
    }

    /**
     * 显示路由表内容
     */
    public void displayR(){
        if(this.flag==1){
            System.out.println("路由器"+this.getName()+"的路由表:");
            System.out.println("目的网络"+"\t"+"距离"+"\t"+"下一跳路由器名");
            for(int i=0;i<this.r.size();i++){
                RoutingTableTuple tuple=this.r.elementAt(i);
                System.out.print(tuple.getObjectiveNetwork()+"\t");
                System.out.print(tuple.getDistance()+"\t");
                System.out.println(tuple.getNextRouterName());
            }
        }
    }
}
