package thread.worldClock;
import javax.swing.*;
/*
JFrame是Frame的子类，从Frame继承的方法：setTitle()
 */
public class WorldClock extends JFrame//多线程
{
    private TimerPanel tp;//TimerPanel为自定义类
    WorldClock()
    {
        super("世界时钟");
        this.tp=new TimerPanel();
        Thread t=new Thread(tp);//创建一个新线程，使它处于创建状态。此时是一个空线程，系统没有给它分配资源
        t.start();//使该线程开始执行；Java 虚拟机调用该线程的 run 方法。
        this.add(tp);

        this.setSize(1000,500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        new WorldClock();
    }
}



