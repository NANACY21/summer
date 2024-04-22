package designpattern.singleton;

public class Main {

    public static void main(String[] args) {

        Tiger tiger1 = Tiger.INSTANCE;
        Tiger tiger2 = Tiger.INSTANCE;
        System.out.println(tiger1 == tiger2);
    }
}
