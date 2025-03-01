#### 概述
1. springboot内置tomcat，整合了开发、部署、调试
2. springboot就是把spring springmvc需要手动做的但是固定的操作都自动帮忙弄了(自动配置)，ioc容器有什么组件 就等于有该功能
3. ioc容器里面是实例对象 getbean()默认单实例 可以改成多实例
4. `类加载时机&对象实例化时机`
    1. `对于单例且非懒加载的@Service类`：springboot启动之后 @service类 会完成bean注册、类加载、对象实例自动进入ioc容器 堆空间分配内存 而不是用到service类的时候
    2. `对于非单例作用域或懒加载的@Service类`：springboot启动之后 @service类 会完成bean注册，在首次使用时才会类加载、进行实例化并放入 IOC 容器，在堆空间分配内存
5. `springboot主函数`:@SpringBootApplication注解启动了自动配置、组件扫描和Spring Boot的特定配置。main方法中的SpringApplication.run则是启动了整个应用
6. SpringBoot只会扫描主程序所在的包及其下面的子包
7. springcloud更是让程序员不用接触Linux
8. 项目不上去 打成的jar文件上去 jar文件包含依赖包
9. 对框架的理解：框架就是让代码只关注业务，其他框架都帮忙完成


#### springboot较springmvc的优势
1. 自动装配无需手动配置
2. 快速构建：springboot的依赖管理 各种starter
   这些starter将相关的依赖和配置整合在一起，开发者只需添加相应的启动器依赖，就能快速开始开发特定功能的应用，提升效率
3. 内置服务器
4. 微服务支持 更适用于Springcloud
5. 监控和管理：提供了 Actuator 模块，可方便地对应用进行监控和管理。
   通过 Actuator 可以获取应用的各种运行时信息，如健康状态、性能指标、环境变量等，
   还支持对应用进行动态配置和操作，如刷新配置、查看线程栈等，为应用的运维和管理提供了很大的便利


#### springboot如此方便依赖2个机制(springboot核心组件/特性)
1. `依赖管理`  
   spring-boot-starter家族成员简介  
   开发什么场景 就只需要把该场景的maven依赖加到pom文件即可 -starter  
   该依赖引用了很多相关依赖 而且不用加版本号 上级定义了
2. `自动配置(自动装配)`
   只需导入相关maven依赖，springboot启动后 自动把很多用到的bean加载到ioc容器内 这些bean对应的类当然也都加载到了方法区


#### springboot自动装配原理
1. SPI 机制：SPI 是 Java 提供的一种服务发现机制，它允许第三方为某个接口提供实现。Spring Boot 在启动时会扫描 META - INF/spring.factories 文件，该文件中定义了一系列的自动配置类
2. 条件注解：Spring Boot 使用了大量的条件注解（如 @ConditionalOnClass、@ConditionalOnMissingBean 等）来判断是否需要进行自动配置。这些注解会根据类路径下是否存在特定的类、是否已经存在特定的 Bean 等条件来决定是否加载相应的自动配置类
3. @Configuration 注解：自动配置类使用 @Configuration 注解标记，它们本质上是 Spring 的配置类，会在 Spring 容器启动时被加载并注册相应的 Bean


#### springboot自动装配核心流程
1. 启动 Spring Boot 应用
   Spring Boot 应用的入口通常是一个带有 @SpringBootApplication 注解的类，其中包含一个 main 方法
   @SpringBootApplication 是一个组合注解，它包含了 @SpringBootConfiguration、@EnableAutoConfiguration 和 @ComponentScan 三个注解
2. 加载自动配置类
    1. @EnableAutoConfiguration 注解：该注解会触发自动装配的过程。它使用 @Import 注解导入了 AutoConfigurationImportSelector 类
    2. AutoConfigurationImportSelector 类：该类会在 Spring 容器启动时被调用，它的主要作用是从 META - INF/spring.factories 文件中读取所有的自动配置类
3. 筛选自动配置类
    1. 条件注解判断：AutoConfigurationImportSelector 类会对读取到的自动配置类进行筛选，
       根据自动配置类上的条件注解（如 @ConditionalOnClass、@ConditionalOnMissingBean 等）来判断是否需要加载该自动配置类。
       例如，WebMvcAutoConfiguration 类上可能有 @ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class}) 注解，
       只有当类路径下存在这些类时，才会加载该自动配置类
4. 注册 Bean
    1. 加载自动配置类：经过筛选后，符合条件的自动配置类会被加载到 Spring 容器中。这些自动配置类使用 @Configuration 注解标记，它们会定义一系列的 @Bean 方法
    2. 注册 Bean 到容器：Spring 容器会调用自动配置类中的 @Bean 方法，将返回的对象注册为 Bean。例如，DataSourceAutoConfiguration 类会注册一个 DataSource Bean
5. 应用自动配置
    1. Spring 容器初始化：Spring 容器会完成所有 Bean 的初始化和依赖注入，应用自动配置的结果。此时，开发人员可以直接在代码中使用自动配置的 Bean，而不需要手动进行配置


#### Spring Boot启动工作流程
1. `启动类入口` SpringApplication.run()
2. `创建并配置环境`
    1. 加载属性源：从不同的来源（如配置文件、环境变量、命令行参数等）加载属性配置
       `命令行参数`：在启动时传递的参数会影响Spring Boot的行为
    2. 配置属性源的优先级：确保不同来源的属性按照正确的优先级进行加载和覆盖
    3. 发布应用环境准备就绪事件（ApplicationEnvironmentPreparedEvent）：通知监听器应用环境已经准备好
3. `日志配置`：如果没有提供日志配置，Spring Boot会使用其内置的日志配置。
4. `创建应用上下文`：Spring应用上下文被初始化，并根据配置加载bean
5. `刷新 ApplicationContext`
    1. 扫描并注册 Bean 定义：通过注解（如 @Component、@Service、@Repository 等）或 XML 配置文件，扫描项目中的类，并将符合条件的类注册为 Bean 定义
    2. 创建和初始化 Bean 实例：根据 Bean 定义创建 Bean 实例，并进行依赖注入和初始化操作
       `自动配置`注入的 Bean 会在代码中自定义的 Bean 之前进行处理
    3. 启动嵌入式服务器（如果是 Web 应用）：如 Tomcat、Jetty 或 Undertow 等，使其能够处理 HTTP 请求
6. `启动完成`：一旦应用上下文完成启动，应用就准备好接受请求了


#### springboot常用注解
1. 启动类相关注解 @SpringBootApplication
2. 配置文件相关注解
    1. @Value
    2. @ConfigurationProperties
3. 控制器相关注解
    1. @RestController @Controller 和 @ResponseBody 的组合注解，用于创建 RESTful 风格的控制器，表明该类的所有方法返回的结果都会直接作为 HTTP 响应体返回
    2. @RequestMapping 其派生注解包括 @GetMapping、@PostMapping、@PutMapping、@DeleteMapping 等
4. 服务层注解
    1. @Service
5. 数据访问层注解
    1. @Repository
6. 条件注解
    1. @ConditionalOnProperty 根据配置文件中的属性值来决定是否加载某个 Bean
    2. @ConditionalOnClass 表示只有当类路径下存在指定的类时，才会加载对应的 Bean
    3. @ConditionalOnMissingClass 只有当类路径下不存在指定的类时，才会加载对应的 Bean
7. 依赖注入相关
    1. @Autowired 对构造函数、方法、字段等进行标注
       根据类型在容器中查找匹配的 Bean 并注入。如果有多个匹配的 Bean，还可以结合 @Qualifier 注解指定具体的 Bean 名称
    2. @Qualifier 当存在多个相同类型的 Bean 时，@Autowired 无法确定具体注入哪个 Bean，此时可以使用 @Qualifier 注解指定要注入的 Bean 的名称
8. 切面编程相关
    1. @Aspect 用于定义切面类，在 Spring Boot 中结合 AOP（面向切面编程）可以实现日志记录、事务管理等功能。切面类中可以定义切点（@Pointcut）和通知（如 @Before、@After、@Around 等）
9. 事务管理相关
    1. @Transactional 用于声明事务，可应用在方法或类上。当应用在类上时，表示该类的所有公共方法都具有事务性；应用在方法上时，仅该方法具有事务性。可以指定事务的传播行为、隔离级别、是否只读等属性
10. 测试相关
    1. @SpringBootTest 在 Spring Boot 项目进行集成测试时使用，它会启动整个 Spring 应用上下文，模拟应用的实际运行环境，方便对服务层、控制器层等进行测试

**@Autowired和@Resource注解区别**  
如何找到要注入的bean，`byname` 和 `bytype` 两种方式
1. `byname` 根据唯一标识符
2. `bytype` 通过匹配bean的类型来找到要注入的bean


#### Springboot中servlet是线程安全吗
1. 请求打到servlet实例，servlet为每个请求创建一个线程来处理请求
2. 当多个线程同时访问比如写servlet实例的成员变量时，会产生线程不安全，比如这个成员变量是统计请求次数。
3. Springboot中servlet通常是单例的
4. 解决方案：
    1. 使用局部变量
    2. 同步关键字
    3. 使用线程安全的类(成员变量可以是原子类)