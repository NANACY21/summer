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