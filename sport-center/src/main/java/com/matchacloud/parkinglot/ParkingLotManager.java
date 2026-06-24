package com.matchacloud.parkinglot;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 停车场
 */
public class ParkingLotManager {

    private List<List<ParkingSpace>> floorList;  // 楼层集合 floorList.get(0) = 1楼
    private List<ParkingOrder> orderList;       // 全部订单
    private String lotName;
    private int floorCount;                     // 总楼层数
    private int totalSpace = 0;                // 总车位
    private int usedSpace = 0;                 // 已占用车位
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ParkingLotManager() {
        floorList = new ArrayList<>();
        orderList = new ArrayList<>();
    }

    // 1. 停车场初始化
    public void initParkingLot(Scanner sc) {
        System.out.print("请输入停车场名称：");
        this.lotName = sc.next();
        System.out.print("请输入停车场总楼层数：");
        this.floorCount = sc.nextInt();
        sc.nextLine();

        floorList.clear();
        totalSpace = 0;
        for (int i = 1; i <= floorCount; i++) {
            List<ParkingSpace> floorSpaces = new ArrayList<>();
            System.out.println("\n===== 初始化第" + i + "层 =====");
            System.out.print("该层普通车位数量：");
            int normal = sc.nextInt();
            System.out.print("该层VIP车位数量：");
            int vip = sc.nextInt();
            System.out.print("该层残疾人专用车位数量：");
            int disable = sc.nextInt();
            sc.nextLine();

            int spaceNum = 1;
            // 创建普通车位
            for (int j = 0; j < normal; j++) {
                floorSpaces.add(new ParkingSpace(spaceNum++, i, ParkingSpaceType.NORMAL));
            }
            // 创建VIP车位
            for (int j = 0; j < vip; j++) {
                floorSpaces.add(new ParkingSpace(spaceNum++, i, ParkingSpaceType.VIP));
            }
            // 创建残疾人车位
            for (int j = 0; j < disable; j++) {
                floorSpaces.add(new ParkingSpace(spaceNum++, i, ParkingSpaceType.DISABLED));
            }
            floorList.add(floorSpaces);
            totalSpace += floorSpaces.size();
        }
        System.out.println("\n【初始化完成】");
        System.out.println("停车场：" + lotName + " | 总楼层：" + floorCount + " | 总车位：" + totalSpace);
    }

    // 2. 智能分配最优车位（优先低层普通车位，VIP车辆可匹配VIP）
    public ParkingSpace getBestSpace(CarType carType, boolean isVipCar, boolean isDisableCar) {
        // 逐层遍历，从1楼开始优先低层
        for (List<ParkingSpace> floor : floorList) {
            List<ParkingSpace> freeSpaces = floor.stream()
                    .filter(s -> !s.isUsed())
                    .collect(Collectors.toList());
            if (freeSpaces.isEmpty()) continue;

            // 残疾人车辆优先残疾人车位
            if (isDisableCar) {
                Optional<ParkingSpace> disableSpace = freeSpaces.stream()
                        .filter(s -> s.getType() == ParkingSpaceType.DISABLED)
                        .findFirst();
                if (disableSpace.isPresent()) return disableSpace.get();
            }
            // VIP车辆优先VIP车位
            if (isVipCar) {
                Optional<ParkingSpace> vipSpace = freeSpaces.stream()
                        .filter(s -> s.getType() == ParkingSpaceType.VIP)
                        .findFirst();
                if (vipSpace.isPresent()) return vipSpace.get();
            }
            // 优先普通车位
            Optional<ParkingSpace> normalSpace = freeSpaces.stream()
                    .filter(s -> s.getType() == ParkingSpaceType.NORMAL)
                    .findFirst();
            if (normalSpace.isPresent()) return normalSpace.get();

            // 无匹配则返回任意空闲车位
            return freeSpaces.get(0);
        }
        return null; // 无空闲车位
    }

    // 车辆入场
    public void carIn(Scanner sc) {
        if (floorList.isEmpty()) {
            System.out.println("请先执行【1 停车场初始化】！");
            return;
        }
        System.out.print("输入车辆车牌：");
        String plate = sc.next();
        // 判断车牌是否已在场
        boolean exist = orderList.stream().anyMatch(o -> !o.getParkSpace().isUsed() && o.getPlate().equals(plate));
        if (exist) {
            System.out.println("该车辆已在停车场内，无法重复入场！");
            return;
        }

        System.out.println("选择车辆类型：1小型轿车 2SUV 3货车");
        int carTypeOpt = sc.nextInt();

        CarType carType;
        switch (carTypeOpt) {
            case 1:
                carType = CarType.SMALL_CAR;
                break;
            case 2:
                carType = CarType.SUV;
                break;
            case 3:
                carType = CarType.TRUCK;
                break;
            default:
                System.out.println("输入错误，默认小型轿车");
                carType = CarType.SMALL_CAR;
                break;
        }
        System.out.print("是否VIP车辆(1是/0否)：");
        boolean isVip = sc.nextInt() == 1;
        System.out.print("是否残疾人车辆(1是/0否)：");
        boolean isDisable = sc.nextInt() == 1;
        sc.nextLine();

        // 分配车位
        ParkingSpace space = getBestSpace(carType, isVip, isDisable);
        if (space == null) {
            System.out.println("停车场暂无空闲车位！");
            return;
        }
        // 占用车位
        space.setUsed(true);
        space.setOccupiedPlate(plate);
        usedSpace++;

        // 生成订单票据
        String orderNo = "PARK" + System.currentTimeMillis();
        LocalDateTime inTime = LocalDateTime.now();
        ParkingOrder order = new ParkingOrder(orderNo, plate, carType, space, inTime);
        orderList.add(order);

        // 打印入场票据
        System.out.println("\n==================== 停车入场票据 ====================");
        System.out.println("票据单号：" + orderNo);
        System.out.println("停车场：" + lotName);
        System.out.println("车牌号码：" + plate);
        System.out.println("车辆类型：" + carType.getDesc());
        System.out.println("停放楼层：" + space.getFloor() + "层");
        System.out.println("车位编号：" + space.getSpaceId());
        System.out.println("车位类型：" + space.getType().getDesc());
        System.out.println("入场时间：" + inTime.format(fmt));
        System.out.println("======================================================");
    }

    // 费用计算核心方法
    // 费用计算
    private BigDecimal calculateFee(ParkingOrder order) {
        LocalDateTime in = order.getInTime();
        LocalDateTime out = order.getOutTime();
        Duration duration = Duration.between(in, out);
        // Java8 兼容计算停车小时
        long totalMinutes = duration.toMinutes();
        long hours = totalMinutes / 60;
        if (totalMinutes % 60 > 0) {
            hours++;
        }

        CarType car = order.getCarType();
        ParkingSpaceType spaceType = order.getParkSpace().getType();
        BigDecimal baseHour = new BigDecimal(car.getBasePrice());
        BigDecimal spaceRate = new BigDecimal(spaceType.getRate());

        BigDecimal timeRate;
        int hour = out.getHour();
        if (hour >= 22 || hour < 6) {
            timeRate = new BigDecimal("0.5");
        } else {
            timeRate = BigDecimal.ONE;
        }

        BigDecimal total = baseHour.multiply(spaceRate)
                .multiply(timeRate)
                .multiply(new BigDecimal(hours))
                .setScale(2, RoundingMode.HALF_UP);
        return total;
    }

    // 车辆出场
    public void carOut(Scanner sc) {
        if (floorList.isEmpty()) {
            System.out.println("请先初始化停车场！");
            return;
        }
        System.out.print("输入出场车辆车牌：");
        String plate = sc.next();
        // 找到未结算订单
        List<ParkingOrder> orderOpt = orderList.stream()
                .filter(o -> o.getPlate().equals(plate) && o.getOutTime() == null)
                .collect(Collectors.toList());
        if (orderOpt.isEmpty()) {
            System.out.println("未找到该入场车辆，请核对车牌！");
            return;
        }
        ParkingOrder order = orderOpt.get(0);
        LocalDateTime outTime = LocalDateTime.now();
        order.setOutTime(outTime);
        // 计算费用
        BigDecimal fee = calculateFee(order);
        order.setTotalFee(fee);

        // 释放车位
        ParkingSpace space = order.getParkSpace();
        space.setUsed(false);
        space.setOccupiedPlate(null);
        usedSpace--;

        // 出场结算单
        System.out.println("\n==================== 停车结算单 ====================");
        System.out.println("票据单号：" + order.getOrderNo());
        System.out.println("车牌：" + plate);
        System.out.println("入场：" + order.getInTime().format(fmt));
        System.out.println("出场：" + outTime.format(fmt));
        System.out.println("停放楼层：" + space.getFloor() + "层 车位：" + space.getSpaceId());
        System.out.println("车位类型：" + space.getType().getDesc());
        System.out.println("应付停车费：¥" + fee);
        System.out.println("====================================================");
    }

    // 按楼层/车位类型查询空闲车位
    public void queryFreeSpace(Scanner sc) {
        if (floorList.isEmpty()) {
            System.out.println("请先初始化停车场！");
            return;
        }
        System.out.println("1 按楼层查询  2 按车位类型查询");
        int opt = sc.nextInt();
        sc.nextLine();
        if (opt == 1) {
            System.out.print("输入要查询的楼层：");
            int floorNo = sc.nextInt();
            if (floorNo < 1 || floorNo > floorCount) {
                System.out.println("楼层不存在");
                return;
            }
            List<ParkingSpace> targetFloor = floorList.get(floorNo - 1);
            List<ParkingSpace> free = targetFloor.stream().filter(s -> !s.isUsed()).collect(Collectors.toList());
            System.out.println("\n【第" + floorNo + "层空闲车位共" + free.size() + "个】");
            free.forEach(s -> System.out.println("车位" + s.getSpaceId() + " - " + s.getType().getDesc()));
        } else if (opt == 2) {
            System.out.println("1普通 2VIP 3残疾人专用");
            int typeOpt = sc.nextInt();

            ParkingSpaceType targetType;
            switch (typeOpt) {
                case 1:
                    targetType = ParkingSpaceType.NORMAL;
                    break;
                case 2:
                    targetType = ParkingSpaceType.VIP;
                    break;
                case 3:
                    targetType = ParkingSpaceType.DISABLED;
                    break;
                default:
                    System.out.println("输入错误，查询普通车位");
                    targetType = ParkingSpaceType.NORMAL;
                    break;
            }
            List<ParkingSpace> allFree = new ArrayList<>();
            for (List<ParkingSpace> floor : floorList) {
                allFree.addAll(floor.stream()
                        .filter(s -> !s.isUsed() && s.getType() == targetType)
                        .collect(Collectors.toList()));
            }
            System.out.println("\n【" + targetType.getDesc() + "空闲车位总数：" + allFree.size() + "】");
            allFree.forEach(s -> System.out.println(s.getFloor() + "层-" + "车位" + s.getSpaceId()));
        }
    }

    // 统计报表：总收入、车位利用率、各车位使用统计
    public void showReport() {
        if (floorList.isEmpty()) {
            System.out.println("无停车场数据");
            return;
        }
        List<ParkingOrder> finishedOrders = orderList.stream()
                .filter(o -> o.getOutTime() != null)
                .collect(Collectors.toList());
        // 总收入
        BigDecimal totalIncome = finishedOrders.stream()
                .map(ParkingOrder::getTotalFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // 车位利用率
        double useRate = totalSpace == 0 ? 0 : (double) usedSpace / totalSpace * 100;

        // 分类统计
        long normalUse = orderList.stream()
                .filter(o -> o.getParkSpace().getType() == ParkingSpaceType.NORMAL)
                .count();
        long vipUse = orderList.stream()
                .filter(o -> o.getParkSpace().getType() == ParkingSpaceType.VIP)
                .count();
        long disableUse = orderList.stream()
                .filter(o -> o.getParkSpace().getType() == ParkingSpaceType.DISABLED)
                .count();

        System.out.println("\n==================== 停车场统计报表 ====================");
        System.out.println("停车场名称：" + lotName);
        System.out.println("总楼层：" + floorCount + " 总车位：" + totalSpace);
        System.out.println("当前占用车位：" + usedSpace + " 空闲车位：" + (totalSpace - usedSpace));
        System.out.printf("车位实时利用率：%.2f%%%n", useRate);
        System.out.println("历史完成订单总数：" + finishedOrders.size());
        System.out.println("累计总收入：¥" + totalIncome.setScale(2, RoundingMode.HALF_UP));
        System.out.println("----------------------------------------");
        System.out.println("车位历史使用次数统计：");
        System.out.println("普通车位：" + normalUse + "次");
        System.out.println("VIP车位：" + vipUse + "次");
        System.out.println("残疾人车位：" + disableUse + "次");
        System.out.println("========================================================");
    }
}
