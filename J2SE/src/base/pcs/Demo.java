package base.pcs;

/**
 * 程序控制语句
 */
public class Demo {

    public static void main(String[] args) {
        int x = 2;
        switch (x) {
            case 1:
                System.out.println("hello 1");
                break;
            case 2:
            case 3:
                System.out.println("hello 2");
                break;
        }
    }
}
