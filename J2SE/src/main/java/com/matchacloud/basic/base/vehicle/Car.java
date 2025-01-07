package com.matchacloud.basic.base.vehicle;

/**
 * 轿车
 */
public class Car implements Vehicle {

    /**
     * 轿车停车费8元
     * @return
     */
    @Override
    public int parkingFee() {
        return 8;
    }
}
