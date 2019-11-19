package partition1;
// STOPSHIP: 2019/9/5
public class AA {
    public static void main(String[] args) {
//        System.out.println(new AA().test());
//        System.out.println(4&7);
//        Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;
//        System.out.println(f1==f2);
//        System.out.println(f3==f4);
//        System.out.println(1 + 2 + "TCL" + 3 + 4);
//        if (args == null || new AA() {
//            {
//                AA.main(null);
//            }
//        }.equals(null)){
//            System.out.println("A");
//        }else {
//            System.out.println("B");
//        }


        int a = 2, b = 3, c = 4;
        System.out.printf("a=%d,", ++a);
        c = a + b++;
        System.out.printf("c=%d,", c);
        try {
            a++;
            b = b / 0;
        } catch (Exception e) {
            ++b;
            System.out.printf("b=%d,", b++);
            c = a + b;
            return;
        } finally{
            c = ++a + b++;
            System.out.printf("c=%d", c);
        }
        // STOPSHIP: 2019/9/5
    }

    int test() {
        try {
            return func1();
        } finally{
            return func2();
        }
    }

    int func1() {
        System.out.println("func1");
        return 1;
    }
    int func2() {
        System.out.println("func2");
        return 2;
    }
}
