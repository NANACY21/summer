package com.matchacloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.matchacloud.entity.SportsTournament;
import com.matchacloud.mapper.SportsTournamentMapper;
import com.matchacloud.service.SportsTournamentService;
import org.springframework.stereotype.Service;

/**
 * 体育赛事Service实现类，继承通用Service
 */
@Service
public class SportsTournamentServiceImpl extends BaseServiceImpl<SportsTournamentMapper, SportsTournament> implements SportsTournamentService {
    @Override
    public SportsTournament findByYear(String year) {
        QueryWrapper<SportsTournament> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year", year);
        return baseMapper.selectOne(queryWrapper);
    }
    // 可添加体育赛事相关的业务方法
}
