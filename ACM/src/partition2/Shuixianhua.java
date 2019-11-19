package partition2;

public class Shuixianhua {
    public static void main(String[] args) {
        System.out.println("[100,999]范围内的水仙花数");
        //法1
        for(int i=100;i<1000;i++){
            int gewei=(i/1) % 10;
            int shiwei=(i/10) % 10;
            int baiwei=i/100;
            if(i == gewei*gewei*gewei + shiwei*shiwei*shiwei + baiwei*baiwei*baiwei){
                System.out.print(i+"\t\t");
            }
        }

        //法2
        for(int bai=1;bai<10;bai++){
            for(int shi=0;shi<10;shi++){
                for(int ge=0;ge<10;ge++){
                    int target=bai*100+shi*10+ge;
                    int num=(int)(Math.pow(bai,3)+Math.pow(shi,3)+Math.pow(ge,3));
                    if(num==target){
                        System.out.print(target+"\t");
                    }
                }
            }
        }
    }
}
