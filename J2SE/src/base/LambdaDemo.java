package base;
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
 * java8新特性
 * 注解
 * runtime
 * 泛型类型推断
 * 反射
 */
public class LambdaDemo {

	/**
	 *
	 */
	public void lambdaTest() {
		String split = ",";

		//单条语句可以不用{}
		Arrays.asList("aa", "bb", "cc").forEach((String e) -> {
			// 多条语句
			e += split;
			System.out.println(e);
		});

		List<String> lst = Arrays.asList("bb", "cc", "aa");
		lst.sort((e1, e2) -> -e1.compareTo(e2));

		lst.forEach(e -> System.out.println(e));


		lst = Arrays.asList("bb", "cc", "aa");
		lst.sort((e1, e2) -> {
			int result = -e1.compareTo(e2);
			return result;
		});
		lst.forEach(e -> System.out.println(e));


		new Thread(() -> System.out.println("aaaa")).start();
		//遍历数组
		String[] books = {"java", "c", "c++"};
		Arrays.asList(books).forEach(book -> System.out.println(book));
	}

	/**
	 * 对集合批量操作
	 */
	public void streamTest() {
		//不定参数
		List<Integer> is = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

		//Stream不会改变原数据，data只是流进来再流出去
		Stream<Integer> isStream = is.stream();

		/**大于3的个数 x 操作数 只保留x>3的值 filter是条件
		 * 函数式编程(把一个函数当成一个参数传给另一个函数)
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

	/**
	 * 流 打开 关闭
	 * @throws ScriptException
	 */
	public void streamTest2() throws ScriptException {
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
