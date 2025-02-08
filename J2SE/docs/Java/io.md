### 数据传输
1. 主机之间的数据传输(网络)
2. 计算机内存/外存之间的数据传输


### Java IO

**流**  
`流`是指`数据集合`

`流`按`数据流向`分类：
1. 输入流(InputStream/Reader) 用于外存数据读取到内存
2. 输出流(OutputStream/Writer) 用于内存数据写入外存

`流`按`处理数据的单位`分类：
1. 字节流(InputStream/OutputStream) 以字节为单位处理数据
2. 字符流(Reader/Writer) 以字符为单位处理数据


**字节流**  
基类：InputStream/OutputStream
1. FileInputStream / FileOutputStream：用于文件的字节输入和输出!!!
   当创建一个 `FileOutputStream` 对象时，就是在程序和外存中的某个文件之间建立了一个连接 有一个数据传输通道
   `FileOutputStream` 对象本身不存储数据
   当调用 FileOutputStream 的 write() 时，数据会从程序的内存中流出，经过 FileOutputStream 这个通道，最终到达外部文件进行存储
2. BufferedInputStream / BufferedOutputStream：带缓冲的字节流

**字符流**  
基类：Reader / Writer
1. FileReader / FileWriter：用于文件的字符输入和输出
2. BufferedReader / BufferedWriter：
   带缓冲的字符流，同样可以提高读写效率，并且 BufferedReader 提供了 readLine() 方法方便读取一行文本

**序列化&反序列化**  
将对象转换为字节流(序列化)并在需要时将字节流恢复为对象(反序列化)。实现 Serializable 接口的类的对象可以被序列化