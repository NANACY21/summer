### Linux命令


#### 文件/目录操作
1. `touch file1.txt` 新建文件
2. `sudo cp 原文件 新文件` 复制文件
3. `rm xxx.txt` 删除文件
4. `mkdir 目录名` 新建目录
5. `rm -rf 目录名` 删除目录
6. `ls -l` 显示当前目录下的内容
7. `cd ..` 返回上一级目录
8. `scp -r ~/.ssh/id_rsa.pub root@vm2:/tmp` 远程复制


#### 查找文件/目录
1. `find / -name java` 找所有和java、jdk有关的目录或文件  
2. `find . -name "*.xxx"` 列出当前目录及其子目录下扩展名是xxx的文件  
3. `find . -type f` 列出当前目录及其子目录下所有一般文件


#### 系统操作
1. `sudo shutdown -P now` 关机  
2. `sudo poweroff` 关机  
3. `sudo reboot` 重启
4. `linux怎么查看某端口是否被占用：`
    1. `ss -tuln | grep 8080`
    2. `netstat -tuln | grep 8080`(如果输出中显示了该端口，那么说明该端口已被占用)
  

#### 安装/下载的操作
1. `sudo apt install vim-gtk3` 安装vim
2. `apt-get` 用于安装/卸载软件等  
3. `sudo apt-get update` 更新源


#### 网络操作
1. `ifconfig` 显示或配置网络设备  
2. `ping ip地址` 查看是否能连接到该ip地址  
3. `route` 用于显示和操作IP路由表
4. `rsync` 用于远程数据同步


#### 查看文件内容
1. `sudo gedit 文件名` 以记事本形式编辑文件
2. `cat .txt` 输出文件内容
3. `less .txt` 打开文件 查看文件内容
4. `sudo vim runoob.txt` 文本形式打开文件(默认命令模式)
    1. `进入到一个文本类文件后，输入:/要查找的关键字回车` 查找关键字高亮显示
    2. `键入:` 输入命令
    3. `键入ESC` 切换成命令模式
    4. `键入i` 文本文件可编辑
    5. `键入ggdG` 清空文本文件
5. `tail -f -n 3000 文件名` 操作应用后输命令，实时打印文件后3000行  
6. `tail -f .log日志文件 | grep -C10  'keyword'` 输命令后操作应用，监听日志文件，查看关键字日志及前后10行的日志，实时刷新