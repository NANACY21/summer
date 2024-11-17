### Mybatis概述
1. Mybatis(原名 `iBatis`)是一个ORM(对象关系映射)框架，是一款基于Java的持久层框架  
2. MyBatis避免了几乎所有的JDBC代码和手动设置参数以及获取结果集  
3. MyBatis可以使用简单的XML或注解来配置和映射原生信息  
4. 将接口和Java的POJOs(简单的Java对象、实体类)映射成数据库中的记录  
5. Mybatis专注于SQL语句本身 xml - 实体 - 接口方法 Mybatis不仅适用于mysql  
6. [Mybatis下载地址](https://github.com/mybatis)，也可以用maven在项目中集成mybatis
7. dao -> 持久层(用来数据持久化) -> db，持久层和dao层融合一起来使用
8. ORM框架解决的是面向对象程序设计语言和关系型数据库不匹配的问题，Mybatis能使操作数据库像使用面向对象语言一样。
9. hibernate是纯ORM框架。而Mybatis是半ORM框架，要手写sql的
10. Mybatis要做的事：实体类和sql之间建立映射关系


**mybatis开发dao层**
1. `mybatis逆向工程(反向代理)` 运行main->db单表生成Java code，如pojo、mapper接口、映射文件，修改一下，完了再做上述的事
2. `传统开发方式(不常用)：`mybatis对dao的支持：在daoimpl加成员变量sqlsession工厂
3. `mapper代理开发方式(最常用)：`mybatis对dao的支持，只需实现接口，并保证和映射文件名字一致，会生成代理实现类，使用mapper代理注意：namespace、映射文件insert等的id和接口方法名一致、参数类型=接口方法形参类型、返回值也要一致
4. `动态sql` `mybatis关联查询/分页查询设置` `延迟加载` 重点!


**mybatis查询缓存配置，空间换时间**
1. 一级缓存：第二次查询从缓存里取，对于一次sqlsession会话，还没关时，相同sql语句/结果集，只会命中/查询一次mysql
2. 二级缓存：是namespace级别的，同一个接口的相同查询，会二级缓存，要适当来用。


**相关知识和问题**
1. soap，简单对象访问协议，它是webservice的基础，soa(面向服务) 异构系统之间传输data
2. 思考：如何设计spring IOC框架，必须有一个xml，仿照springbean的xml
3. 用dom4j解析xml
4. [各种数据的映射类型](https://www.cnblogs.com/zhuangfei/p/9492915.html)
5. [逆向工程无效](https://blog.csdn.net/weixin_42215286/article/details/86765076)
6. 运行时找不到对应映射xml文件：映射文件namespace为mapper接口，xml文件要放在resources里
7. 不可以重复执行同一张表的逆向工程，启动SpringBoot项目会报错，xml映射文件内容新增了一遍。
8. resultMap和resultType区别
    1. [二者不能同时写]https://blog.csdn.net/xiao_xiao3601/article/details/92724587。
    2. sql标签字段，不使用resultMap映射结果集时需要起别名来映射结果集。使用resultMap映射结果集并且也用sql标签、sql标签里字段还起别名可能对应不上，这要注意。
9. [Mybatis mapper接口的方法为多个参数](https://blog.csdn.net/qq_34427163/article/details/92783230)
10. if标签里不能有注释。
11. [#和$区别](https://www.cnblogs.com/hellokitty1/p/6007801.html)