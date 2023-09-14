package hik;


import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class A {
    public static void main(String[] args) throws Exception {
        BigDecimal num1 = new BigDecimal("10");

        BigDecimal num2 = new BigDecimal("3");

        //BigDecimal num3 = num1.divide(num2);
        BigDecimal num3 = num1.divide(num2, 6, BigDecimal.ROUND_HALF_DOWN);
        System.out.println(num3);

    }
}
