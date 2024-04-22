package collection;//集合类!!!

import java.util.*;

/**java.util.Collection接口(存一个一个的数据)
 *     List接口:适用查询 有放入顺序 可重复 动态数组
 *         ArrayList 适用查
 *         LinkedList 适用增删改
 *         Vector
 *     Set接口:适用增删 增删不引起元素位置改变 无放入顺序 不可重复 set中元素须定义equals()
 *         HashSet 适用快速查找 HashSet中元素须定义hashCode() 只能放一个null
 *         TreeSet 可排序 基于红黑树 不允许null值
 *         LinkedHashSet 查找也很快 迭代器遍历时以插入顺序显示
 *
 * java.util.Map接口(存键值对 映射)
 *     HashMap 利用hashCode()进行快速查询 输入/输出顺序不一致
 *     TreeMap 基于红黑树 得到的结果被排序
 *     LinkedHashMap 输入/输出顺序一致
 *     Hashtable
 *     Properties
 *     WeakHashMap
 *     IdentityHashMap
 */
public class Demo1 {

    public static void main(String[] args) {
        // 针对于具体特点的多个数据，知道选择相应的适合的接口的主要实现类
        // 接口不同实现类区别
        // 实现类底层数据结构
    }


    /**
     * ArrayList:
     * 1 非线程安全
     * 2 数组 默认初始化长度10 查找效率高 模拟栈 扩容原来1.5倍
     */
    public void test1() {

        ArrayList<String> aL = new ArrayList<>();
        //这样指定长度，不扩容
        aL = new ArrayList<>(20);
    }


    /**
     * LinkedList:
     * 1 非线程安全
     * 2 双向链表 第一次 add初始化 插入删除效率高 模拟队列
     */
    public void test2() {

        LinkedList<String> lL = new LinkedList();
        lL.add("z");
        lL.add("d");
        lL.add("y");

        Iterator i = lL.iterator();//迭代器

        //遍历
        for (; i.hasNext(); ) {
            System.out.print(i.next() + "\t");
        }
        System.out.println();
        System.out.println(lL.getLast());//最后一个元素


        //遍历
        for (String s : lL) {
            System.out.print(s + "\t");
        }
    }


    /**
     * TreeSet
     */
    public void test3() {
        TreeSet ts = new TreeSet();
        ts.add(2);
        ts.add(6);
        ts.add(1);
//        ts.add(null);//不允许null值
        Iterator iterator = ts.iterator();
        //TreeSet自动排序
        for (; iterator.hasNext(); ) {
            System.out.print(iterator.next() + "\t");
        }
    }

    /**
     * LinkedHashMap
     */
    public void test4() {
        LinkedHashMap lhm = new LinkedHashMap();
        lhm.put("4", "第一");
        lhm.put("3", "第二");
        lhm.put("10", "第三");
        Set<Map.Entry<String, Object>> set = lhm.entrySet();
        for (Object o : set) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) o;

            System.out.println(entry.getKey() + entry.getValue());
        }
    }
}
