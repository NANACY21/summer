package collection;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HashMap
 * ConcurrentHashMap
 * Hashtable
 * TreeMap
 */
public class Demo3 {
    public static void main(String[] args) {
    }

    /**
     * ConcurrentHashMap
     */
    public void test1() {
        ConcurrentHashMap chm = new ConcurrentHashMap();//最优解
    }

    /**
     * HashMap:
     * Java7底层:数组+单向链表
     * Java8底层:数组+单向链表+红黑树
     * 非线程安全 效率优于Hashtable
     * 适用于增删、定位元素
     * hashMap key不重复且无序!!!
     * HashMap中的所有的key彼此之间是不可重复的、无序的。所有的key就构成一个Set集合。--->key所在的类要重写hashCode（）和equals（）
     * HashMap中的所有的valve彼此之间是可重复的、无序的。所有的value就构成一个Collection集合。--->valve所在的类要重写equals（〕
     * HashMap中的一个key-valve，就构成了一个Entry。
     * HashMap中的所有的entry彼此之间是不可重复的、无序的。所有的entry就构成了一个Set集合。
     * 思考:什么情况下重写equals、hashcode!!!
     * 什么时候必须重写hashcode?hash结构且不可重复!!!
     * 利用hashCode()进行快速查询 输入/输出顺序不一致
     *
     * jdk8与jdk7的不同之处:
     * 1 在jdk8中，当我们创建了HashMap实例以后，底层并没有初始化table数组。
     * 当首次添加（key,value）时，进行判断，如果发现table尚未初始化，则对数组进行初始化。
     * 2 在jdk8中，HashMap底层定义了Node内部类，替换jdk7中的Entry内部类。意味着，我们创建的数组是Node[]
     * 3 在jdk8中，如果当前的（key,value）经过一系列判断之后，可以添加到当前的数组角标i中。如果此时角标i位置上有
     * 元素。Jdk7中是将新的（key,Value）指向已有的旧的元素。両在jdk8中是旧的元素指向新的（key,value）元素
     * 4 底层数据结构不完全相同
     * 什么时候会单向链表变成红黑树：如果数组索引i位置上的元素的个数达到8，并且数组的长度达到64时，我们就将此索引i位置上
     * 的多个元素改为使用红黑树的结构进行存储(方便查找)(为什么修改呢？红黑树进行put()/get()/remove()
     * 操作的时间复杂度为O(logn)，比单向链表的时间复杂度O(n)的好。性能更高。)
     * 什么时候会红黑树变成单向链表：当使用红黑树的索引i位置上的元素的个数低于6的时候，就会将红黑树结构退化为单向链表
     *
     */
    public void test2() {
        /**创建对象的过程中，底层会初始化数组Entry[] table = new Entry[16]
         * 默认初始长度16(这个调用size是测不出的，size是实际元素个数，不是容量)
         * 加载因子0.75:元素个数超过容量长度的0.75倍时扩容 扩容成原来容量的一倍
         */
        HashMap<String, Object> hm = new HashMap<>();
        String message = "message";
//        System.out.println("HashMap初始长度：" + hm.size());
        /**
         * Java7 put()解析:(源码就是这样做的)
         * "AA"和78封装到一个Entry对象中，考虑将此对象添加到table数组中
         * 先调 key所在类hashCode()方法，计算key哈希值1，此哈希值1经过某种算法(hash(哈希值1) 为了避免哈希冲突链表越来越长)之后，得到哈希值2
         * 哈希值2再经过某种算法(indexFor())之后，就确定了其在数组table中的索引位置i
         * 1.1 如果此索引位置i的数组上没有元素，则(key,Value)添加成功(情况1)
         * 1.2 如果此索引位置i的数组上有元素(key2,value2)，则需要继续比较key和key2的哈希值2 -->哈希冲突
         * 2.1 如果key的哈希值2与key2的哈希值2不相同，则(key1,value1)添加成功。(情況2)
         * 2.2 如果key的哈希值2与key2的哈希值2相同，则需要继续比较key和key2的equals()。要调用key所在类的equals()，将key2作为参数传递进去。
         * 3.1 调用equals()，返回false：则（key,valve1）添加成功。(情況3)
         * 3.2 调用equals()，返回true：则认为key和key2是相同的。默认情况下，value1替换原有的value2。
         * 说明：情况1：将（key,value1）存放到数组的索引i的位置
         * 情况2，情况3：（key,value1）元素与现有的（key2,valve2）构成单向链表结构，（key,Valve1）指向（key2,value2）
         *
         * 随着不断的添加元素，在满足如下的条件的情况下，会考虑扩容：
         * (size >= threshold) && (null != table[i])
         * 当元素的个数达到临界值（->数组的长度*加载因子）时，就考虑扩容
         * 默认扩容为原来的2倍
         *
         * 计算并返回的hashCode是用于找到Map数组的bucket位置来储存Node 对象。
         * 这里关键点在于指出，HashMap是在bucket中储存键对象和值对象，作为Map.Node 。
         */
        hm.put("AA", 78);
        hm.put("age", 21);
        hm.put("birthday", new Date());
        hm.put("name", "gyx");
        hm.put("mess1", message);
        hm.put("mess1", "666");
        hm.put("mess2", message);
        hm.put(null, "p");//允许null键

        System.out.println("\"hm\"信息（hm是1个HashMap对象）:");
        System.out.println("hm中键 \"name\" 的值:" + hm.get("name"));
        System.out.println("hm长度:" + hm.size());
        System.out.println("hm中包含键\"name\"吗？" + hm.containsKey("name"));
        Object obj = hm.remove("name");//移除键"name"，返回键的值
        System.out.println(obj);
        System.out.println("hm中包含键\"name\"吗？" + hm.containsKey("name"));
        System.out.println("hm中包含值\"gyx\"吗？" + hm.containsValue(21));

//        HashMap遍历
        System.out.println("Map集合遍历方式1:");
        Set<String> hm_allKey = hm.keySet();//得到hm的所有键
        System.out.println("hm的所有键:");
        for (Object key : hm_allKey) {//遍历
            System.out.println("键:" + key + "\t\t" + "值:" + hm.get(key));
        }

        System.out.println("Map集合遍历方式2 - 迭代器:");
        //entry 条目
        System.out.println(hm.entrySet().toString() + "886");
        Iterator<Entry<String, Object>> iterator = hm.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, Object> entry = iterator.next();
            System.out.println("键:" + entry.getKey() + "\t\t" + "值:" + entry.getValue());
        }

        Collection<Object> allValue = hm.values();//hm所有的值
        for (Object o : allValue) {
            System.out.print(o + "\t\t");
        }

        System.out.println();

        System.out.println("Map集合遍历方式3:");
        Set<Entry<String, Object>> allEntry = hm.entrySet();//得到hm的所有条目
        for (Object o : allEntry) {
            Entry entry = (Entry) o;
            System.out.print("键:" + entry.getKey() + "\t\t" + "值:" + entry.getValue());
        }

        System.out.println();

        System.out.println("Map集合遍历方式4:");
        hm.forEach((k, v) -> System.out.println("键:" + k + "\t\t值:" + v));
        hm.put(null, null);
        hm.put(null, "aa");
        hm.put(null, "lixiaolong");
        System.out.println(hm.get(null));
        System.out.println("HashMap实现了Map接口，HashMap示例至此ok");
    }

    /**
     * Hashtable:
     * 线程安全
     * Java8底层:数组+单向链表
     */
    public void test3() {
        /**
         *
         * 遍历无序
         * 默认初始容量11
         * 加载因子0.75 元素个数超过容量的0.75倍时容量扩容为原来一倍+1
         */
        Hashtable ht = new Hashtable();
        Set htAllEntry = ht.entrySet();
        ht.put("777", 1);//值不能空
        ht.put("666", 2);//值不能空
        ht.put(null, 5);//键不能空
        System.out.println("Hashtable遍历法1");
        for (Object e : htAllEntry) {
            Entry entry = (Entry) e;
            System.out.println("键:" + entry.getKey() + "\t\t值:" + entry.getValue());
        }

        ht.put("username", 1);//值不能空
        ht.put("password", 2);//值不能空
        ht.put("id", 3);//值不能空
        ht.put("age", 3);//值不能空

        //键值都不能为空
//        ht.put(null,3);

        htAllEntry = ht.keySet();
        System.out.println("Hashtable遍历法2");
        for (Object hey : htAllEntry) {
            System.out.print("键:" + hey + "\t\t值:" + ht.get(hey));
        }
        System.out.println("Hashtable遍历法3");
        Enumeration es = ht.keys();
        while (es.hasMoreElements()) {
            Object o = es.nextElement();
            System.out.print(ht.get(o));
        }
    }

    /**
     * TreeMap:
     * 底层使用红黑树存储
     * 可以按照添加的key-value中的key元素的指定的属性的大小顺序进行遍历
     */
    public void test4() {
        /**非线程安全
         * 按key排序
         * 没有调优选项，因为该树总处于平衡状态
         * 适用于按自然顺序或自定义顺序遍历键
         */
        TreeMap tm = new TreeMap();
        tm.put("aaa", 1);
        tm.put("ccc", 222);
        tm.put("eee", 3);
        tm.put("bbb", "resg");
        Set htAllEntry = tm.keySet();
        for (Object key : htAllEntry) {
            System.out.print(tm.get(key) + "\t");
        }
    }
}