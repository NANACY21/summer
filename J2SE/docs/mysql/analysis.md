### 场景分析

1. `为什么深分页很慢`(比如页面翻到第500页 每页100条数据)  
    limit 50000,100 mysql会拿到所有的500100条数据 然后找出想查询的数据  
    如果有2级索引查询有可能会更慢