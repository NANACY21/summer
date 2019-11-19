package partition2;

public class yanghuisanjiao {
    public static void main(String[] args) {
        /**
         * 杨辉三角示例（不一定对）
         */
        final int N=7;//常量
        int c[][]=new int[N][N],m,n,j;
        for(n=0;n<N;n++){
            c[n][n]=1;
            c[n][0]=1;
        }
        for(n=2;n<N;n++){
            for(m=1;m<=n-1;m++){
                c[n][m]=c[n-1][m-1]+c[n-1][m];
            }
            for(n=0;n<N;n++){
                for(j=1;j<N-n;j++){
                    //System.out.println(String.format("*%1$-10s*","moon"));
                    //System.out.println(String.format("*%1$10s*","moon"));
//                    cout<<setw(3)<<'';
                }
                for(m=0;m<=n;m++){
//                    cout<<setw(6)<<c[n][m];
                    System.out.print(c[n][m]);
                }
                System.out.println();
            }
        }
    }
}