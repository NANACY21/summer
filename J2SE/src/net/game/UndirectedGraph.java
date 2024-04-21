package net.game;

import java.util.ArrayList;
import java.util.Vector;

/**
 * 无向图
 * 属性：
 * 该图的节点名集合
 * 该图节点数
 *
 * 应该每个路由器既是服务器也是客户端，无临时服务器
 */
public class UndirectedGraph {
    private ArrayList<Node> nodes;//节点集合
    private int edgeNum;//边数
    private boolean[][] AdjMatrix;//邻接矩阵
    public UndirectedGraph(String[] nodeNames,String[][] edges) {
        this.nodes=new ArrayList<>();
        for(int i=0;i<nodeNames.length;i++){
            Node node=new Node(nodeNames[i]);
            /*
            初始化该节点的路由表，建立网络时才加载每个路由器的路由表
             */
            if(node.getName().indexOf("R")!=-1){
                if(node.getName().indexOf("1")!=-1){
                    node.setR(ConstPool.getR1());
                }
                else if(node.getName().indexOf("2")!=-1){
                    node.setR(ConstPool.getR2());
                }
                else if(node.getName().indexOf("3")!=-1){
                    node.setR(ConstPool.getR3());
                }
                else if(node.getName().indexOf("4")!=-1){
                    node.setR(ConstPool.getR4());
                }
                else if(node.getName().indexOf("5")!=-1){
                    node.setR(ConstPool.getR5());
                }
                else if(node.getName().indexOf("6")!=-1){
                    node.setR(ConstPool.getR6());
                }
            }
            this.nodes.add(node);//一定按此顺序
        }
        this.edgeNum=edges.length;
        this.AdjMatrix=new boolean[this.nodes.size()][this.nodes.size()];
        /*
        初始化邻接矩阵
         */
        for(int i=0;i<this.AdjMatrix.length;i++){
            for(int j=0;j<this.AdjMatrix[i].length;j++){
                this.AdjMatrix[i][j]=false;
            }
        }
        /*
        生成邻接矩阵
         */
        for(int i=0;i<this.edgeNum;i++){
            this.AdjMatrix[this.nodes.indexOf(this.getNode(edges[i][0]))][this.nodes.indexOf(this.getNode(edges[i][1]))]=true;
            this.AdjMatrix[this.nodes.indexOf(this.getNode(edges[i][1]))][this.nodes.indexOf(this.getNode(edges[i][0]))]=true;
        }


        for(int i=0;i<this.nodes.size();i++){//遍历节点
            Node node=this.nodes.get(i);
            Vector<String> nearNodeName=new Vector<String>();//找出所有与该节点node邻接的节点的名字
            for(int j=0;j<this.AdjMatrix[i].length;j++){
                if(this.AdjMatrix[i][j]==true){
                    nearNodeName.addElement(this.nodes.get(j).getName());
                }
            }
            //node.setNearNodeName(nearNodeName);
        }

        for(int i=0;i<this.nodes.size();i++){//遍历节点
            Node node=this.nodes.get(i);
            if(node.getFlag()==1){
                Vector<String> nearRouterName=new Vector<String>();//找出所有与该路由器node相邻的路由器的名字（不一定邻接）
                for(int j=0;j<this.AdjMatrix[i].length;j++){
                    if(this.AdjMatrix[i][j]==true){
                        for(int k=0;k<this.AdjMatrix[j].length;k++){
                            if(this.AdjMatrix[j][k]==true){
                                if(this.nodes.get(k).getName().indexOf("R")!=-1 && !this.nodes.get(k).getName().equals(node.getName())){
                                    nearRouterName.addElement(this.nodes.get(k).getName());
                                }
                            }
                        }
                    }
                }
                node.setNearRouterName(nearRouterName);
            }
            else if(node.getFlag()==0){
                node.setNearRouterName(null);
            }
        }
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    /**
     * 通过节点名返回网络中的节点
     * @param NodeName
     * @return
     */
    public Node getNode(String NodeName){
        for(Node i:this.nodes){
            if(i.getName().equals(NodeName))
                return i;
        }
        return null;
    }

    /**该网络中每个节点相继更新自己的路由表
     */
    public void updateRoutingTable(){
        for(int i=0;i<this.nodes.size();i++){//遍历该网络的节点
            Node node=this.nodes.get(i);
            if(node.getFlag()==1){//如果该节点是路由器，该节点接收所有相邻路由器的路由表
                Vector<RoutingTableTuple> r=node.getR();//得到该节点的当前路由表r（r待更新）
                Vector<String> NearRouterName=node.getNearRouterName();//得到该路由器的所有相邻的路由器的名字
                for(int j=0;j<NearRouterName.size();j++){
                    Node NearRouter=this.getNode(NearRouterName.elementAt(j));//得到1个相邻路由器
                    Vector<RoutingTableTuple> NearRouterR=NearRouter.getR();//得到一个相邻的路由器的路由表
                    /*
                    这一过程中NearRouterR不应该变化（引用NearRouterR指向的地址中的数据不变化）
                     */
                    Vector<RoutingTableTuple> temp = new Vector<RoutingTableTuple>();

                    //temp -- 一个临时路由表
                    for(int k = 0;k < NearRouterR.size();k++){

                        RoutingTableTuple tuple=new RoutingTableTuple(NearRouterR.elementAt(k).getObjectiveNetwork(),
                                                              NearRouterR.elementAt(k).getDistance()+1,
                                                                      NearRouter.getName());
                        temp.addElement(tuple);
                    }//至此temp ok

                    //现在更新路由表r（temp往r里加）
                    for(int k=0;k<temp.size();k++){
                        int index=isHave(r,temp.elementAt(k));
                        if(index==-1){
                            r.addElement(temp.elementAt(k));
                        }
                        else{
                            if(r.elementAt(index).getNextRouterName().equals(temp.elementAt(k).getNextRouterName())){
                                r.setElementAt(temp.elementAt(k),index);
                            }
                            else{
                                if(temp.elementAt(k).getDistance() < r.elementAt(index).getDistance()){
                                    r.setElementAt(temp.elementAt(k),index);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 判断路由表yuanR1中有没有网络tuple的ObjectiveNetwork，有，则返回yuanR1的那处索引
     * @param yuanR1
     * @param tuple
     * @return
     */
    public int isHave(Vector<RoutingTableTuple> yuanR1,RoutingTableTuple tuple){
        for(int i=0;i<yuanR1.size();i++){
            if(yuanR1.elementAt(i).getObjectiveNetwork().equals(tuple.getObjectiveNetwork())){
                return i;
            }
        }
        return -1;
    }

    /**
     * 显示该网络所有路由器的路由表
     */
    public void displayRs(){
        for(int i=0;i<this.nodes.size();i++){
            this.nodes.get(i).displayR();
        }
    }
}
