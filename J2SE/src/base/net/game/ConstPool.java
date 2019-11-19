package base.net.game;

import java.util.Vector;

/**
 * 常量池
 */
public class ConstPool {

    //客户端1的所有文件放在该目录
    private static final String ClientDir = "D:\\allproject\\projects\\J2SE\\src\\base\\net\\game\\client";
    //客户端2的所有文件放在该目录
    private static final String Client2Dir = "D:\\allproject\\projects\\J2SE\\src\\base\\net\\game\\client2";

    //服务器端所有文件放在该目录
    public static final String SERVER_DIR = "D:\\allproject\\projects\\J2SE\\src\\base\\net\\game\\server";
    private static Vector<RoutingTableTuple> r1 = new Vector<RoutingTableTuple>();//路由器R1的路由表
    private static Vector<RoutingTableTuple> r2 = new Vector<RoutingTableTuple>();//路由器R2的路由表
    private static Vector<RoutingTableTuple> r3 = new Vector<RoutingTableTuple>();//路由器R3的路由表
    private static Vector<RoutingTableTuple> r4 = new Vector<RoutingTableTuple>();//路由器R4的路由表
    private static Vector<RoutingTableTuple> r5 = new Vector<RoutingTableTuple>();//路由器R5的路由表
    private static Vector<RoutingTableTuple> r6 = new Vector<RoutingTableTuple>();//路由器R6的路由表

    public static String getClientDir() {
        return ClientDir;
    }

    public static String getClient2Dir() {
        return Client2Dir;
    }

    public static Vector<RoutingTableTuple> getR1() {
        RoutingTableTuple r = new RoutingTableTuple("net1", 1, "-");
        r1.addElement(r);
        r = new RoutingTableTuple("net2", 1, "-");
        r1.addElement(r);
        r = new RoutingTableTuple("net3", 1, "-");
        r1.addElement(r);
        return r1;
    }

    public static Vector<RoutingTableTuple> getR2() {
        RoutingTableTuple r = new RoutingTableTuple("net3", 1, "-");
        r2.addElement(r);
        r = new RoutingTableTuple("net4", 1, "-");
        r2.addElement(r);
        return r2;
    }

    public static Vector<RoutingTableTuple> getR3() {
        RoutingTableTuple r = new RoutingTableTuple("net4", 1, "-");
        r3.addElement(r);
        r = new RoutingTableTuple("net6", 1, "-");
        r3.addElement(r);
        return r3;
    }

    public static Vector<RoutingTableTuple> getR4() {
        RoutingTableTuple r = new RoutingTableTuple("net2", 1, "-");
        r4.addElement(r);
        r = new RoutingTableTuple("net5", 1, "-");
        r4.addElement(r);
        return r4;
    }

    public static Vector<RoutingTableTuple> getR5() {
        RoutingTableTuple r = new RoutingTableTuple("net1", 1, "-");
        r5.addElement(r);
        r = new RoutingTableTuple("net5", 1, "-");
        r5.addElement(r);
        return r5;
    }

    public static Vector<RoutingTableTuple> getR6() {
        RoutingTableTuple r = new RoutingTableTuple("net5", 1, "-");
        r6.addElement(r);
        r = new RoutingTableTuple("net6", 1, "-");
        r6.addElement(r);
        return r6;
    }
}
