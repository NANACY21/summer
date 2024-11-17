### Ajax
什么是ajax？ajax：异步的javascript和xml  
jq封装了ajax，jq支持ajax
异步刷新，使用ajax可以不刷新整个网页，刷新局部网页
当ui不访问控制器时(或者说仅是ui的交互时)，没必要刷新页面
### ajax作用
- 动态的把innerload.html包含进此网页，此页面还无需刷新
- 异步提交UI的数据，而无需刷新整个页面

### nodejs vue项目
快速删除mode_modules目录 `npm install rimraf -g` `rimraf node_modules`  
created() `html加载完成之前执行；执行顺序：父组件-子组件`  
VUE文件命名 `避免敏感关键字`  