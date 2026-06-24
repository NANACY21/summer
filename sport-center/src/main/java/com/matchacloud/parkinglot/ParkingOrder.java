package com.matchacloud.parkinglot;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 停车订单（票据）
 */
public class ParkingOrder {
    private String orderNo;                 // 票据单号
    private String plate;                   // 车牌
    private CarType carType;                // 车辆类型
    private ParkingSpace parkSpace;         // 停放车位
    private LocalDateTime inTime;           // 入场时间
    private LocalDateTime outTime;          // 出场时间
    private BigDecimal totalFee;            // 停车总费用

    public ParkingOrder(String orderNo, String plate, CarType carType, ParkingSpace space, LocalDateTime inTime) {
        this.orderNo = orderNo;
        this.plate = plate;
        this.carType = carType;
        this.parkSpace = space;
        this.inTime = inTime;
    }

    // getter/setter
    public String getOrderNo() { return orderNo; }
    public String getPlate() { return plate; }
    public CarType getCarType() { return carType; }
    public ParkingSpace getParkSpace() { return parkSpace; }
    public LocalDateTime getInTime() { return inTime; }
    public LocalDateTime getOutTime() { return outTime; }
    public void setOutTime(LocalDateTime outTime) { this.outTime = outTime; }
    public BigDecimal getTotalFee() { return totalFee; }
    public void setTotalFee(BigDecimal totalFee) { this.totalFee = totalFee; }
}
