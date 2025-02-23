## 场景问题分析


#### 为什么深分页很慢(比如页面翻到第500页 每页100条数据)
limit 50000,100 mysql会拿到所有的500100条数据 然后找出想查询的数据  
如果有2级索引查询有可能会更慢


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


#### 保证修改语句的幂等性
1. INSERT ... ON DUPLICATE KEY UPDATE 不存在则插入存在则更新
2. UPDATE/delete 语句结合唯一条件
3. 在应用层生成唯一标识，将其作为业务操作的幂等键，在数据库中进行唯一性约束