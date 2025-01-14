### Spring
`Spring开源框架`致力于`J2EE各层`的`解决方案`

**Java创建对象的方式**  
1. `new`
2. `从工厂取一个对象(工厂模式的应用)`
3. `反射(按需分配)` spring就是按需分配，当你需要的时候再给你，spring有一个bean容器用于缓存Java对象

**Spring两大特性(原理)-IOC**
`IOC(控制反转)`是面向对象编程中的一种设计原则，即`不手动创建对象`，而是`交给Spring来创建对象`以及`管理整个对象的生命周期`  
控制反转最常见的落地实现方式是`DI(依赖注入)`，这是spring框架的核心  
`DI(依赖注入)`比如`Autowired`，Java程序读取代码，向ioc容器中获取对象，对象注入到应用程序中，这个过程即依赖注入

springIOC特性解决了对象创建的问题、依赖关系维护的问题

ioc，Spring的这个bean工厂ioc容器主要是利用反射技术按需分配缓存对象，管理对象，描述依赖，依赖注入  
Spring的bean工厂容器的作用就是缓存对象  
依赖关系对象的赋值交给spring容器来完成  

定义一个bean，只用一种配置方式以注入容器：`xml配置`或`注解`  
自己写的class一般使用注解，而注入第三方对象写xml的bean

**Spring两大特性(原理)-AOP**
`AOP(面向切面编程)`，AOP基于IOC，AOP是对OOP的补充，但和OOP不冲突，这使代码内聚性更高  
AOP可以使软件各层耦合度降低，AOP有特定应用场景，面向切面就是软件的分层  
一个功能，方法执行顺序，即层次  
`面向切面编程：`软件层次从ui到db需要调用很多方法且有调用顺序，db到ui这有很多层，这就是面向切面  
过滤器就是面向切面编程思想：  
把非功能性代码(如登录过滤)提取出来，插入在功能代码执行前或后  
每个页面都执行相同的代码，每个页面都要登录才能访问所以提取出来，这就是AOP

设想：`IOC是缓存bean`，`AOP是缓存非功能性方法`。都是反射。

AOP底层机制：动态代理，就是为目标类(目标功能)加上前/后置代码。即定制了目标类。

**Spring对dao的支持**

**spring事务**
给指定Java方法加`@Transactional注解`：利用aop机制使该Java方法内的所有db操作在一个事务里  
方法执行前开启事务，方法执行成功后提交事务，方法抛出异常则回滚事务  
可以让开启事务提交回滚事务的代码从业务代码中剥离  

**事务分类**
1. `编程式事务` 需要写代码实现
2. `声明式事务` `基于注解` 或 `基于xml`

在服务层的方法上使用 `@Transactional 注解`来声明事务，该注解的属性含义：   
1. `readOnly`：事务是否为只读
2. `timeout`：事务超时时间
3. `rollbackFor`：需要进行回滚的异常类型
4. `noRollbackFor`：不需要进行回滚的异常类型
5. `propagation`：事务传播行为
6. `isolation`：事务隔离级别

**事务传播行为(propagation,多个声明事务的方法相互调用的时候如何处理)**
1. `REQUIRED`：如果当前存在事务，则加入到当前事务中运行，否则创建一个新的事务
2. `REQUIRES_NEW`：创建一个新的事务，如果当前存在事务，则挂起当前事务
3. `NESTED`：如果当前存在事务，则在嵌套事务中运行，否则创建一个新的事务
4. `SUPPORTS`：如果当前存在事务，则在当前事务中运行，否则以非事务方式运行
5. `NOT_SUPPORTED`：以非事务方式运行，如果当前存在事务，则挂起当前事务
6. `MANDATORY`：必须在当前存在事务中运行，否则抛出异常
7. `NEVER`：以非事务方式运行，如果当前存在事务，则抛出异常

**事务隔离级别(isolation)**
1. `DEFAULT`：使用数据库默认的隔离级别
2. `READ_UNCOMMITTED` 
3. `READ_COMMITTED`
4. `REPEATABLE_READ`
5. `SERIALIZABLE`
注意：在使用声明式事务管理时，确保你的类被 Spring 容器管理，即类上有 @Component 或者 @Service 等注解，或者类是通过 @Bean 方法创建的。此外还需要在配置文件中配置事务管理器。

spring事务处理，有操作日志，有问题可以重执行日志，这是spring对事务管理的支持  
spring：事务传播的管理


**类加载时机、对象实例化时机**  
Spring容器启动时会注册bean，此时service类没加载，也没实例化  
当调用该service类时，类加载，并实例化，类加载时，静态成员变量也会加载并创建一个实例对象


**Spring循环依赖**  
spring循环依赖前置知识：spring bean生命周期，spring aop
循环依赖：对象A有属性B，对象B有属性A  
循环依赖会导致：
1. 导致死循环创建bean
2. 导致aop代理问题 如何避免

构造器方式注入时如果是循环依赖一定有问题，set方式注入时循环依赖没有问题  
Spring是如何解决循环依赖的？如何避免死循环创建bean问题：新建一个半成品池(二级缓存) bean产生阶段-反射实例化之后就放到半成品池  
成员变量绑定bean时先找单例池 再找半成品池  
单例情况下，采用三级缓存机制(难点)解决了循环依赖问题，从而允许循环依赖，多例模式下不允许循环依赖      
Spring创建对象机制：  
1. 向ioc容器获取bean
2. bean不存在则创建实例 实例化
3. 填充属性
4. 初始化


**spring bean生命周期**
bean在ioc容器  
bean三阶段：
1. `产生` spring启动就开始生产bean了  
    通过加载bean定义方法 用xml、注解扫描方式，找到代码里定义的所有bean类，放到bean定义集合中  
    遍历bean定义集合 调create bean()以创建bean实例(1 实例化 通过反射创建实例 2 填充属性 属性赋值 依赖注入 3 初始化实例 4 注册销毁(为了方便bean销毁))  
    最后将bean放到单例池  
2. `使用` 获取bean 去单例池寻找!!! 如果有直接返回 没有则创建!!!这很关键!!!  
3. `销毁`


**spring加载bean有哪些方式**  
1. 使用注解 @ 会自动扫描并注册到ioc容器
2. xml 配置
3. 配置类 @bean注解
4. importselector接口 BeanDefinitionRegistry接口灵活控制bean加载注册

### Spring MVC
`Spring MVC`是spring框架的后续产品，是一个基于MVC的web框架，远超struts2

了解Spring MVC框架处理流程图(策略模式)

**Spring MVC相关知识点**  
1. 爆400 -> 客户端错误
2. 请求映射
3. 方法返回值
4. 参数绑定：在springmvc中，data从ui到db，再反之
5. 转换器
6. [数据校验](https://blog.csdn.net/eson_15/article/details/51725470)
7. 数据回显：分为离散的数据、pojo
8. 异常处理：统一抛给一个异常处理器，不用每个都try catch了
9. 拦截器：类似过滤器。可以做操作日志、未登录拦截等
10. 文件上传。
11. json数据交互。
12. RESTful：表述性状态传递，一种软件架构风格，前后端分离
13. 跨域


### Spring boot
springboot内置tomcat，整合了开发、部署、调试  
springcloud更是让程序员不用接触Linux  
项目不上去 打成的jar文件上去 jar文件包含依赖包  
对框架的理解：框架就是让代码只关注业务，其他框架都帮忙完成  

**springboot如此方便依赖2个机制(也是springboot核心组件)**
1. `依赖管理`  
spring-boot-starter家族成员简介  
开发什么场景 就只需要把该场景的maven依赖加到pom文件即可 -starter  
该依赖引用了很多相关依赖 而且不用加版本号 上级定义了  
2. `自动配置(自动装配)` 只需要实现导入相关maven依赖
springboot启动后 自动把很多用到的bean加载到ioc容器内 这些bean对应的类当然也都加载到了方法区  
springboot就是把spring springmvc需要手动做的但是固定的操作都自动帮忙弄了  
ioc容器有什么组件 就等于有该功能  
SpringBoot只会扫描主程序所在的包及其下面的子包  

ioc容器里面是实例对象!!!getbean默认单实例 可以改成多实例  
springboot启动之后 @service注解对应的类的实例自动进入ioc容器 堆空间分配内存 而不是用到service类的时候


**springboot自动装配原理**


**springboot自动装配核心流程**


**Spring Boot启动的工作流程可以概括为以下几个步骤**
1. `启动类`
2. `自动配置`
3. `环境属性`
4. `日志配置`：如果没有提供日志配置，Spring Boot会使用其内置的日志配置。
5. `创建应用上下文`：Spring应用上下文被初始化，并根据配置加载bean。
6. `命令行参数`：在启动时传递的参数会影响Spring Boot的行为。
7. `启动完成`：一旦应用上下文完成启动，应用就准备好接受请求了。

`springboot主函数`:@SpringBootApplication注解启动了自动配置、组件扫描和Spring Boot的特定配置。main方法中的SpringApplication.run则是启动了整个应用


**springboot常用注解**

**@Autowired和@Resource注解区别**  
如何找到要注入的bean，`byname` 和 `bytype` 两种方式  
1. `byname` 根据唯一标识符
2. `bytype` 通过匹配bean的类型来找到要注入的bean