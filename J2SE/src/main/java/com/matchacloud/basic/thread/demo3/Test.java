package thread.demo3;

/**
 * 不会
 */
public class Test implements Runnable {
    private int x=0;
    private int y=0;
    boolean flag = true;
    @Override
    public void run() {
        while (flag){
            x++;
            y++;
            System.out.println("("+x+","+y+")");
            if(x>=10){
                flag=false;
            }
        }
    }

    public static void main(String[] args) {
        Test r=new Test();
        Thread t1=new Thread(r);
        Thread t2=new Thread(r);
        t1.start();
        t2.start();
    }
}
