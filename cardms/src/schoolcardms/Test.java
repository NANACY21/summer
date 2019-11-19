package schoolcardms;

import schoolcardms.ui.MainUI;
/*
粘贴内容到文本框报错
转账导入银行系统的database类有问题
移除一个不存在的卡机报错

一卡通实体类、学生类
系统名称：一卡通管理系统，即产品（product）管理系统

2个版本银行卡管理系统（IO/JDBC）
2个版本一卡通管理系统（IO/JDBC）
选课系统（.net mysql）
个人网上商店（.net mysql）
 */

public class Test {

    public static void main(String[] args) {

        /*
        schoolcardms如果使用文件存储（所有data：3个.txt）：
        1个.txt存储1个Vector对象，所有卡机类对象在该Vector对象中
        1个.txt存储1个Vector对象，所有一卡通用户类对象在该Vector对象中
        1个.txt存储1个Vector对象，所有交易记录类对象在该Vector对象中

        如果使用文件存储，代码、数据一般在同一目录下

        一个用户操作时所有用户读取到内存在写到外存，很不合理
        不支持多用户并发，即使并发这样读写也会有问题，

        该系统需要访问bankcardms，事实上是访问后者的db，想办法连入银行的db即可
        该系统和bankcardms不一定必须在同一个模块里

        如果在同一个模块里，bankcard的db对象以访问




        bankcardms如果使用文件存储（所有data：2个.txt）：
        1个.txt存储1个Vector对象，所有交易记录类对象在该Vector对象中
        1个.txt存储1个Vector对象，所有银行卡用户类对象在该Vector对象中
        随机生成不重的16位银行卡号，这是单线程，这不是线程安全的，另外，
        和文件中银行卡号比对，容易死循环，有可能自旋，浪费CPU资源
         */
        MainUI mi = new MainUI();//mi为引用
    }
}