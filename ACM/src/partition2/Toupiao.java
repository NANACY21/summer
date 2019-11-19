package partition2;
import java.util.Scanner;
public class Toupiao {
    private int h[];
    public Toupiao()
    {
        this.h=new int[5];
        for(int i=0;i<5;i++)
        {
            h[i]=0;
        }
    }
    public static void main(String[] args)
    {
        Toupiao t=new Toupiao();
        t.tou();
        // TODO Auto-generated method stub

    }
    public void tou()
    {


        Scanner scan = new Scanner(System.in);
//		System.out.println("请输入1,2,3一个数字【1 - 石头 2 - 剪刀 3 - 布】");
        System.out.println("请输入一个不为0的数以开始投票");
        int a = scan.nextInt();
        while(a!=0)
        {
            System.out.println("请开始投票");
            a = scan.nextInt();
            if(a<0 || a>5)
            {
                System.out.println("投票无效，请下一个人投票");
            }
            else if(a==0)
            {
                break;
            }
            else
            {
                int b=a-1;
                this.h[b]=this.h[b]+1;
            }
        }
        System.out.println("投票结果");
        for(int i=0;i<5;i++)
        {
            System.out.println(i+1+"号："+this.h[i]+"票");
        }
        int x=this.h[0];int s=0;
        for(int i=0;i<5;i++)
        {
            if(this.h[i]>x)
            {
                x=this.h[i];
                s=i+1;
            }
        }
        if(s==0)
        {
            for(int i=0;i<5;i++)
            {
                if(h[i]!=0)
                {
                    s=h[i];
                    break;
                }
            }
            System.out.println("都是"+s+"票，没人成为班长");
        }
        else
            System.out.println(s+"号成为班长");
    }

}
