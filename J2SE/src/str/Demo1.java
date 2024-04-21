package str;

/**
 * 传参问题
 */
public class Demo1 {

    private String str1 = "6";
    private String str2 = new String("good");//这创建2个对象，一个堆一个栈
    private char[] ch = {'a', 'b', 'c'};

    public static void main(String[] args) {
        Demo1 demo1 = new Demo1();
        demo1.change(demo1.str1, demo1.str2, demo1.ch);
        System.out.print(demo1.str1 + "\t" + demo1.str2 + "\t");
        System.out.println(demo1.ch);
    }

    /**
     *
     *
     * @param str1 String类特殊，不是一般对象的引用传递，是值传递
     * @param str2
     * @param ch 数组每个元素按引用传递
     */
    public void change(String str1, String str2, char[] ch) {
        str1 = "666";
        str2 = "10";
        ch[0] = 'g';
    }
}
