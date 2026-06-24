package com.matchacloud.parkinglot;

/**
 * 车辆类型
 */
public enum CarType {

    SMALL_CAR("小型轿车", 5),
    SUV("SUV", 6),
    TRUCK("货车", 10),
    ;

    private final String desc;

    /**
     * 日间基础单价/小时
     */
    private final int basePrice;

    CarType(String desc, int basePrice) {
        this.desc = desc;
        this.basePrice = basePrice;
    }

    public String getDesc() {
        return desc;
    }

    public int getBasePrice() {
        return basePrice;
    }
}
