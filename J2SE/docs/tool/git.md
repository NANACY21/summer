### git&github
git用于源码管理  
`PC1：`自己的一台电脑  
`PC2：`自己的另一台电脑

`pc1`上安装了git软件，pc1上生成一对公钥/秘钥，每台pc本地都有一对公钥密钥  
GitHub/GitLab远程上SSH Keys：代表有哪些pc连接到了此远程，其中公钥要在GitHub/gitlab上填写以绑定，从而确定是哪一台电脑连接GitHub/gitlab账户

`pc1 - github`
`pc1 - gitlab`
`pc2 - github`
`pc2 - gitlab`

git使用协议：ssh https 底层tcp协议


### git命令
1. `git checkout 分支名`：切换到已有分支
2. `git checkout -b abc`：创建并切换到一个名为 abc 的新分支
3. `git apply 文件名`：


### 常见的情况
1. 遇到代码冲突怎么解决?