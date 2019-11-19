package partition2;

import java.util.Scanner;

public class Lingxing {
    private String aaa[];
    int cou;
    public Lingxing() {
        this.aaa = new String[8];
        //for(int i=0;i<8;i++)
            aaa[0]="祝";aaa[1]="大";aaa[2]="家";aaa[3]="中";aaa[4]="秋";
        aaa[5]="节";aaa[6]="快";aaa[7]="乐";
        cou=0;
    }

    public String[] getAaa() {
        return aaa;
    }

    public static void main(String[] args) {

//      菱形边长为n,有2n-1行
//      要点：算每一行的空数和*数和行数的关系
        Lingxing k=new Lingxing();
        Scanner scan=new Scanner(System.in);
        System.out.println("请输入一个数");
        int shu=scan.nextInt();
        int i=0,j=0;
        int g=shu*2-1;
        for(i=1;i<=g;i++)//hang
        {
            if(i>shu)
            {
                j=2*shu-i;
                int a=(int)(g-(2*j-1))/2;
                for(int x=0;x<a;x++)
                {
                    System.out.print(' ');
                }
                if(i>1)
                {
                    int u=(int)(2*j-1-2)/2;
                    for(int v=0;v<u;v++)
                    {
                        System.out.print('*');
                    }
                    if(k.cou<8)
                    {
                        System.out.print(k.getAaa()[k.cou]);
                        k.cou++;
                    }
                    for(int v=0;v<u;v++)
                    {
                        System.out.print('*');
                    }

                }
                else
                {
                    for(int c=0;c<2*j-1;c++)
                    {
                        System.out.print('*');
                    }
                }
                for(int x=0;x<a;x++)
                {
                    System.out.print(' ');
                }
                System.out.println();
            }
            else
            {
                int a=(int)(g-(2*i-1))/2;
                for(int x=0;x<a;x++)
                {
                    System.out.print(' ');
                }
                if(i>1)
                {
                    int u=(int)(2*i-1-2)/2;
                    for(int v=0;v<u;v++)
                    {
                        System.out.print('*');
                    }
                    if(k.cou<8)
                    {
                        System.out.print(k.getAaa()[k.cou]);
                        k.cou++;
                    }
                    for(int v=0;v<u;v++)
                    {
                        System.out.print('*');
                    }

                }
                else
                {
                    for(int c=0;c<2*i-1;c++);
                    System.out.print('*');
                }
                for(int x=0;x<a;x++)
                {
                    System.out.print(' ');
                }
                System.out.println();
            }

        }
    }
}
