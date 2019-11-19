package partition2;
import java.util.Scanner;
public class Chashu {
    //第五题
    private double a[];
    private int kongyu;//空余位置个数
    private  int yu;//空余位置索引
    public Chashu() {
        super();
        a=new double[20];
        for(int i=0;i<20;i++)
        {
            a[i]=-1;
        }
        kongyu=20;
        yu=0;
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args)
    {
        Chashu q=new Chashu();
        q.shuru();
        for(int i=0;i<q.yu;i++)
        {
            System.out.print(q.a[i]+"  ");
        }
    }
    public void shuru()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入1个数字");
        double a = scan.nextDouble();
        if(a==0)
        {
            System.out.println("错误，请重新输入");
            shuru();
        }
        else
        {
            if(find(a)==true)
            {
                System.out.println("该数已存在，不可以向数组中插入，");
                System.out.println("是否输入下一个数(Y(1) or N(2))");
                int g=scan.nextInt();
                if(g==1)
                {
                    shuru();
                }
                else
                {

                }

            }
            else
            {
                if(this.kongyu==0)
                {
                    System.out.println("位置已满");
                }
                else
                {
                    this.insert(a);
                    int d=this.yu-1;
                    System.out.println("添加成功，添加位置：索引"+d);
                    System.out.println("是否输入下一个数(Y(1) or N(2))");
                    int g=scan.nextInt();
                    if(g==1)
                    {
                        shuru();
                    }
                    else
                    {

                    }

                }
            }
        }
    }
    public boolean find(double ar)
    {
        for(int i=0;i<20;i++)
        {
            if(a[i]==ar)
            {
                return true;
            }
        }
        return false;
    }
    public void insert(double ar)
    {
        this.a[this.yu]=ar;
        this.yu++;
        this.kongyu--;
    }
}
