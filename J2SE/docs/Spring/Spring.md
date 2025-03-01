#### 概述
1. `Spring开源框架`致力于`J2EE各层`的`解决方案`
2. Spring两大特性(原理)：IOC、AOP


#### Java创建对象的方式
1. `new`
2. `从工厂取一个对象(工厂模式的应用)`
3. `反射(按需分配)` spring就是按需分配，当你需要的时候再给你，spring有一个bean容器用于缓存Java对象


#### IOC
1. `IOC(控制反转)`是面向对象编程中的一种设计原则，即`不手动创建对象`，而是`交给Spring来创建对象`以及`管理整个对象的生命周期`  
   控制反转最常见的落地实现方式是`DI(依赖注入)`，这是spring框架的核心
   springIOC特性解决了对象创建的问题、依赖关系维护的问题
   ioc，Spring的这个bean工厂ioc容器主要是利用反射技术按需分配缓存对象，管理对象，描述依赖，依赖注入
   Spring的bean工厂容器的作用就是缓存对象
   依赖关系对象的赋值也交给spring容器来完成
2. `DI(依赖注入)`比如`Autowired`，Java程序读取代码，向ioc容器中获取对象，对象注入到应用程序中，这个过程即依赖注入
3. 定义一个bean，只用一种配置方式以注入容器：`xml配置`或`注解`
   自己写的class一般使用注解，而注入第三方对象写xml的bean


#### AOP
1. `AOP(面向切面编程)`，AOP基于IOC，AOP是对OOP的补充，但和OOP不冲突，这使代码内聚性更高  
   AOP可以使软件各层耦合度降低，AOP有特定应用场景，面向切面就是软件的分层  
   一个功能，方法执行顺序，即层次  
   `面向切面编程：`软件层次从ui到db需要调用很多方法且有调用顺序，db到ui这有很多层，这就是面向切面  
   过滤器就是面向切面编程思想：  
   把非功能性代码(如登录过滤)提取出来，插入在功能代码执行前或后  
   每个页面都执行相同的代码，每个页面都要登录才能访问所以提取出来，这就是AOP
   设想：`IOC是缓存bean`，`AOP是缓存非功能性方法`。都是反射。
2. AOP底层机制：动态代理，就是为目标类(目标功能)加上前/后置代码。即定制了目标类


#### Spring对dao的支持


#### spring事务
1. 给指定Java方法加`@Transactional注解`：利用aop机制使该Java方法内的所有db操作在一个事务里  
   方法执行前开启事务，方法执行成功后提交事务，方法抛出异常则回滚事务  
   可以让开启事务提交回滚事务的代码从业务代码中剥离
2. 事务分类
    1. `编程式事务` 需要写代码实现
    2. `声明式事务` `基于注解` 或 `基于xml`
       注意:在使用`声明式事务`管理时，确保你的类被 Spring 容器管理，即类上有 @Component 或者 @Service 等注解，或者类是通过 @Bean 方法创建的。此外还需要在配置文件中配置事务管理器。
3. 在服务层的方法上使用 `@Transactional 注解`来声明事务，该注解的属性含义：   
    1. `readOnly`：事务是否为只读
    2. `timeout`：事务超时时间
    3. `rollbackFor`：需要进行回滚的异常类型
    4. `noRollbackFor`：不需要进行回滚的异常类型
    5. `propagation`：事务传播行为
    6. `isolation`：事务隔离级别
4. 事务传播行为(propagation,多个声明事务的方法相互调用的时候如何处理)
5. 事务隔离级别(isolation)
    1. `DEFAULT`：使用数据库默认的隔离级别
    2. `READ_UNCOMMITTED` 
    3. `READ_COMMITTED`
    4. `REPEATABLE_READ`
    5. `SERIALIZABLE`
6. spring事务处理，有操作日志，有问题可以重执行日志，这是spring对事务管理的支持


#### spring加载bean有哪些方式
1. 使用注解 @ 会自动扫描并注册到ioc容器
2. xml 配置
3. 配置类 @bean注解
4. importselector接口 BeanDefinitionRegistry接口灵活控制bean加载注册


#### spring bean生命周期(见源码)
1. 定义阶段：Spring 容器启动时读取配置，
   通过加载bean定义方法 用xml、注解扫描方式，找到代码里定义的所有bean类，放到bean定义集合中(将 Bean 的定义信息注册到BeanFactory，此时仅记录元数据，未创建实例)
2. 实例化阶段：容器根据 Bean 定义，遍历bean定义集合，用反射调用构造函数或工厂方法create bean()创建 Bean 实例
3. 属性注入阶段：实例创建后，Spring 依据定义通过构造函数、Setter 方法或字段注入等方式，将依赖的 Bean 或属性值注入到该 Bean
   spring boot配置文件内容是这时候赋值给bean属性的。
4. 初始化实例阶段
    1. 若 Bean 实现BeanNameAware等接口，Spring 会调用相应方法传递 Bean 名称、BeanFactory、ApplicationContext等
    2. 调用BeanPostProcessor的postProcessBeforeInitialization方法进行前置处理
    3. 若 Bean 实现InitializingBean接口，会调用afterPropertiesSet方法
    4. 执行自定义初始化方法（init-method或@PostConstruct注解）
    5. 调用BeanPostProcessor的postProcessAfterInitialization方法进行后置处理
    6. 最后将bean放到单例池
5. 使用阶段：Bean 完成初始化后，可在应用程序中被使用，在容器运行期间一直存在
   bean在ioc容器，获取bean 去单例池寻找 如果有直接返回 没有则创建
6. 销毁阶段：当 Spring 容器关闭，对单例 Bean 进行销毁操作。若 Bean 实现DisposableBean接口，会调用destroy方法；也会执行自定义销毁方法（destroy-method或@PreDestroy注解） 


#### Spring循环依赖(见源码)
1. spring循环依赖前置知识：`spring bean生命周期`、`spring aop`
2. 循环依赖：对象A有属性B，对象B有属性A  
3. 循环依赖会导致：
    1. 导致死循环创建bean
    2. 导致aop代理问题 如何避免
4. 构造器方式注入时如果是循环依赖一定有问题，set方式注入时循环依赖没有问题
5. Spring如何解决循环依赖：
    1. 单例情况下，采用三级缓存机制(难点)解决了循环依赖问题，从而允许循环依赖，多例模式下不允许循环依赖
    2. 一级缓存(单例池)：存放完整bean 可以直接被注入的bean
    3. 二级缓存(半成品池)：存放未依赖注入的bean(属性还没有赋值)
    4. 三级缓存(工厂池)：存放对象工厂
6. `具体流程`：
    1. 创建 BeanA：Spring 容器开始创建 BeanA，先将创建 BeanA 的工厂对象放入三级缓存 singletonFactories 中
    2. 发现依赖 BeanB：在初始化 BeanA 时，发现需要注入 BeanB，此时 Spring 开始正常创建 BeanB，将创建 BeanB 的工厂对象放入三级缓存 singletonFactories 中
    3. 创建 BeanB 时发现依赖 BeanA：在初始化 BeanB 的过程中，发现需要注入 BeanA，此时从三级缓存 singletonFactories 中获取创建 BeanA 的工厂对象，创建一个提前曝光的 BeanA 并放入二级缓存 earlySingletonObjects 中，同时从三级缓存 singletonFactories 中移除该工厂对象
    4. 完成 BeanB 的创建：将提前曝光的 BeanA 注入到 BeanB 中，完成 BeanB 的初始化，并将其放入一级缓存 singletonObjects 中，同时从二级缓存 earlySingletonObjects 中移除
    5. 完成 BeanA 的创建：将完全初始化好的 BeanB 注入到 BeanA 中，完成 BeanA 的初始化，并将其放入一级缓存 singletonObjects 中
7. 几点补充说明：
    1. 需要使用某个bean时比如成员变量注入bean时先找单例池，再找半成品池，再找工厂池，逐个找
    2. 工厂池中有对象a工厂 说明对象a正在创建中
    3. 一级缓存->三级缓存，`对象实例化意愿`降低
8. 附带说明一下Spring创建对象机制：
    1. 向ioc容器获取bean
    2. bean不存在则创建实例 实例化
    3. 填充属性
    4. 初始化


#### Spring MVC
1. `Spring MVC`是spring框架的后续产品，是一个基于MVC的web框架，远超struts2
2. 了解Spring MVC框架处理流程图(策略模式)

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