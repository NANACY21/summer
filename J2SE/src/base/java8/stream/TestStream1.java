package base.java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 对集合批量操作
 *
 */
public class TestStream1 {

    public static void main(String[] args) {

        List<Integer> is = Arrays.asList(1, 2, 3, 4, 5, 6, 7);//不定参数

        //Stream不会改变原数据，data只是流进来再流出去
        Stream<Integer> isStream = is.stream();

        /**>3的个数 x 操作数 只保留x>3的值 filter是条件
         * 函数式编程（把一个函数当成一个参数传给另一个函数）
         * x+1后还在流里,指向一个操作
         */
        System.out.println(isStream.filter(x -> x > 3).count());//流结束


        //map是 +1  计算过程
        isStream = is.stream();
        System.out.println(isStream.map(x -> x + 1).filter(x -> x > 3).count());


        //打印流里的data
        is.forEach(x -> System.out.print(x + "\t"));

        //默认打印流里的data
        isStream = is.stream();
        isStream.map(x -> x + 1).forEach(System.out::println);
    }
}
