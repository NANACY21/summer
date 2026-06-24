package com.matchacloud.parkinglot;

/**
 * 车位类型
 */
public enum ParkingSpaceType {
    NORMAL("普通车位", 1.0),
    VIP("VIP车位", 1.5),
    DISABLED("残疾人专用车位", 0.6);

    private final String desc;
    private final double rate; // 费率系数
    ParkingSpaceType(String desc, double rate) {
        this.desc = desc;
        this.rate = rate;
    }
    public String getDesc() { return desc; }
    public double getRate() { return rate; }
}
