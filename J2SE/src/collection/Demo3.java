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
     * HashMap
     */
    public void test2() {
        /**适用于增删、定位元素
         * 默认初始长度16（这个调用size是测不出的，size是元素个数，不是容量）
         * 加载因子0.75，元素个数超过容量长度的0.75倍时扩容 扩容成原来容量的一倍
         * 非线程安全 效率优于HashTable
         */
        HashMap<String, Object> hm = new HashMap();
        String message = "message";
//        System.out.println("HashMap初始长度：" + hm.size());
        /**
         * put()怎么实现的
         */
        hm.put("name", "李篪");
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
     * Hashtable
     */
    public void test3() {
        /**
         * 线程安全
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
     * TreeMap
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