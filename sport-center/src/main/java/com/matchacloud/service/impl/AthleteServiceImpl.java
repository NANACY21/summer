package com.matchacloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.matchacloud.entity.Athlete;
import com.matchacloud.mapper.AthleteMapper;
import com.matchacloud.service.AthleteService;
import org.springframework.stereotype.Service;

/**
 * 运动员Service实现类，继承通用Service
 */
@Service
public class AthleteServiceImpl extends BaseServiceImpl<AthleteMapper, Athlete> implements AthleteService {
    @Override
    public Athlete findByEvent(String event) {
        QueryWrapper<Athlete> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event", event);
        return baseMapper.selectOne(queryWrapper);
    }
    // 可添加运动员相关的业务方法
}
