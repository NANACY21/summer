package com.matchacloud.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.matchacloud.service.AthleteService;
import com.matchacloud.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 通用CURD
 */
@RestController
@RequestMapping("/api")
public class CommonController {

//    // 注入所有实体的 Service
//    @Autowired
//    private AthleteService athleteService;
////    @Autowired
////    private SportItemService sportItemService;
////    @Autowired
////    private SportsEventService sportsEventService;
////    @Autowired
////    private MatchService matchService;
//
//    /**
//     * 根据实体类型获取对应的 Service
//     */
//    private BaseService<?> getService(String entityType) {
//        return switch (entityType) {
//            case "athlete" -> athleteService;
//            case "sportItem" -> athleteService;
//            case "sportsEvent" -> athleteService;
//            case "match" -> athleteService;
//            default -> throw new IllegalArgumentException("未知实体类型：" + entityType);
//        };
//    }
//
//    /**
//     * 通用分页查询接口
//     * 示例：查询运动员列表 → /api/page/athlete?pageNum=1&pageSize=10&nameLike=博尔特
//     */
//    @GetMapping("/listByPage/{entityType}")
//    public IPage<?> listByPage(
//            @PathVariable String entityType,
//            @RequestParam(defaultValue = "1") int pageNum,
//            @RequestParam(defaultValue = "10") int pageSize,
//            @RequestParam Map<String, Object> params) {
//        return getService(entityType).pageQuery(pageNum, pageSize, params);
//    }
//
//    /**
//     * 通用新增接口
//     * 示例：新增运动员 → /api/save/athlete + 请求体（运动员信息）
//     */
//    @PostMapping("/save/{entityType}")
//    public boolean save(@PathVariable String entityType, @RequestBody Object entity) {
//        return getService(entityType).save(entity);
//    }
//
//    /**
//     * 通用更新接口
//     */
//    @PutMapping("/update/{entityType}")
//    public boolean update(@PathVariable String entityType, @RequestBody Object entity) {
//        return getService(entityType).updateById(entity);
//    }
//
//    /**
//     * 通用删除接口
//     */
//    @DeleteMapping("/delete/{entityType}/{id}")
//    public boolean delete(@PathVariable String entityType, @PathVariable Long id) {
//        return getService(entityType).removeById(id);
//    }
}