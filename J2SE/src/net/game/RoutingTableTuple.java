package net.game;

/**
 * 路由表元组
 */
public class RoutingTableTuple {
    private String ObjectiveNetwork;//目的网络
    private int distance;//距离
    private String nextRouterName;//下一跳路由器名

    public RoutingTableTuple(String objectiveNetwork, int distance, String nextRouterName) {
        this.ObjectiveNetwork = objectiveNetwork;
        this.distance = distance;
        this.nextRouterName = nextRouterName;
    }

    public String getObjectiveNetwork() {
        return ObjectiveNetwork;
    }

    public int getDistance() {
        return distance;
    }

    public String getNextRouterName() {
        return nextRouterName;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * 距离+1
     */
    public void addDistance(){
        this.distance++;
    }

    public void setNextRouterName(String nextRouterName) {
        this.nextRouterName = nextRouterName;
    }
}
