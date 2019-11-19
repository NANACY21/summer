package base.java8.lambda;
import java.util.Arrays;
import java.util.List;

/**
 * java8新特性
 * 注解
 * runtime
 * 泛型类型推断
 * 反射
 */
public class Demo1 {

	public static void main(String[] args) {
		String split = ",";

		//单条语句可以不用{}
		Arrays.asList("aa", "bb", "cc").forEach((String e) -> {
			//split =  "aaaa";
			e += split;
			System.out.println(e);//多条语句
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
}
