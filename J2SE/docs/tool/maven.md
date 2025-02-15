### 概述
`maven`是一款`跨平台项目管理工具`，可以理解为一个`jar仓库`，分为`本地仓库`和`远程仓库`  
maven可以管理`项目的生命周期`，管理`依赖关系`，管理`jar包`

[maven坐标网址](https://mvnrepository.com/)，在这里寻找你需要的jar包依赖，在该网站找到你所需的jar包后，在pom.xml添加该依赖，该依赖对应jar包会保存到本地maven仓库，下次再使用相同的jar包就不需上网了，这即是`maven私服`

在`settings.xml`中设置`repository目录的路径(对于repository目录仅需如此)`，IDEA会自动寻找repository目录

### git工程结构&微服务结构
一个微服务通常由多个子模块组成，每个子模块对应有一个pom.xml，微服务根目录有一个父pom.xml  
子模块打成jar包发到maven仓库，其他微服务/模块的pom.xml添加该依赖即可
`模块`功能要有单一性、通用性，可以用作jar包供其他模块使用  

**jar包&war包**  
1. `jar包`：Java应用档案 静态的
2. `war包`：web应用档案 打包web应用程序的产物 静态的 部署在Java web容器中比如tomcat
