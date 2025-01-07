package thread.worldClock;

import javax.swing.*;
import java.awt.*;

class TimerPanel extends JPanel implements Runnable
{
    Time[] t=new Time[6];
    TimerPanel() {
        t[0]=new Time(0,0,"中国-北京",8);
        t[1]=new Time(150,0,"法国-巴黎",1);
        t[2]=new Time(300,0,"美国-华盛顿特区",8);
        t[3]=new Time(0,150,"美国-洛杉矶",5);
        t[4]=new Time(150,150,"英国-伦敦",0);
        t[5]=new Time(300,150,"美国-芝加哥",7);
        setBackground(Color.black);
    }

    public void paint(Graphics g) {
        super.paint(g);
        for(int i=0;i<t.length;i++)
            t[i].draw(g);
    }
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }
}
