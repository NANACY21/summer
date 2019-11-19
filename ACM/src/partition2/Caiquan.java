package partition2;
import java.util.Scanner;
public class Caiquan {
    int me;
    int computer;
    private static final Object lock = new Object();

    public Object getLock() {
        return lock;
    }

    public void setMe(int me) {
        this.me = me;
    }

    public void setComputer(int computer) {
        this.computer = computer;
    }

    public static void main(String[] args) throws InterruptedException {
        Caiquan c=new Caiquan();
        Thread me=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (c.getLock())//不可以锁一个null对象
                {
                    Scanner scan = new Scanner(System.in);
                    System.out.println("你出啥？[1 - 石头 2 - 剪刀 3 - 布]");
                    int a = scan.nextInt();
                    if (a == 1)
                    {
                        System.out.println("您出石头");
                        c.setMe(a);
                    }
                    else if (a == 2)
                    {
                        System.out.println("您出剪刀");
                        c.setMe(a);
                    }
                    else if (a == 3)
                    {
                        System.out.println("您出布");
                        c.setMe(a);
                    }
                    else
                    {
                        System.out.println("输入错误，请重新输入");
                    }
                }
            }
        },"me");
        Thread computer=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (c.getLock())
                {
                    double shu = Math.random();
                    int i = (int) Math.floor(Math.random() * 3);
                    if (i == 1)
                    {
                        System.out.println("电脑出石头");
                        c.setComputer(i);
                    }
                    if (i == 2)
                    {
                        System.out.println("电脑出剪刀");

                        c.setComputer(i);
                    }
                    if (i == 3)
                    {
                        System.out.println("电脑出布");

                        c.setComputer(i);
                    }
                }
            }
        },"computer");
//        me.setPriority(Thread.MAX_PRIORITY);//最高优先级
        me.start();
        me.join();
//        computer.setPriority(Thread.MIN_PRIORITY);//最低优先级
        computer.start();//电脑
        computer.join();
        c.compare();
    }

    public void compare() {

        if (this.me == 1 && this.computer == 2) {
            System.out.println("您获胜");
        } else if (this.me == 1 && this.computer == 3) {
            System.out.println("电脑获胜");
        } else if (this.me == 1 && this.computer == 1) {
            System.out.println("平局");
        } else if (this.me == 2 && this.computer == 3) {
            System.out.println("您获胜");
        } else if (this.me == 2 && this.computer == 2) {
            System.out.println("平局");
        } else if (this.me == 2 && this.computer == 1) {
            System.out.println("电脑获胜");
        } else if (this.me == 3 && this.computer == 3) {
            System.out.println("平局");
        } else if (this.me == 3 && this.computer == 2) {
            System.out.println("电脑获胜");
        } else if (this.me == 3 && this.computer == 1) {
            System.out.println("您获胜");
        }
    }
}
