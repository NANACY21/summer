package com.matchacloud.service.impl;

import com.matchacloud.entity.SportEvent;
import com.matchacloud.mapper.SportEventMapper;
import com.matchacloud.service.SportEventService;
import org.springframework.stereotype.Service;

/**
 * 运动项目Service实现类，继承通用Service
 */
@Service
public class SportEventServiceImpl extends BaseServiceImpl<SportEventMapper, SportEvent> implements SportEventService {

    // 可添加运动项目相关的业务方法
}
