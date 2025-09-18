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

    // 查询所有
    List<T> list();

    // 根据ID查询
    T getById(Long id);

    // 新增
    boolean save(T entity);

    // 更新
    boolean updateById(T entity);

    // 删除
    boolean removeById(Long id);
}
