package com.matchacloud.basic.base.encapsulation;

public class Test {

    public static void main(String[] args) {

        Point A = new Point(1, 1);
        Point B = new Point(1, 5);
        Circle c1 = new Circle(A, 1);
        Circle c2 = new Circle(B, 1);

        /**
         * 上转型对象
         */
        Circle c6 = new ColoredCircle();

        A.add();
        System.out.println(A.equals(B));
        System.out.println(A.distance(B));
        System.out.println(c1.getArea());
        System.out.println(c1.getCircumference());
        System.out.println(new ColoredCircle());
        System.out.println(c1.PositionalRelation(c1));
        System.out.println(c1.PositionalRelation(c2));

        if (A instanceof Point) {
            System.out.println("A是Point类创建出来的实例");
        }

        Point y = new Point(3, 1);
        Point z = new Point(3, 1);
        System.out.println(y == z);
        System.out.println(y.equals(z));
    }
}
