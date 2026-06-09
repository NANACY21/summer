package com.matchacloud.summerstarter.distlock.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

/**
 * 分布式锁记录实体（Lombok + MyBatis-Plus）
 * @author lichi
 */
@Data
@TableName("dist_lock")
public class DistLock {

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 锁的唯一标识 */
    private String lockKey;

    /** 锁持有者标识 */
    private String lockOwner;

    /** 加锁时间 */
    private Date createTime;

    /** 锁过期时间 */
    private Date expireTime;
}
