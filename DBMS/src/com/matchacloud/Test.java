package com.matchacloud;

import java.util.LinkedList;
import java.util.Scanner;

/**为了了解数据库实现原理
 * this is a DBMS - MyDBMS
 * myDBMS数据类型集={String,double,int,long,char}
 * 处理sql语句方法：拆分字符串、截取有效词、去掉多余子字符串
 * <p>
 * 另一种设计模式，把每个功能做成一个类，insert类，建表类
 * 算法能简易绝不复杂，自造的算法多半有bug
 * @author 李箎
 *
 */
public class Test {

    private static LinkedList<String> log = null;

    /**
     * 仅在输入命令时调用
     *
     * @param newLog
     */
    public static void addLog(String newLog) {
        log.add(newLog);
    }

    public static LinkedList<String> getLog() {
        return log;
    }

    public static void main(String[] args) {
        String info1 = "error";
        int num1 = 8;
        int num2 = 14;
        Scanner scan = new Scanner(System.in);


        System.out.print("input user id ");
        String userId = scan.nextLine();
        System.out.print("input login password ");
        String password = scan.nextLine();

        //登录
        String hint = DataProcess.login(userId, password);
        if (hint.startsWith(info1, num1) || hint.startsWith(info1, num2)) {

            System.out.print(hint);
        }

        //登录成功
        else {
            String info = "exit;";
            log = new LinkedList<>();
            System.out.println("欢迎！");

            String sql = "";
            while (!info.equals(sql)) {

                System.out.print("MyDBMS> ");
                //接收sql语句
                sql = scan.nextLine();
                addLog(sql);
                //执行sql语句命令
                SqlParser.recognition(sql);
            }
            scan.close();
        }
        System.exit(0);
    }
}