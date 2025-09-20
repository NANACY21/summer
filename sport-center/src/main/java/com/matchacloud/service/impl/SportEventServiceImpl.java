package com.matchacloud.service.impl;

import com.matchacloud.annotation.RedisCache;
import com.matchacloud.entity.SportEvent;
import com.matchacloud.mapper.SportEventMapper;
import com.matchacloud.service.SportEventService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 运动项目Service实现类，继承通用Service
 */
@Service
public class SportEventServiceImpl extends BaseServiceImpl<SportEventMapper, SportEvent> implements SportEventService {

    // 使用注解自动缓存，键名为 "EventService:getById:1001"
    @Override
    @RedisCache(prefix = "event:info", suffix = "#id", timeout = 1, unit = TimeUnit.HOURS)
    public SportEvent getSportEventById(Long id) {
        return baseMapper.selectById(id); // 方法结果会自动缓存
    }
    // 可添加运动项目相关的业务方法
}
