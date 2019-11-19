package partition1;

import java.util.Arrays;
import java.util.Scanner;
// STOPSHIP: 2019/9/5 瓜子二手车笔试
public class Guazi {
    public static void main (String[] args) {
        Scanner scan=new Scanner(System.in);
        int sum=scan.nextInt();
        int[] input=new int[sum];
        for (int i=0;i<sum;i++){
            input[i]=scan.nextInt();
        }
        int num=test1(input);
        System.out.println(num);

    }
    public static int test1(int[] input){

        int a=0;
        if(input.length==0){
            return 1;
        }
        Arrays.sort(input);
        while(a<input.length){
            if(input[a]<input.length && input[a] >=1){
                int temp=input[input[a]-1];
                input[input[a]-1]=input[a];
                input[a]=temp;
            }
            a++;
        }
        for(int i=0;i<input.length;i++){
            if(input[i] !=i+1){
                return i+1;
            }
        }
        return input.length+1;
    }
}
