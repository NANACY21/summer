package com.matchacloud.service;

import com.matchacloud.entity.Athlete;

/**
 * 运动员Service接口，继承通用BaseService
 * 可添加运动员相关的自定义业务方法
 */
public interface AthleteService extends BaseService<Athlete> {

    // 示例：添加运动员特有的业务方法
    Athlete findByEvent(String event); // 根据项目查询运动员
}
