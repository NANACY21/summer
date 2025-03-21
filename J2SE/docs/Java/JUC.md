### JUC概述
`JUC`(java.util.concurrent) 用于处理多线程编程中的并发问题

### 进程&线程
**进程&线程的区别**  
1. `进程`:`进程`是`操作系统`进行`资源分配`的基本单位，指在系统中正在运行的一个应用程序，程序一旦运行就是进程  
2. `线程`:`线程`是`操作系统`进行`调度`的基本单位，系统分配处理器时间资源的基本单元，或者说进程之内独立执行的一个单元执行流。线程是程序执行的最小单位。 线程是任务调度和执行的基本单位

**进程&线程在开销方面的区别**  
1. `进程`:每个`进程`都有独立的代码和数据空间(程序上下文)，程序之间的切换会有较大的开销  
2. `线程`:线程可以看做轻量级的进程，同一类线程共享代码和数据空间，每个线程都有自己独立的运行栈和程序计数器(PC)，线程之间切换的开销小

**进程&线程所处环境的区别**  
1. `进程`:在操作系统中能同时运行多个进程  
2. `线程`:而在同一个进程中有多个线程同时执行(通过CPU调度，在每个时间片中只有一个线程执行)  

**进程&线程内存分配方面的区别**
1. `进程`:系统在运行的时候会为每个进程分配不同的内存空间  
2. `线程`:而对线程而言，除了CPU外，系统不会为线程分配内存(线程所使用的资源来自其所属进程的资源)，线程组之间只能共享资源

**进程&线程的包含关系**
1. 没有线程的进程可以看做是单线程的  
2. 如果一个进程内有多个线程，则执行过程不是一条线的，而是多条线(线程)共同完成的  
3. 线程是进程的一部分，所以线程也被称为轻权进程或者轻量级进程  
4. 线程有自己的堆栈和局部变量，但线程之间没有单独的地址空间，一个线程包含以下内容：
    1. 一个指向当前被执行指令的指令指针
    2. 一个栈
    3. 一个寄存器值的集合，定义了一部分描述正在执行线程的处理器状态的值
    4. 一个私有的数据区

**进程&线程各自有什么区别和优劣**
1. `进程`是`操作系统`进行`资源分配`的基本单位，`线程`是程序执行的最小单位  
2. `地址空间方面`：进程有自己的独立地址空间，每启动一个进程，系统就会为它分配地址空间  
    建立数据表来维护代码段、堆栈段和数据段，这种操作非常昂贵  
    而线程是共享进程中的数据的，使用相同的地址空间，因此CPU切换一个线程的花费远比进程要小很多  
    同时创建一个线程的开销也比进程要小很多
3. `通信方面`：线程之间的通信更方便，同一进程下的线程共享全局变量、静态变量等数据  
    而进程之间的通信需要以通信的方式(IPC)进行  
    不过如何处理好同步与互斥是编写多线程程序的难点  
4. `程序健壮性方面`：但是多进程程序更健壮，多线程程序只要有一个线程死掉，整个进程也死掉了  
    而一个进程死掉并不会对另外一个进程造成影响，因为进程有自己独立的地址空间


### 线程
**有关线程的几点说明**  
1. 线程的run()由cpu来调度：`操作系统`的`线程调度器`会在合适的时机为该线程分配 CPU 时间片，当该线程获得 CPU 时间片后，就会开始执行run()  
2. 线程通过设置优先级来控制cpu调度，优先级大的线程，cpu优先调用该线程，但不保证会按优先级来执行
3. 线程若是start启动，代码书写的顺序和代码执行的顺序有可能不一样  
    main()里有线程A start，main线程和A线程执行顺序不确定  
    线程若是调用run，代码就是线性执行


**线程6种状态(见Thread源码内部类State)**
1. NEW(新建)
2. RUNNABLE(准备就绪) 注意：start后等待cpu来调度时的就绪态和运行时都是RUNNABLE!!!
3. BLOCKED(阻塞)
4. WAITING(不见不散)
5. TIMED_WAITING(过时不候)
6. TERMINATED(终结)

**线程状态-`等待` 和 `阻塞`的区别**


**线程状态转换**   
1. new thread 线程处于新建状态  
2. 调start()线程状态由新建->就绪，调start()后线程不是马上执行，此时该线程要等待cpu来调度，因此start()调用顺序也不是线程执行顺序

**用户线程/守护线程**
1. `用户线程`：主线程结束了，用户线程如果还在运行，则jvm存活
2. `守护线程`：例如做垃圾回收操作 如果jvm进程里没有用户线程了都是守护线程，则jvm结束


**关于`锁`的简单介绍**  
1. 多个线程执行同一个方法时为了保证该方法同一时刻只能被一个线程执行 这些线程执行方法时尝试获取的锁必须是同一个
    多个线程 要看是不是同一把锁，多个线程使用同一把锁才能控制这多个线程的并发执行


**wait()/sleep()区别**  
1. 都是暂停当前线程(当前线程不占用cpu了)
2. sleep()是 Thread 的静态方法，wait() 是 Object的方法
3. sleep() 不会释放锁，它也不需要占用锁。就是单纯的暂停线程执行(不占用cpu)
   wait() 会释放锁，但调用它的前提是当前线程占有锁(即代码要在 synchronized 中)
   它们都可以被 interrupted 方法中断


**线程间通信**  
1. 多线程之间，`锁`就是`一个对象`，比如是对象`lock`
2. lock.wait() 可以暂停自己，让其他线程继续执行，调用wait()必须是已经占有锁了
3. wait()/notify()如果不是来自同一个对象 会有问题 通信不成功 (此方法只应由作为此对象监视器的所有者的线程来调用)  
4. wait() :对于某一个参数的版本，实现中断和虛假唤醒是可能的，而且此方法应始终在循环中使用!!!因为wait()在哪里睡就在哪里醒 醒了之后应该再判断所以应while
5. notify()/notifyAll():前者随机唤醒一个线程。后者唤醒所有线程，所有被唤醒的线程会竞争该对象的锁，只有一个线程能获取到锁并继续执行，其他线程会继续阻塞，直到它们也能获取到锁

**多线程编程通用步骤**
1. 创建资源类，在资源类创建属性和操作方法
2. 在资源类操作方法(同步方法) 判断 干活 通知 防止虚假唤醒
3. 创键多个线程，调用资源类的操作方法

**线程间定制化通信**
使用Lock 多个Condition 可以理解为多个线程特有的标识，可以用于精确唤醒/暂停指定线程  
案例：让线程按顺序调用 a唤醒b b唤醒c c唤醒a  
注意：锁 和暂停/唤醒 是完全两个东西  

**集合的线程安全**  
1. 集合类线程不安全的场景 有并发写 并发读 读写问题  
2. 线程安全的vector 使用同步关键字 是悲观锁 性能可能不好  
3. CopyOnWriteArrayList线程安全 其底层原理：写时复制技术(类似git创建多个分支 同时修改 然后合并 乐观锁)  
4. hashset/hashmap线程不安全 解决方案：CopyOnWriteArraySet ConcurrentHashMap  

**多线程锁-juc中各种锁介绍**  
1. `公平锁` 效率相对低 多个线程按申请锁的顺序来获取锁，从而执行，类似先来后到
2. `非公平锁` 效率高 指多个线程不是按照申请锁的顺序获取锁，非公平锁线程上来就占有锁，失败再公平锁
    非公平锁会造成：只有一个线程工作，其他线程空闲的情况(线程饥饿)
3. `可重入锁`(递归锁)
    已经获取锁的线程可以再获取到这个锁 注意针对手动上/释放锁：上锁次数=释放锁次数!!!否则会影响其他线程获取锁  
    线程获取可重入锁之后，可以在该线程里面的代码再获取到该锁，否则，如果是非可重入锁，再获取时就死锁了  
    同步关键字是隐式可重入锁 Lock接口是可重入锁
4. `死锁` 多个线程因为对方而阻塞 都执行不了
    验证`jvm进程`是否存在死锁：`jps`:查看当前运行的进程的进程号 `jstack 进程号`:jvm自带查看堆栈信息工具
5. `乐观锁`
6. `悲观锁`
7. `独占锁`(写锁) 独占锁只能被一个线程持有 同步关键字、可重入锁都是独占锁
    mysql两个事务同时读一条数据 再同时修改这条数据 产生死锁!!!
    mysql两个事务 a事务 update id=1 update id=2 b事务 update id=2 update id=1 ab事务同时执行 产生死锁!!!
8. `共享锁`(读锁) 共享锁可以被多个线程持有

**案例：多个线程对hashmap进行同时读写**  
没写完的数据就开始读取 应该读取不到才对  
使用ReentrantReadWriteLock 在读/写时分别加读/写锁!!! 比悲观锁效率高

**ReentrantReadWriteLock读写锁解析**  
一个资源可以被多个读线程访问，或者可以被一个写线程访问，但是不能同时存在读写线程，读写互斥，读读共享  
无锁时读写混乱，使用悲观锁读读也只有一个线程性能低下，于是产生读写锁：读读 可以共享，提升性能 写写只能同时一个线程  
读写锁缺点：
1. 造成锁饥饿，一直读，没有写操作
2. 读时候，不能写，只有读完成之后，才可以写，写的时候可以进行读(这是锁降级导致的)

`锁降级`：将写入锁降级为读锁 就是写操作肯定要同时获取读和写锁，降级就是把写锁释放了，只留读锁：修改操作->获取写锁->获取读锁->释放写锁->  
释放读锁，这就是锁降级!!!降低权限!!!读锁 不能升级为 写锁 读的时候是不能写的!!! 为了提高数据可见性  
意思是保证写完后可以立刻被读，不耽误读，因为写完后可能被其他写锁上锁导致无法读，所以先让读锁上锁防止写锁其他写锁上锁而不能读  
其实锁降级就是为了能读不耽误读  





**Callable接口**
**创建线程方式**  
1. 继承thread类
2. 实现runnable接口
3. 实现callable接口
4. 线程池

callable接口创建线程在线程结束时可以返回结果 callable接口的线程会抛出异常 callable是call()  
  
FutureTask原理：在线程A中用未来任务开启一个线程 未来任务结束后 线程A能获取到未来任务结束信息  
主线程开启4个未来任务 之后主线程汇总未来任务结果!!!汇总一次  
未来任务get()获取执行返回值 如果还没执行完获取不到!!!主线程会等未来任务都结束后才结束  

**辅助类(相当于封装了一下)**  
1. `减少计数CountDownLatch` 线程间通信 案例：6个同学陆续离开教室后值班同学才可以关门
2. `循环栅栏CyclicBarrier` 案例：集齐7颗龙珠可以召唤神龙
3. `信号灯Semaphore` 用于控制并发线程数量 只能有3个线程同时执行 就类似线程池!!!案例：6辆汽车，停3个车位

**阻塞队列**
线程通过操作阻塞队列实现暂停/自动唤醒!!! 应用于线程池  
共享队列 多个线程操作 阻塞队列对象在堆中 线程共享  
当队列是空的，从队列中获取元素的操作(一个线程干的事)将会被阻塞  
当队列是满的，从队列中添加元素的操作(一个线程干的事)将会被阻塞 阻塞的线程可以的时候会自动唤醒  
使用阻塞队列好处：不需要关心什么时候需要阻塞线程，什么时候需要唤醒线程，因为这一切BlockingQueue都给你一手包办了  

**BlockingQueue<E>是接口 其实现类**
1. `ArrayBlockingQueue(常用)` 由数组结构组成的有界阻塞队列
2. `LinkedBlockingQueue` 由链表结构组成的有界阻塞队列
3. `DelayQueue` 中的元素只有当其指定的延迟时间到了，才能够从队列中获取到该元素  
    DelayQueue 是一个没有大小限制的队列，因此往队列中插入数据的操作(生产者)永远不会被阻塞  
    而只有获取数据的操作(消费者)才会被阻基  
    使用优先级队列实现的延迟无界阻塞队列  
4. `PriorityBlockingQueue` 内部控制线程同步的锁采用的是公平锁 支持优先级排序的无界阻塞队列
5. `SynchronousQueue` 不存储元素的阻塞队列，也即单个元素的队列
6. `LinkedTransferQueue` 由链表组成的无界阻塞队列
7. `LinkedBlockingDeque` 由链表组成的双向阻塞队列

**阻塞队列中的方法**
1. `add()` 失败抛错
2. `remove()` 失败抛错
3. `offer()` 添加 失败返回false 设置超时时间 会阻塞一段时间 超时后仍阻塞 阻塞的线程会结束退出
4. `poll()` 取数据 失败返回null 设置超时时间 会阻塞一段时间 超时后仍阻塞 阻塞的线程会结束退出
5. `put()` 添加 失败 阻塞当前线程!!!
6. `take()` 取数据 失败 阻塞当前线程

**线程池**
创建线程方式之一，线程池维护着多个线程  
等待着监督管理者分配可并发执行的任务。这避免了在处理短时间任务时创建与销毁线程的代价(创建线程对象 分配堆内存)  
线程池不仅能够保证内核的充分利用，还能防止过分调度  

**线程池优势**
线程池做的工作只要是控制运行的线程数量，处理过程中将任务放入队列，然后在线程创建后启动这些任务  
如果线程数量超过了最大数量，超出数量的线程排队等候，等其他线程执行完毕，再从队列中取出任务来执行  
1. 降低资源消耗
2. 提高响应速度
3. 线程的统一管理  

**线程池架构**  
Java 中的线程池是通过Executor 框架实现的，该框架中用到了Executor->ExecutorService->ThreadPoolExecutor  
Executors(工具类) 这几个类  
线程池处理任务的场景类似：10个人去银行共5个窗口办理业务  

**线程池分类**
1. Executors.newFixedThreadPool(int) 一池N线程 固定n线程  
    可以很好控制并发量 超出一定量的线程被提交时候需在队列中等待  
    方法里是：new ThreadPoolExecutor()  
2. Executors.newSingleThreadExecutor() 一个任务一个任务执行，一池一线程 方法里也是： new ThreadPoolExecutor()
3. Executors.newCachedThreadPool() 可以用于处理大量短期突发流量 线程池根据需求创建线程，可扩容，遇强则强  
    方法里也是： new ThreadPoolExecutor()  
    根据需求不断变化可同时执行线程数量 比如20个请求用该线程池 最多可能创建13个线程!!! 10个请求 最多可能创建5个线程

**ThreadPoolExecutor类构造方法7个参数**

**线程池工作流程**
线程池对象执行了execute()时线程才创建  
先占常驻线程，常驻线程满了再来线程依次放到阻塞队列中，阻塞队列满了再来线程新创建线程(非常驻线程)，最大线程数也满了，再来线程执行拒绝策略

实际开发自定义线程池，线程池不允许使用 Executors 去创建，因为阻塞队列太大可能堆溢出，而是通过 ThreadPoolExecutor 的方式

**线程池工作线程出现异常怎么办**  
线程池工作线程出现异常会往外抛 并且销毁这个线程  
1. 线程加try catch 在内部捕获异常
2. submit返回值 try {submit} catch{实际处理异常}

**线程池-线程复用**
**线程池-线程存活时间**  
非核心线程在没有任务可执行时的最大存活时间



**分支合并框架(分治的思想)**  
`Fork/join` 它可以将一个大的任务拆分成多个子任务进行并行处理，最后将子任务结果合并成最后的计算结果，并进行输出  
`Fork/Join框架` 要完成两件事情：  
1. fork:把一个复杂任务进行分拆，大事化小
2. Join：把分拆任务的结果进行合并

分支合并框架涉及的类：`ForkJoinPool`，`Future接口`，`ForkJoinTask`，`RecursiveTask`  
一个分支子任务就是一个线程

**CompletableFuture异步回调**
`同步`：线程a一直阻塞 等待线程b  
`异步`：线程a继续执行，线程b到时候通知线程a  
CompletableFuture用于实现异步回调 实现了Future接口 (未来任务)  
主线程中创建有/无返回值异步任务，主线程可以在异步任务结束时获取异步任务的结束返回值以及异常信息

**怎么实现无锁数据结构(为了性能更好)**
不用锁的情况下实现线程安全 可以采用原子操作+cas机制  
原子操作类似事务 juc.atomic cas 乐观锁

**何为线程安全**
多个线程访问某个方法或对象时，无论任何方式的调用 、线程如何交替执行、不做任何同步干预的情况下，实际执行结果都能按预期程序编写的结果来反馈，那么就说是线程安全的

**JMM(Java内存模型)**  

**相关的计算机术语：**  
1. CPU
2. 主存(运行内存，也叫主物理内存)
3. 外存(磁盘)

**相关的理论知识：**  
1. JMM 是一种抽象的规范，侧重于定义`线程之间`的`内存交互规则`。而 JVM 运行内存是具体的物理内存划分，侧重于描述 JVM 在运行时如何管理内存资源。
2. `CPU`运算数据是把`主存的数据`复制到cpu和主存之间的`高速缓存`里来进行计算  
3. JVM规范中试图定义一种`Java内存模型`，来屏蔽掉不同硬件和操作系统下的内存访问差异。以实现让Java程序在各种平台下都能达到一致的内存访问效果  
   一段Java程序可以不改动源码在任何OS上运行 
4. JMM本身是一种抽象的概念并不真实存在它仅仅描述的是一组约定或规范    
   就比如多线程知识涉及cpu和主存交互的知识，那么是哪个操作系统对应的cpu，不需要关心，JMM它屏蔽了硬件和OS的差异  

**JMM三大特性(要求多线程程序满足这三点 线程安全问题的具体表现的3个方面)**
1. `数据可见性`:一个线程修改一个共享变量后的值对其他线程应该是实时可见的，导致数据可见性问题原因比如 cpu高速缓存 cpu指令重排序 编译器指令重排序
   因为共享变量就是线程公有的，经某线程修改后就应该是对其他线程实时可见的。就是因为主内存-工作内存这种机制导致数据可能不是实时可见的。  
   `保证可见性方式`:可以使用 volatile 关键字、synchronized 关键字或者 Lock 接口来保证共享变量的可见性
2. `原子性`:要求线程之间隔离性 互不干扰 主要表现为一个线程的写操作不被其他线程干扰导致写错误。
   `保证原子性方式`:synchronize关键字或原子类
3. `有序性`:有序性是指程序执行的顺序按照代码的先后顺序执行。
   但在实际执行过程中，为了提高性能，编译器和处理器可能会对指令进行重排序。重排序分为编译器重排序和处理器重排序，
   虽然重排序可以提高性能，但在多线程环境下可能会导致程序出现错误。
   指令重排单线程永远没事 多线程可能有事
   因为指令重排序，程序编写的指令顺序和cpu最终运行的指令顺序可能不一致
   `保证有序性方式`:volatile 关键字、synchronized 关键字或者 Lock 接口来保证程序的有序性

因为编译器、处理器工作机制如此，造成了多线程环境下可能的`数据可见性问题`、`原子性问题`、`有序性问题`，要避免这三个问题。

**指令重排序**  
一条Java源码可能是有多条cpu指令，编译器处理器会进行多条cpu指令的指令重排序，在多线程时可能因此出现问题  
`指令重排`可以保证`单线程时`程序结果和代码顺序执行结果一致 不保证多线程 所以多线程时有时要禁止指令重排,指令重排要在数据依赖性的前提下

**happens-before原则**  
`先行发生原则`强调的是一种在多线程环境下，对操作之间可见性和顺序性的规则约束，以保证程序的正确性和稳定性。要遵循和保证该原则。
在多线程环境下，管程锁定规则、volatile 变量规则等则是为了在不同线程之间建立起特定的顺序和可见性约束。

多线程先行发生原则 先行发生原则就是对指令重排序的一个约束 不允许肆无忌惮的指令重排序  
a线程执行x=5 b线程执行y=x  如果线程a赋值没完成就挂起了 b线程执行会得到非预期结果 所以必须等a线程某操作完事后  
b线程才开始操作 即要保证线程a操作 先行发生 线程b操作 先行发生原则保证了多线程操作的正确性  
happens-before原则约束了可见性、有序性  
先行发生原则是自动的规则，不需要程序员额外操心。程序员没有处处使用同步关键字和volatile关键字  

先行发生原则：如果操作a 先行发生 操作b 那么操作a结果对操作b可见 而且操作a执行顺序排在操作b之前  
如果重排序之后仍按照先行发生原则 执行结果一致没影响 那么允许重排序  
指令重排有时会破坏先行发生原则  

先行发生原则说白了就是本来多线程并发执行没有先后顺序 但是指令纬度是有先后顺序的 一个线程的一组指令有原子性  
比如unlock()是一组指令组成的 如果没有先行发生原则 unlock()操作的原子性被其他线程干扰导致问题 所以  
必须等原子性指令全完事再操作(这得益于先行发生原则)    纠正：先行发生原则说的不是原子性 说的是指令执行顺序!!! 有些指令要先于其他指令执行  
volatile的写回主存实时通知其他线程 也是得益于先行发生原则 也就是说多线程同时执行代码要依赖于先行发生原则  
在说白了就是原子性操作时不能切换线程执行!!! 线程a 执行一组指令没结束 这时cpu时间片切换到另一个线程了 不可以  
JMM-原子性和事务类似但也有不同，JMM-原子操作侧重于操作中不能插入其他线程干扰，而数据库事务指全部成功或失败