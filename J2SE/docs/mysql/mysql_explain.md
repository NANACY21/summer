#### 概述
explain 是 MySQL 提供的SQL 执行计划分析工具
mysql优化器会重写sql，如子查询重写为多表连接，explain看到的是重写后的sql的执行计划  
优化器生成最终执行计划由执行器执行
用法：`explain select语句;`


#### 核心用途
1. 判断 SQL 有没有走索引、走了哪些索引；
2. 定位慢 SQL 性能瓶颈：全表扫描、索引失效、关联查询笛卡尔积；
3. 分析多表联查（join）的驱动表、关联顺序是否合理；
4. 看到是否用到临时表、文件排序（filesort）、分组优化；
5. 优化慢查询、给 SQL 调优提供依据。


#### explain每一列含义
1. `type` 针对单表的访问方法
   性能从好到差排序：system > const > eq_ref > ref > range > index > ALL
   type只要不是all 都读取了非聚簇索引
   const /eq_ref/ref /range -> 真正高效使用索引（推荐）
   type = index -> 用到了索引文件，但没有利用索引加速查询
   1. system：表只有 1 条数据，极少出现
   2. const：主键 / 唯一索引等值匹配，只匹配 1 行（最优）
   3. eq_ref：联表时主键 / 唯一索引匹配，每行只匹配一次
   4. ref：普通索引等值查询，匹配多行
   5. range：索引范围查询（between、>、<、in）
   6. index：扫完整棵索引树，比全表好一点，但仍慢
   7. ALL：完全不走二级索引，只扫聚簇索引（全表扫描），最差，必须优化
2. `possible_key` 可能用到的索引  
3. `key` 实际用的索引  
4. `key_len` 使用索引的长度
   可判断联合索引是否完整命中
   主要针对联合索引 值越大越好 通过key_len可以看出联合索引用了哪个字段的索引
5. `rows` 预估读取的条数 越小越好  
   缓存 内存 磁盘 顺序io  
6. `filtered` 越大越好  
7. `extra`：一些额外的信息 可以看出大量性能问题
   1. Using filesort：出现就代表需要优化，MySQL 无法利用索引排序，额外磁盘排序，耗性能；
      代表order by 字段无索引 -> order by 条件和 where 建立联合索引
      代表是文件排序 无法利用索引排序
   2. Using temporary：创建临时表（常见 group by、distinct），性能差；
      explain发现使用临时表 不好 最好使用索引来替代
   3. Using index：覆盖索引，不需要回表，性能极佳；
   4. Using where：存储引擎筛选后服务层再过滤，正常；
   5. Using join buffer：联表没用到索引，使用连接缓冲，需要加索引。
8. `id` SQL 执行序号，多表 join、子查询时区分执行顺序
   和select关键字对应  
   对于嵌套查询有两个select关键字 有时优化器会优化成多表连接查询因此id值只有一个  
   id越大 优先级越高 越先执行  
   id每个值 表示一趟独立查询  
9. `table` 当前分析的表名。
   涉及几个表 - 生成几条记录  
10. `select_type` 和小查询对应 用于描述小查询在整体查询中扮演的角色  
11. `partitions`
  
  
#### explain的4中输出方式
1. 有索引不用：key:NULL、type:ALL
2. 低效遍历索引：key:索引名、type:index
3. 正常命中索引：type:ref/range/const + key:索引名


#### 场景分析(联合索引(a,b))
1. `select a,b from t order by b;`
   无where，只查索引内字段，走全索引扫描 type=index 依然有 Using filesort
2. `select * from t where a>1 order by b;`
   type = range 但是也会Using filesort(走索引 无法利用索引排序)
3. `select * from t order by b;`
   type = ALL 同时有 Using filesort 要查询所有字段，走二级索引还要大量回表，代价更高，优化器直接放弃索引，全表扫描。
4. `where a=1 order by b;`
   无 Using filesort 能走索引排序
5. `where b=1;`
   只能全扫二级索引树（type=index），这种遍历成本 大于直接全表扫聚簇索引（type=ALL），
   MySQL 优化器会选择更省成本的全表扫描，于是看起来 “索引失效”。
   如果查询字段都是索引字段 则覆盖索引 不回表 走索引
