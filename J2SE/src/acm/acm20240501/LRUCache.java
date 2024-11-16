package acm.acm20240501;

import java.util.LinkedHashMap;

/**
 * 最近最少使用算法:是一种常用的页面置换算法，用于虚拟内存和缓存管理。
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private static final long serialVersionUID = 1L;
    private int cacheSize;

    public LRUCache(int cacheSize) {
        super((int) Math.ceil(cacheSize / 0.75) + 1, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        return size() > cacheSize;
    }

    public static void main(String[] args) {
        LRUCache<Integer, Integer> cache = new LRUCache<>(3); // 创建一个大小为3的LRU缓存
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.get(1); // 访问键为1的元素，使其成为最近访问的元素
        cache.put(4, 4); // 这会移除键为2的元素，因为2最近最少使用
    }
}
