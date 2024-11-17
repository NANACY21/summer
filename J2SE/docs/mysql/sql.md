### DDL
根据旧表创建新表 `create table new_t like 表名;`  
创建新表，复制旧表结构及数据都弄过去，但约束都没了 `create table new_t select * from 旧表名;`  
between以限制查询数据范围时包括了边界值。not between不包括 `select * from 表名 where year between 2000 and 2010;`  
分页 该表行索引,要查的记录数 `select * from 表名 limit 2,10;`  
在第一列添加字段  
`alter table 表名 add column new_field varchar(11) default null auto_increment primary key first;`  
删除字段  
`alter table 表名 drop column name,drop column lyrics;`  
给表某字段加唯一索引 `create unique index 索引名 on 表名 (字段名1, 字段名2);`  
给某几个字段加联合唯一索引 `alter ignore table 表名 add unique index (字段名1, 字段名2);`  

### DCL：数据库控制语句，如授权，提交 回滚
新建用户  
删除用户 `drop user user_id;`  
授权  
撤销权限  
导入sql文件数据到数据库 `source`  
查看MySQL默认编码格式 `show variables like 'character%';`  
查看MySQL允许的最大连接数 `show variables like '%max_connections%';`  
启动MySQL服务  
关闭MySQL服务  
查询所有支持的存储引擎 `show engines;`  
`show create table 表名;`