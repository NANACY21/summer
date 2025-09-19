package com.matchacloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.matchacloud.entity.Athlete;
import com.matchacloud.entity.MatchRecord;
import com.matchacloud.mapper.AthleteMapper;
import com.matchacloud.mapper.MatchRecordMapper;
import com.matchacloud.service.AthleteService;
import com.matchacloud.service.MatchRecordService;
import org.springframework.stereotype.Service;

/**
 * 比赛记录Service实现类，继承通用Service
 */
@Service
public class MatchRecordServiceImpl extends BaseServiceImpl<MatchRecordMapper, MatchRecord> implements MatchRecordService {
    @Override
    public MatchRecord findByEvent(String event) {
        QueryWrapper<MatchRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event", event);
        return baseMapper.selectOne(queryWrapper);
    }
    // 可添加比赛记录相关的业务方法
}
