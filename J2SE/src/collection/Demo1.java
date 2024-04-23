package collection;//集合类!!!

import java.util.*;

/**java.util.Collection接口(存一个一个的数据)
 *     List接口:适用查询 有放入顺序 可重复 动态数组
 *         ArrayList
 *         LinkedList
 *         Vector
 *     Set接口:适用增删 增删不引起元素位置改变 无放入顺序 不可重复
 *         什么是无序性？与添加的元素的位置有关，不像ArrayList一样是依次紧密排列的，
 *         元素hashcode决定了在set中存放的位置!!!
 *         不可重复性:hashcode不同则放入，hashcode相同则再判断是否逻辑相同!!!
 *         HashSet 适用快速查找 HashSet中元素须定义hashCode()!!! 只能放一个null
 *         TreeSet 可排序 基于红黑树 不允许null值
 *         LinkedHashSet 查找也很快 迭代器遍历时以插入顺序显示 HashSet的子类
 *
 * java.util.Map接口(存键值对 映射)
 *     HashMap
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
     * 1 非线程安全 适用查 添加、查找效率高!!!时间复杂度为O(1)
     * 删除和插入操作效率低，时间复杂度为O(n)
     * 2 底层是Object数组!!!
     * new list对象时数组默认初始化长度10(java7)
     * new list对象时数组默认初始化长度0 首次添加元素时指向新数组长度10(java8)
     * 数组扩容原来1.5倍 指向新数组地址
     * 模拟栈
     */
    public void test1() {
        //底层创建长度为10的数组。
        ArrayList<String> aL = new ArrayList<>();
        aL.add("aaa");//数组索引0位置放"aaa"
        //底层创建指定capacity长度的数组。大体确认集合长度时使用此参数 避免扩容和复制数组
        //但是如果一直添加还是会扩容!!!
        aL = new ArrayList<>(20);
    }


    /**
     * LinkedList:
     * 1 非线程安全 适用增删改
     * 2 底层是双向链表
     * 第一次add 将数据封装到Node对象 没有扩容问题
     * 插入、删除效率高，时间复杂度为O(1)
     * 添加、查找效率低!!!时间复杂度O(n)有可能添加操作是O(1)
     * 模拟队列
     * 看LinkedList类源码!!!
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
     * TreeSet:可以按照添加的元素的指定的属性的大小顺序进行遍历。
     * 不需要重写equals和hashcode，因为底层不是哈希了!!!
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

    /**
     * LinkedHashSet:
     * 是HashSet的子类；在现有的数组+单向链表+红黑树结构的基础上，又添加了
     * 一组双向链表，用于记录添加元素的先后顺序。即：我们可以按照添好元素的顺序实现遍历。
     */
    public void test5() {


    }

    /**
     * LinkedHashMap:
     * HashMap的子类，在hashmap底层数据结构基础上增加了双向链表!!!
     * 用于记录添加元素的先后顺序。即：我们可以按照添好元素的顺序实现遍历。
     * 对于频繁的遍历操作，建议使用此类。
     */
    public void test6() {

    }
}
