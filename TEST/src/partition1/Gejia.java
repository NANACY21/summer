package partition1;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Gejia {
    static int i = 0;

    public int a(int x) {
        i = i + x;
        return i;
    }
    public static void main(String[] args) {


        Set<Integer> I = new TreeSet<>();
        I.add(1);
        I.add(10);
        I.add(3);
        I.add(-3);
        I.add(-4);
        I.stream().unordered().forEach(System.out::println);//有序

    }
}
