CREATE TABLE `dist_lock` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `lock_key` varchar(512) NOT NULL COMMENT '锁的唯一标识（业务名称/编号）',
                             `lock_owner` varchar(128) NOT NULL COMMENT '持有锁的实例/线程标识',
                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加锁时间',
                             `expire_time` datetime NOT NULL COMMENT '锁过期时间（防死锁）',
                             PRIMARY KEY (`id`),
    -- 核心：唯一索引，保证同一 lock_key 只能存在一条，实现互斥
                             UNIQUE KEY `uk_lock_key` (`lock_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='MySQL分布式锁表';