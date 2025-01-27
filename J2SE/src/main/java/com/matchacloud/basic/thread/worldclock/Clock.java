package com.matchacloud.basic.thread.worldclock;
import java.awt.*;
import java.util.Date;

/**
 * 钟表类
 */
public class Clock {

    /**
     * 该钟表在面板的位置坐标x
     */
    private int x;

    /**
     * 该钟表在面板的位置坐标y
     */
    private int y;

    /**
     * 钟表的地区，如北京
     */
    private String place;

    /**
     * 钟表的时区,东x区为+x,西x区为-x
     */
    private int timezone;

    private Date currentTime;

    private long currentTs;

    private double hour;

    private double minute;

    private double second;

    Clock(int x, int y, String place, int timezone) {
        this.x = x;
        this.y = y;
        this.place = place;
        this.timezone = timezone;
    }

    /**
     * 画出该钟表
     * @param g
     */
    public void draw(Graphics g) {
        //将此图形上下文的当前颜色设置为指定颜色
        g.setColor(Color.green);
        currentTime = new Date();
        //获得0时区1970年1月1日0点到现在的毫秒数
        currentTs = currentTime.getTime();
        //计算时针弧度
        hour = (((currentTs / 1000) + 3600 * timezone) % 43200) * 2 * Math.PI / 3600 / 12;
        //计算分针弧度
        minute = (currentTs / 1000) % 3600 * 2 * Math.PI / 3600;
        //计算秒针弧度
        second = (currentTs / 1000) % 60 * 2 * Math.PI / 60;

        /******画出钟表轮廓和时针******/

        //初始化画笔，使时钟图案的圆形粗一些
        ((Graphics2D) g).setStroke(new BasicStroke(3.0f));
        //画个圆
        ((Graphics2D) g).drawOval(x, y, 100, 100);
        //画一条线段
        ((Graphics2D) g).drawLine(x + 50, y, x + 50, y + 5);
        ((Graphics2D) g).drawLine(x + 50, y + 100, x + 50, y + 95);
        ((Graphics2D) g).drawLine(x, y + 50, x + 5, y + 50);
        ((Graphics2D) g).drawLine(x + 100, y + 50, x + 95, y + 50);
        ((Graphics2D) g).drawLine(x + 50, y + 50, (int) (x + 50 + 25 * Math.sin(hour)), (int) (y + 50 - 25 * Math.cos(hour)));
        //画出分针
        ((Graphics2D) g).setStroke(new BasicStroke(2.0f));
        ((Graphics2D) g).drawLine(x + 50, y + 50, (int) (x + 50 + 35 * Math.sin(minute)), (int) (y + 50 - 35 * Math.cos(minute)));
        //画出秒针
        ((Graphics2D) g).setStroke(new BasicStroke(1.0f));
        ((Graphics2D) g).drawLine(x + 50, y + 50, (int) (x + 50 + 45 * Math.sin(second)), (int) (y + 50 - 45 * Math.cos(second)));
        //画出钟表名字
        g.setColor(Color.red);
        g.drawString(place, x + 35, y + 120);
    }
}