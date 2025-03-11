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