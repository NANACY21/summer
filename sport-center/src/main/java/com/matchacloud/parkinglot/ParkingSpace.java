package com.matchacloud.parkinglot;

/**
 * 车位
 */
public class ParkingSpace {
    private int spaceId;            // 车位编号
    private int floor;              // 所属楼层
    private ParkingSpaceType type;  // 车位类型
    private boolean isUsed;         // 是否占用
    private String occupiedPlate;    // 占用车牌

    public ParkingSpace(int spaceId, int floor, ParkingSpaceType type) {
        this.spaceId = spaceId;
        this.floor = floor;
        this.type = type;
        this.isUsed = false;
        this.occupiedPlate = null;
    }

    // getter/setter
    public int getSpaceId() { return spaceId; }
    public int getFloor() { return floor; }
    public ParkingSpaceType getType() { return type; }
    public boolean isUsed() { return isUsed; }
    public void setUsed(boolean used) { isUsed = used; }
    public String getOccupiedPlate() { return occupiedPlate; }
    public void setOccupiedPlate(String plate) { occupiedPlate = plate; }
}
