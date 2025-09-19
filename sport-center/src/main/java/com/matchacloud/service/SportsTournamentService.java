package com.matchacloud.service;

import com.matchacloud.entity.SportsTournament;

/**
 * 体育赛事Service接口，继承通用BaseService
 * 可添加体育赛事相关的自定义业务方法
 */
public interface SportsTournamentService extends BaseService<SportsTournament> {

    // 示例：添加体育赛事特有的业务方法
    SportsTournament findByYear(String year); // 根据年份查询体育赛事
}
