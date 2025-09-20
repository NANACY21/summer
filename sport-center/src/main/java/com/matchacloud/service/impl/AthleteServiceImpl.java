package com.matchacloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.matchacloud.constant.RedisKeyConstant;
import com.matchacloud.entity.Athlete;
import com.matchacloud.mapper.AthleteMapper;
import com.matchacloud.service.AthleteService;
import com.matchacloud.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 运动员Service实现类，继承通用Service
 */
@Service
public class AthleteServiceImpl extends BaseServiceImpl<AthleteMapper, Athlete> implements AthleteService {

    @Autowired
    private RedisService redisService;

    @Override
    public Athlete getAthleteById(Long id) {
        // 1. 先查缓存
        String cacheKey = RedisKeyConstant.ATHLETE_INFO + id;
        Athlete athlete = redisService.get(cacheKey, Athlete.class);
        if (athlete != null) {
            return athlete;
        }

        // 2. 缓存未命中，查数据库
        athlete = baseMapper.selectById(id);

        // 3. 存入缓存（设置2小时过期）
        if (athlete != null) {
            redisService.set(cacheKey, athlete, 2, TimeUnit.HOURS);
        }
        return athlete;
    }

    @Override
    public Athlete findByEvent(String event) {
        QueryWrapper<Athlete> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event", event);
        return baseMapper.selectOne(queryWrapper);
    }
    // 可添加运动员相关的业务方法
}
