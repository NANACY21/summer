### Redis概述
1. `使用缓存的好处` 提升查询效率 减轻数据库的并发压力
2. `redis单线程` 当存在多个请求时 多个请求串行执行! 一个redis进程里只有一个处理请求的线程


### 缓存穿透解决方案
1. `缓存穿透`：无法命中缓存
2. 对于热点数据，不设置缓存过期时间或者延长过期时间，热点数据就是频繁查询的，一旦某一时刻热点数据缓存集体失效，这时mysql会亚历山大
3. 对于热点数据进行多级缓存
4. 分布式锁 相当于设置一个屏障 使得类似串行访问mysql
5. 布隆过滤器 相当于对不存在的数据也使用一个缓存 让无法命中缓存的请求也能命中缓存


### 缓存雪崩解决方案
1. `缓存雪崩`：缓存消失了


### 5种数据类型
1. value有5种数据类型,关注5种数据类型的使用场景
2. `String`
3. `List`:底层使用链表 头插、尾插时间复杂度常数级别 定位元素比较慢
4. `Hash`:类似HashMap
5. `Set`:无序集合，集合元素是唯一的 类似list列表
6. `ZSet`:有序集合，已排序，集合元素是唯一的


### 缓存淘汰策略  
1. Redis缓存淘汰策略主要是通过Redis的内存淘汰机制来实现的。被淘汰的 key 通常会被删除，Redis提供了以下几种淘汰策略：
2. `noeviction`: 不进行淘汰，当内存不足时，新写入命令会报错
3. `allkeys-lru`: 当内存不足以容纳更多数据时，使用最近最少使用算法(LRU)进行淘汰
4. `allkeys-random`: 随机淘汰键
5. `volatile-lru`: 只对设置了过期时间的键进行LRU淘汰
6. `volatile-random`: 随机淘汰设置了过期时间的键
7. `volatile-ttl`: 淘汰即将过期的键，优先淘汰TTL更短的键。如果它的过期时间还未到，在某些情况下 Redis 可能不会立即删除它，而是标记该 key 为待删除状态
   减少不必要的删除，提高性能


### 设置淘汰策略
1. Redis配置文件中设置：`maxmemory-policy allkeys-lru`
2. `CONFIG SET命令`动态设置淘汰策略  
   设置为`allkeys-lru`策略：`redis-cli CONFIG SET maxmemory-policy allkeys-lru`
3. 实际应用中，可以根据应用的需求选择合适的淘汰策略：
    1. 如果应用对数据一致性要求不高，可以选择allkeys-random策略随机淘汰键，以保证数据的均匀分布在缓存中  
    2. 如果希望淘汰最近最少使用的数据，可以选择allkeys-lru策略  
    3. 如果希望优先淘汰即将过期的数据，可以选择volatile-ttl策略


### key的过期清理机制
1. `定期删除` 每隔一段时间执行一次定期删除操作(随机抽取一部分key 检查其中过期的key 删除并释放内存空间),因此不能保证所有过期key都能立即被删除并释放内存空间!!!
2. `惰性删除` 访问某一个key的时候 检查它是否过期 过期则删除并释放内存空间


### 数据类型使用演示
**String类型**  
1. `set name "huanhuanhello"` 新的键值对  
2. `get name` 获取指定键的值  
3. `getrange name 2 5` 获取键name指定索引范围的值  
4. `getset info AAAA` 修改键info的值，返回info旧值 键info若之前不存在，返回空，但已新增了键info并有了值  
5. `setex bb 10 bbbb` 键bb生存10秒  
6. `mset key1 "hello" key2 "world"` 批量设置键值

**list类型**  
1. `lpush hobbys redis` 向hobbys列表头插入值redis  
2. `rpush hobbys java` 向hobbys列表尾部插入值java  
3. `lrange hobbys 0 4` 显示hobbys列表 索引0 - 索引4 范围的值

**set类型**  
1. `sadd strs abc` 向strs列表插入值abc  
2. `sadd strs qwe tyu abc` 向strs列表插入值qwe、tyu、abc  
3. `smembers strs` 显示列表中所有的值  

**zset类型**  
1. `zadd ball 0 fooball` 向ball列表的索引0处插入值football  
2. `zadd ball 20 vollyball` 索引越界则默认插入到最后  
3. `zrange ball 0 100` 显示列表中指定索引范围的值  
4. `zrangebyscore ball 0 10` 显示列表索引0 - 10的数据并显示数据的序号

**hash类型**  
1. `HMSET person:1 name huanhuan age 21 sex male` 哈希表中有多对键值对  
2. `hgetall person:1` 获取指定哈希表的所有键值对  


### 其他Redis命令
1. `keys *` 列出所有的key  
2. `flushall` 清空所有数据


### 主主复制主从复制
1. Redis主从复制，也叫master-slaves配置，可以更好地读写分离


### 集群配置


### 三种集群模式


### 单点故障
1. 一个master节点有问题，其slave机器都完了，因此设置多个master节点。单线程，原子性


### 实现分布式锁
1. 分布式锁的设计 redis分布式锁适用于集群场景，单实例jvm锁即可
2. redis分布式锁续期具体实现 业务时间过长redis分布式锁失效导致并发问题如何延长锁时间
3. 分布式锁redis某实例坏了怎么办


### 不同数据类型的应用场景