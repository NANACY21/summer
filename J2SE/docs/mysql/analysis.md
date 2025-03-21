#### 保证修改语句的幂等性
1. INSERT ... ON DUPLICATE KEY UPDATE 不存在则插入存在则更新
2. UPDATE/delete 语句结合唯一条件
3. 在应用层生成唯一标识，将其作为业务操作的幂等键，在数据库中进行唯一性约束


#### 为什么深分页很慢(比如页面翻到第500页 每页100条数据)
limit 50000,100 mysql会拿到所有的500100条数据 然后找出想查询的数据  
如果有2级索引查询有可能会更慢
深分页很慢怎么优化：
1. 优化索引
    1. 使用覆盖索引 无需回表
    2. 对于 WHERE 子句中的列，应确保其有索引，这样能加快过滤数据的速度
2. 数据库架构优化
    1. 根据主键范围 水平分表
3. 用缓存


#### 如何定位慢SQL
1. 通过慢查询日志文件


#### 排查慢SQL
1. 判断是否阻塞
    1. `SHOW PROCESSLIST;`状态字段
    状态=Locked，表示该线程正在等待锁，可能存在阻塞情况
    状态=Sending data，并不一定意味着阻塞，可能是 SQL 本身执行较慢
    2. `SHOW ENGINE INNODB STATUS;` 查找事务信息、死锁信息
    若有事务长时间持有锁(别的事务锁表)，也会造成其他事务阻塞
    3. INFORMATION_SCHEMA.INNODB_LOCKS、INFORMATION_SCHEMA.INNODB_LOCK_WAITS 和 INFORMATION_SCHEMA.INNODB_TRX 等表提供了关于 InnoDB 锁和事务的详细信息
2. 没阻塞在执行，单纯执行慢
    1. `explain分析`
       重点关注 type 列（连接类型，如 ALL 表示全表扫描，性能较差）、key 列（使用的索引）和 rows 列（预估扫描的行数），判断是否存在索引未使用、全表扫描等问题
    2. `查询sql各操作耗时`
        1. SHOW PROFILE; SET profiling = 1;启用该功能 SHOW PROFILES; SHOW PROFILE ALL FOR QUERY <上一个返回的query_id>;
        2. 开启慢查询日志 分析慢查询日志 mysqldumpslow -s t -t 10 /var/log/mysql/mysql-slow.log
    3. `索引情况`
        1. 查看表有没有索引 看sql是否可能导致`索引失效` 优化索引
        2. 删除数据会导致索引树结构不完整，所以一般会逻辑删除为了数据分析、不破坏索引
    4. `表结构` 字段类型 存储引擎 表字段过多 表设计是否遵循数据库表设计规范 第几范式
    5. `数据过多` 分库分表
    6. `检查配置信息`
        1. 内存配置：确保 innodb_buffer_pool_size 等内存相关参数设置合理，使 MySQL 有足够的内存来缓存数据和索引，减少磁盘 I/O
        2. 日志配置：开启慢查询日志(slow_query_log)，记录执行时间超过指定阈值(long_query_time)的 SQL 语句，方便后续分析更多慢 SQL
    7. `服务器性能检查`
        1. 系统资源使用情况：使用系统监控工具(如 top、htop、iostat 等)查看服务器的 CPU、内存、磁盘 I/O 等资源使用情况。若资源紧张，会影响 MySQL 性能
        2. 网络状况：对于远程连接的 MySQL，检查网络是否稳定、网络延迟是否过高，这也可能影响 SQL 执行时间
    8. `sql写的怎么样`：sql写的烂，关联查询太多


#### 如何优化SQL查询语句
1. `使用explain分析:`
   写的sql本地执行一遍，explain看是否符合预期，比如建的索引是否用到了，再看在本地的执行时间(排除mysql本身查询缓存的干扰)
2. `肉眼观察:`
   不用工具先通过理论知识肉眼看sql可能有的问题  `覆盖索引` `回表` `最左匹配原则`等情况
3. `索引优化(物理层面优化):`
    1. 合理创建索引：
        1. 选择合适的列创建索引：通常在 WHERE 子句、JOIN 子句和 ORDER BY 子句中经常使用的列上创建索引
        2. 复合索引：当查询条件涉及多个列时，创建复合索引可能更有效。注意复合索引遵循最左前缀原则
        3. 避免冗余索引：冗余索引会增加数据插入、更新和删除的开销，同时占用额外的磁盘空间。可以使用工具（如 pt-duplicate-key-checker）来检测和删除冗余索引
    2. `看情况使用前缀索引`：一个字段值过长建索引会占较大空间，因此考虑使用前缀索引
    3. 针对sql优化中查询优化：进行索引优化 避免索引失效
4. `SQL写法优化(逻辑层面优化):`
    1. 避免使用 SELECT *：只选择需要的列，减少数据传输量和数据库处理的工作量
    2. 优化 WHERE 子句：避免在索引列上使用函数：这会导致索引失效
    3. 使用 AND 连接条件时，将过滤性强的条件放在前面：让数据库尽早过滤掉不必要的数据
    4. 优化 JOIN 操作
        1. 确保 JOIN 列上有索引：可以加快连接操作的速度
        2. 选择合适的连接类型：根据业务需求选择 INNER JOIN、LEFT JOIN 等
        3. 小表驱动大表：MySQL 优化器通常会自动选择，但在某些情况下可以使用 STRAIGHT_JOIN 手动指定驱动表
    5. 子查询优化 排序优化 group by优化 分页查询优化