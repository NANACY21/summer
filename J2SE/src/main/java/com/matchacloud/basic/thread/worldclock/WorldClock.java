package com.matchacloud.basic.thread.worldclock;
import javax.swing.*;

/**
 *
 * 世界时钟页面(前端UI)
 */
public class WorldClock extends JFrame {

    private TimerPanel tp;

    WorldClock() {
        super("世界时钟");
        this.tp = new TimerPanel();
        //创建一个新线程，使它处于创建状态。此时是一个空线程，系统没有给它分配资源
        Thread t = new Thread(tp);
        //使该线程开始执行 Java 虚拟机调用该线程的 run()
        t.start();
        this.add(tp);

        this.setSize(1000, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new WorldClock();
    }
}



