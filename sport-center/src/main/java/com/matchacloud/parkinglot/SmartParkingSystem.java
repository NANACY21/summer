package com.matchacloud.parkinglot;

import java.util.Scanner;

/**
 * 智能停车系统 程序入口
 */
public class SmartParkingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParkingLotManager manager = new ParkingLotManager();
        while (true) {
            // 打印菜单
            System.out.println("\n========== 智能多层停车场管理系统 ==========");
            System.out.println("1. 停车场初始化（设置楼层、车位）");
            System.out.println("2. 车辆入场（自动分配最优车位，生成票据）");
            System.out.println("3. 车辆出场（自动计费，释放车位）");
            System.out.println("4. 空闲车位查询（按楼层/车位类型）");
            System.out.println("5. 停车费用规则说明");
            System.out.println("6. 生成统计报表（收入、车位利用率）");
            System.out.println("0. 退出系统");
            System.out.print("请输入操作序号：");
            int select;
            try {
                select = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("输入非法数字！");
                continue;
            }
            scanner.nextLine();
            switch (select) {
                case 1:
                    manager.initParkingLot(scanner);
                    break;
                case 2:
                    manager.carIn(scanner);
                    break;
                case 3:
                    manager.carOut(scanner);
                    break;
                case 4:
                    manager.queryFreeSpace(scanner);
                    break;
                case 5:
                    showFeeRule();
                    break;
                case 6:
                    manager.showReport();
                    break;
                case 0:
                    System.out.println("系统退出，感谢使用！");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("序号不存在，请重新输入！");
            }
        }
    }

    // 计费规则说明
    private static void showFeeRule() {
        System.out.println("\n==================== 计费规则 ====================");
        System.out.println("1. 车型基础单价（元/小时，日间6:00-22:00）");
        System.out.println("   小型轿车：5元 | SUV：6元 | 货车：10元");
        System.out.println("2. 车位费率系数：普通×1.0 VIP×1.5 残疾人×0.6");
        System.out.println("3. 夜间时段(22:00-6:00)全部半价 ×0.5");
        System.out.println("4. 停车时长不足1小时按1小时计算");
        System.out.println("==================================================");
    }
}
