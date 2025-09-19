package com.matchacloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.matchacloud.entity.AthleteMatchResult;
import com.matchacloud.mapper.AthleteMatchResultMapper;
import com.matchacloud.service.AthleteMatchResultService;
import org.springframework.stereotype.Service;

/**
 * 运动员比赛成绩Service实现类，继承通用Service
 */
@Service
public class AthleteMatchResultServiceImpl extends BaseServiceImpl<AthleteMatchResultMapper, AthleteMatchResult> implements AthleteMatchResultService {
    @Override
    public AthleteMatchResult findByAthlete(String athlete) {
        QueryWrapper<AthleteMatchResult> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("athlete", athlete);
        return baseMapper.selectOne(queryWrapper);
    }
    // 可添加运动员比赛成绩相关的业务方法
}
