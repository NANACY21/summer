package com.matchacloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * 通用Mapper接口，继承MyBatis-Plus的BaseMapper
 * @param <T> 实体类
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 自定义通用分页查询
     * @param page
     * @param params
     * @return
     */
    IPage<T> selectByPage(Page<T> page, Map<String, Object> params);
}
