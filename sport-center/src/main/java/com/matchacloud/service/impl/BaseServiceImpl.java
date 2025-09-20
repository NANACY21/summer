package com.matchacloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.matchacloud.mapper.MyBaseMapper;
import com.matchacloud.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 通用Service实现类，继承MyBatis-Plus的ServiceImpl并实现自定义BaseService
 * @param <M> Mapper接口泛型
 * @param <T> 实体类泛型
 */
public class BaseServiceImpl<M extends MyBaseMapper<T>, T>
        extends ServiceImpl<M, T> implements BaseService<T> {

    /**
     * 分页查询（可根据参数动态拼接条件）
     */
    @Override
    public IPage<T> selectByPage(int pageNum, int pageSize, Map<String, Object> params) {
        Page<T> page = new Page<>(pageNum, pageSize);
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        // 示例：根据params中的参数动态添加查询条件（可根据实际需求扩展）
        if (params != null && !params.isEmpty()) {
            params.remove("pageNum");
            params.remove("pageSize");
            params.forEach((key, value) -> {
                if (value != null && !"".equals(value.toString().trim())) {
                    queryWrapper.like(key, value); // 这里默认用like，实际可根据字段类型调整
                }
            });
        }

        return baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 查询所有
     */
    @Override
    public List<T> list() {
        return baseMapper.selectList(null);
    }

    /**
     * 新增
     */
    @Override
    public boolean save(T entity) {
        return baseMapper.insert(entity) > 0;
    }

    /**
     * 更新
     */
    @Override
    public boolean updateById(T entity) {
        return baseMapper.updateById(entity) > 0;
    }
}
