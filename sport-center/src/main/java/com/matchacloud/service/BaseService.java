package com.matchacloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 通用Service接口，定义基础CRUD方法
 * @param <T> 实体类泛型
 */
public interface BaseService<T> extends IService<T> {

    // 分页查询
    IPage<T> selectByPage(int pageNum, int pageSize, Map<String, Object> params);
}
