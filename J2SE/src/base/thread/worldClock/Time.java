package base.thread.worldClock;
import java.awt.*;
import java.util.Date;
//若自己写一个无参构造方法，不初始化"Vector"，则报错
class Time {
    private int x,y;//每个钟表的坐标
    private String place;//每个钟表的地区，如“北京”
    private int timezone;//每个钟表的时区,东x区为+x,西x区为-x
    private Date d;
    private long time;
    private double hour,minite,second;
    Time(int x,int y,String place,int timezone) {
        this.x=x;
        this.y=y;
        this.place=place;
        this.timezone=timezone;
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);//将此图形上下文的当前颜色设置为指定颜色。
        d=new Date();
        time=d.getTime();/*获得0时区1970年1月1日0点到现在的毫秒数*/
        hour=(((time/1000)+3600*timezone)%43200)*2*Math.PI/3600/12;/*计算时针弧度*/
        minite=(time/1000)%3600*2*Math.PI/3600;/*计算分针弧度*/
        second=(time/1000)%60*2*Math.PI/60;/*计算秒针弧度*/
        /*画出钟表轮廓和时针*/
        ((Graphics2D)g).setStroke(new BasicStroke(3.0f));//初始化画笔，使时钟图案的圆形粗一些
        ((Graphics2D)g).drawOval(x, y, 100, 100);//画个圆
        ((Graphics2D)g).drawLine(x+50, y, x+50, y+5);//画一条线段
        ((Graphics2D)g).drawLine(x+50, y+100, x+50, y+95);
        ((Graphics2D)g).drawLine(x, y+50, x+5, y+50);
        ((Graphics2D)g).drawLine(x+100,y+50, x+95, y+50);
        ((Graphics2D)g).drawLine(x+50,y+50,(int)(x+50+25*Math.sin(hour)),(int)(y+50-25*Math.cos(hour)));
        /*画出分针*/
        ((Graphics2D)g).setStroke(new BasicStroke(2.0f));
        ((Graphics2D)g).drawLine(x+50,y+50,(int)(x+50+35*Math.sin(minite)),(int)(y+50-35*Math.cos(minite)));
        /*画出秒针*/
        ((Graphics2D)g).setStroke(new BasicStroke(1.0f));
        ((Graphics2D)g).drawLine(x+50,y+50,(int)(x+50+45*Math.sin(second)),(int)(y+50-45*Math.cos(second)));
        /*画出钟表名字*/
        g.setColor(Color.red);
        g.drawString(place, x+35, y+120);
    }
}