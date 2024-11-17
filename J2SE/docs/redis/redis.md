### 前言
1. `使用缓存的好处` 提升查询效率 减轻数据库的并发压力!!!
2. `redis单线程` 当存在多个请求时 多个请求串行执行!! 一个redis进程里只有一个处理请求的线程

**缓存穿透(无法命中缓存)解决方案**
1. 对于热点数据，不设置缓存过期时间或者延长过期时间，热点数据就是频繁查询的，一旦某一时刻热点数据缓存集体失效，这时mysql会亚历山大
2. 对于热点数据进行多级缓存
3. 分布式锁 相当于设置一个屏障 使得类似串行访问mysql
4. 布隆过滤器 相当于对不存在的数据也使用一个缓存 让无法命中缓存的请求也能命中缓存!!!!

**缓存雪崩：缓存消失了 解决方案**

**5种数据类型(value有这5种数据类型,关注五种数据类型的使用场景)**
1. String
2. List:底层使用链表 头插、尾插时间复杂度常数级别!!! 定位元素比较慢!!!
3. Hash:类似HashMap
4. Set:无序集合，集合元素是唯一的 类似list列表
5. ZSet:有序集合，已排序，集合元素是唯一的


**Redis缓存淘汰策略**  
Redis缓存淘汰策略主要是通过Redis的内存淘汰机制来实现的。Redis提供了以下几种淘汰策略：
1. noeviction: 不进行淘汰，当内存不足时，新写入命令会报错
2. allkeys-lru: 当内存不足以容纳更多数据时，使用最近最少使用算法(LRU)进行淘汰
3. allkeys-random: 随机淘汰键
4. volatile-lru: 只对设置了过期时间的键进行LRU淘汰
5. volatile-random: 随机淘汰设置了过期时间的键
6. volatile-ttl: 淘汰即将过期的键，优先淘汰TTL更短的键  

在Redis配置文件中设置或者通过CONFIG SET命令动态设置淘汰策略。例如，要设置为allkeys-lru策略，可以使用以下命令：  
`redis-cli CONFIG SET maxmemory-policy allkeys-lru`  
或者在Redis配置文件中添加：  
`maxmemory-policy allkeys-lru`  
在实际应用中，可以根据应用的需求选择合适的淘汰策略  
如果应用对数据一致性要求不高，可以选择allkeys-random策略随机淘汰键，以保证数据的均匀分布在缓存中  
如果希望淘汰最近最少使用的数据，可以选择allkeys-lru策略  
如果希望优先淘汰即将过期的数据，可以选择volatile-ttl策略  

### redis数据类型的使用演示
**String类型**  
`set name "huanhuanhello"` 新的键值对  
`get name` 获取指定键的值  
`getrange name 2 5` 获取键name指定索引范围的值  
`getset info AAAA` 修改键info的值，返回info旧值 键info若之前不存在，返回空，但已新增了键info并有了值  
`setex bb 10 bbbb` 键bb生存10秒  
`mset key1 "hello" key2 "world"` 批量设置键值

**list类型**  
`lpush hobbys redis` 向hobbys列表头插入值redis  
`rpush hobbys java` 向hobbys列表尾部插入值java  
`lrange hobbys 0 4` 显示hobbys列表 索引0 - 索引4 范围的值

**set类型**  
`sadd strs abc` 向strs列表插入值abc  
`sadd strs qwe tyu abc` 向strs列表插入值qwe、tyu、abc  
`smembers strs` 显示列表中所有的值  

**zset类型**  
`zadd ball 0 fooball` 向ball列表的索引0处插入值football  
`zadd ball 20 vollyball` 索引越界则默认插入到最后  
`zrange ball 0 100` 显示列表中指定索引范围的值  
`zrangebyscore ball 0 10` 显示列表索引0 - 10的数据并显示数据的序号!!!

**hash类型**  
`HMSET person:1 name huanhuan age 21 sex male` 哈希表中有多对键值对  
`hgetall person:1` 获取指定哈希表的所有键值对  


**其他redis命令**  
`keys *` 列出所有的key  
`flushall` 清空所有数据  

**key的过期清理机制**
1. 定期删除 每隔一段时间执行一次定期删除操作(随机抽取一部分key 检查其中过期的key 删除并释放内存空间),因此不能保证所有过期key都能立即被删除并释放内存空间!!!
2. 惰性删除 访问某一个key的时候 检查它是否过期 过期则删除并释放内存空间

**redis主主复制主从复制**  
Redis主从复制，也叫master-slaves配置，可以更好地读写分离

**redis集群配置**  
**单点故障**  
一个master节点有问题，其slave机器都完了，因此设置多个master节点。单线程，原子性

**redis实现分布式锁**
1. 分布式锁的设计 redis分布式锁适用于集群场景，单实例jvm锁即可
2. redis分布式锁续期具体实现 业务时间过长redis分布式锁失效导致并发问题如何延长锁时间
3. 分布式锁redis某实例坏了怎么办