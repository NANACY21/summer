package com.matchacloud.service;


import com.matchacloud.entity.AthleteMatchResult;

/**
 * 运动员比赛成绩Service接口，继承通用BaseService
 * 可添加运动员比赛成绩相关的自定义业务方法
 */
public interface AthleteMatchResultService extends BaseService<AthleteMatchResult> {

    // 示例：添加运动员比赛成绩特有的业务方法
    AthleteMatchResult findByAthlete(String athlete); // 根据运动员查询运动员比赛成绩
}
