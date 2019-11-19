package base.java8.stream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 流 打开 关闭
 * 学以致用
 */
public class TestStream2 {

    public static void main(String[] args) throws ScriptException {

        //数组转链表
        List<Integer> l = Arrays.asList(1, 2, 3, 5, 6, 4, 9);
        Stream<Integer> is = l.stream();
        //元素和
        Integer sum = is.reduce(0, (x, y) -> x + y);

        is = l.stream();
        //给每个元素*2再累加求和
        sum = is.reduce(0, (x, y) -> x + y * 2);


        //*2后转出来
        is = l.stream();
        List<Integer> collect = is.map(x -> x * 2).collect(Collectors.toList());
        /*----------------------------------------*/


        List<Person> ps = Arrays.asList(new Person(23), new Person(16), new Person(19), new Person(15));

        /**成年人数量，封装成Adult对象 map还可以转换了类型
         * ps放流里
         */
        long count = ps.stream().filter(p -> p.getAge() >= 18).map(p -> new Adult(p.getAge())).count();
        /*----------------------------------------*/
//        scala
//        flatMap 扁平化 大数据 读文件常用

        List<String> strs = Arrays.asList("aa bb", "cc dd", "ee ff");
        strs.stream().map(x -> x.split(" ")).forEach(System.out::println);//打印三个数组
        //把数组转成流
        strs.stream().map(x -> x.split(" ")).flatMap(Arrays::stream).forEach(System.out::println);

        //顺序流 元素一个一个执行
        //并行流 cpu并行化 并行执行 有些又先后顺序的事件不能并行化
        //1-1000000的和

        //collect 收集器 对结果的收集
        //strs.stream().parallel().collect();//并行化




        long l1 = System.currentTimeMillis();//当前时间
        System.nanoTime();//??
        //生成1-1000000区间data
        IntStream range = IntStream.range(0, 1_000_000);//返回值是流，说明还在流里
        range.filter(p -> p % 2 == 0).toArray();
        long l2 = System.currentTimeMillis();//当前时间
        long l3 = System.currentTimeMillis();//当前时间
        IntStream.range(1, 1_1000_000).parallel().filter(p -> p % 2 == 0).toArray();//toArray流也就终止了，无需手动关
        long l4 = System.currentTimeMillis();//当前时间 单位是毫秒 l4-l3

        strs.stream().parallel();//并行化

        IntStream range1 = IntStream.range(1, 100);
        range1.toArray();//终止 关了
        range1.count();//这会报错，因为流已经关了

        //java8改进时间


        //支持调用.js：但不是可以写任意的js代码
        //进入java目录 jjs f.js
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");//拿到引擎
        System.out.println(engine.getClass().getName());
        System.out.println("Result:" + engine.eval("function f(){return 1;}; f()+1;"));

        try {
            Reader r = new FileReader("jj.js");
            System.out.println(engine.eval(r));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
