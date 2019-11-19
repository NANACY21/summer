package partition2;
public class chitao {
    public static void main(String[] args) {
        /*
        爬出井问题
         */
        int dayy=0;
        int m=0;
        while(dayy>=0){
            dayy++;
            m=m+3;
            if(m>=7){
                System.out.print(dayy);
                break;
            }
            m=m-2;
            if(m>=7){
                System.out.print(dayy);
                break;
            }
        }


        //猴子吃桃问题:猴子第一天摘了一些桃，第二天吃了上一天的一半还多一，每天都如此，第十天剩1，求第一天摘了多少
        int shengyu=1,yy=1;
        while (yy<10){
            shengyu=(shengyu+1)*2;
            yy++;
        }
        System.out.println(shengyu);

        //求大于200的最小的质数,用平方根以提高性能
//        int min=201;
        long min=201;
        int ii=0;
        while(true){
            for(ii=2;ii<min;ii++){
                if(min%ii==0){
                    min=min+1;
                    break;
                }
                else{
                    continue;
                }
            }

            if(ii==min){
                System.out.println(min);
                break;
            }
        }
    }
}
