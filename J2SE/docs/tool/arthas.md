#### arthas概述
1. arthas是一个jvm进程，进入arhhas安装目录后运行对应jar，这样就启动了arthas进程
2. 再启动一个Spring应用，arthas会让选择监控哪一个jvm进程，选择一个即可监控该应用


#### arthas原理
1. 利用Java Agent 技术


#### Java 的 Instrumentation 机制
1. 它是 Java 提供的一个强大的 API 集合，位于 java.lang.instrument 包中。
   其核心思想是允许在类加载到 JVM 之前或之后对其字节码进行修改，从而实现对程序行为的动态改变。
   简单来说，它提供了一种修改字节码的能力和规范


#### Java Agent
1. Java Agent 是基于 Java Instrumentation 机制实现的一种应用程序。
   它本质上是一个特殊的 JAR 文件，通过特定的配置和启动参数，可以在 JVM 启动时或运行时加载并执行。
   Java Agent 利用 Instrumentation 提供的 API 来完成具体的字节码修改任务
2. 使用场景：
    1. arthas
    2. 热部署



#### jvm知识
1. 元空间不断增加 jvm进程占用的服务器物理运行内存就会随之增加
2. JVM 进程整体占用操作系统物理运行内存=Java 堆+本地内存（Native Memory，堆外）+JVM 自身进程开销
   元空间属于本地内存
   jvm管理的内存：
   1. 堆内存（Heap）
   2. 本地内存（堆外内存）
      1. Metaspace 元空间（JDK8+）
         1. 类元数据
         2. 静态成员变量
      2. Direct Buffer 直接缓冲区：ByteBuffer.allocateDirect()，IO 高性能读写用
      3. 线程栈（VM Stack / Native Stack）：每个线程栈内存都是本地内存


#### arthas命令
1. `watch`
   打印方法入参 出参 异常信息
2. `trace`
   方法里每一行耗时
3. `jad`
   源码
4. `thread`
   线程列表 以及cpu利用率
5. `thread -n 3`
   前三个线程 执行栈 cpu利用率
6. `dashboard`
   线程列表 cpu利用率 各区域内存使用情况快照
7. `jvm`
   1. HEAP-MEMORY-USAGE 堆初始大小 堆最大大小
   2. GARBAGE-COLLECTORS 当前使用的垃圾回收器
8. `memory`
   1. metaspace 元空间当前占用 总大小 最大大小


