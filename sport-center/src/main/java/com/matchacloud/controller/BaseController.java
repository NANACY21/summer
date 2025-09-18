package com.matchacloud.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.matchacloud.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 通用Controller基类
 * 通用CURD
 */
@RestController
public abstract class BaseController<T> {

//    @Autowired
//    protected BaseServiceImpl<?, T> baseService;
//
//    // 分页查询
//    @GetMapping("/page")
//    public R<IPage<T>> getPage(
//            @RequestParam(defaultValue = "1") int pageNum,
//            @RequestParam(defaultValue = "10") int pageSize,
//            @RequestParam Map<String, Object> params) {
//        return R.ok(baseService.selectByPage(pageNum, pageSize, params));
//    }
//
//    // 获取所有数据
//    @GetMapping
//    public R<List<T>> getAll() {
//        return R.ok(baseService.list());
//    }
//
//    // 根据ID查询
//    @GetMapping("/{id}")
//    public R<T> getById(@PathVariable Long id) {
//        return R.ok(baseService.getById(id));
//    }
//
//    // 新增
//    @PostMapping
//    public R<Boolean> save(@RequestBody T entity) {
//        return R.ok(baseService.save(entity));
//    }
//
//    // 更新
//    @PutMapping
//    public R<Boolean> update(@RequestBody T entity) {
//        return R.ok(baseService.updateById(entity));
//    }
//
//    // 删除
//    @DeleteMapping("/{id}")
//    public R<Boolean> delete(@PathVariable Long id) {
//        return R.ok(baseService.removeById(id));
//    }
}
