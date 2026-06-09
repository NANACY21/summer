package com.matchacloud.summerstarter.distlock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.matchacloud.summerstarter.distlock.dto.DistLock;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lichi
 */
@Mapper
public interface DistLockMapper extends BaseMapper<DistLock> {
    // 继承 BaseMapper 后，自动拥有所有 CRUD 方法，无需写任何代码
}
