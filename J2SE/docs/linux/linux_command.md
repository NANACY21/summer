`rm xxx.txt` 删除文件  
`rm -rf 目录名` 删除目录  
`touch 文件名` 新建文件  
`mkdir 目录名` 新建目录  
`ls -l` 显示当前目录下的内容  
`cd ..` 返回上一级目录  
`进入到一个文本类文件后，输入:/要查找的关键字回车` 查找关键字高亮显示  


`find / -name java` 找所有和java、jdk有关的目录或文件  
`find . -name "*.xxx"` 列出当前目录及其子目录下扩展名是xxx的文件  
`find . -type f` 列出当前目录及其子目录下所有一般文件  
`scp -r ~/.ssh/id_rsa.pub root@vm2:/tmp` 远程复制  
`sudo gedit 文件名` 以记事本形式编辑文件  
`sudo shutdown -P now` 关机  
`sudo poweroff` 关机  
`sudo reboot` 重启  
`sudo cp 原文件 新文件` 复制文件  
`gedit ./a.txt` 用记事本编辑  
`sudo apt install vim-gtk3` 安装vim  
`sudo vim 文件` 文本形式打开文件(默认命令模式)  
打开文件后  
- `键入:` 输入命令
- `键入ESC` 切换成命令模式
- `键入i` 文本文件可编辑
- `键入ggdG` 清空文本文件


`ifconfig` 显示或配置网络设备  
`ping ip地址` 查看是否能连接到该ip地址  
`route` 用于显示和操作IP路由表  
`rsync` 用于远程数据同步  
`apt-get` 用于安装/卸载软件等  
`sudo apt-get update` 更新源  

`tail -f -n 3000 文件名` 操作应用后输命令，实时打印文件后3000行  
`tail -f .log日志文件 | grep -C10  'keyword'` 输命令后操作应用，监听日志文件，查看关键字日志及前后10行的日志，实时刷新  
`linux怎么查看某端口是否被占用` ss -tuln | grep 8080  
`netstat -tuln | grep 8080` 如果输出中显示了该端口，那么说明该端口已被占用  


`cat .txt` 查看文件内容  

`less .txt` 查看文件内容  
- /keyword 搜索关键字

`vim runoob.txt` 打开文件  