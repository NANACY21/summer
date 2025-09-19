package com.matchacloud.service;

import com.matchacloud.entity.MatchRecord;

/**
 * 比赛记录Service接口，继承通用BaseService
 * 可添加比赛记录相关的自定义业务方法
 */
public interface MatchRecordService extends BaseService<MatchRecord> {

    // 示例：添加比赛记录特有的业务方法
    MatchRecord findByEvent(String event); // 根据项目查询比赛记录
}
