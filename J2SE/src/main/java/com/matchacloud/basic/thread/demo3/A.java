package thread.demo3;

/**
 * start和run是有区别的
 */
public class A extends Thread{
    A(){
        setDaemon(true);
    }

    public void run(){
        (new B()).start();
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            System.out.println("A done");
        }
    }

    public static void main(String[] args) {
        (new A()).start();
    }
}
