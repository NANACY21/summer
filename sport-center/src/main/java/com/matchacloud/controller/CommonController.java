package com.matchacloud.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.matchacloud.entity.*;
import com.matchacloud.enums.BusiObjEnum;
import com.matchacloud.service.*;
import com.matchacloud.utils.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 通用CURD
 */
@RestController
@RequestMapping("/api")
public class CommonController {

    // 注入所有实体的 Service
    //远动员
    @Autowired
    private AthleteService athleteService;

    //运动员比赛成绩
    @Autowired
    private AthleteMatchResultService matchResultService;

    //比赛记录
    @Autowired
    private MatchRecordService matchRecordService;

    //运动项目
    @Autowired
    private SportEventService sportEventService;

    //体育赛事
    @Autowired
    private SportsTournamentService tournamentService;

    /**
     * 根据实体类型获取对应的 Service
     */
    private BaseService<?> getService(String entityType) {
        if (BusiObjEnum.ATHLETE.getCode().equals(entityType)) {
            return athleteService;
        } else if (BusiObjEnum.ATHLETE_MATCH_RESULT.getCode().equals(entityType)) {
            return matchResultService;
        } else if (BusiObjEnum.MATCH_RECORD.getCode().equals(entityType)) {
            return matchRecordService;
        } else if (BusiObjEnum.SPORT_EVENT.getCode().equals(entityType)) {
            return sportEventService;
        } else if (BusiObjEnum.SPORTS_TOURNAMENT.getCode().equals(entityType)) {
            return tournamentService;
        } else {
            return null;
        }
    }

    /**
     * 通用分页查询接口
     * 示例：查询运动员列表 → /api/listByPage/ATHLETE?pageNum=1&pageSize=10&nameLike=博尔特
     */
    @GetMapping("/listByPage/{entityType}")
    public IPage<?> listByPage(
            @PathVariable String entityType,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam Map<String, Object> params) {
        return getService(entityType).selectByPage(pageNum, pageSize, params);
    }

    /**
     * 通用删除接口
     */
    @DeleteMapping("/delete/{entityType}/{id}")
    public boolean delete(@PathVariable String entityType, @PathVariable Long id) {
        return getService(entityType).removeById(id);
    }


    /**
     * 不直接处理JSON的通用保存接口
     * 利用Spring自动类型转换，通过反射获取具体实体类型
     */
    @PostMapping("/save/{entityType}")
    public boolean save(@PathVariable String entityType, @RequestBody String entityJson) {
        try {
            BaseEntity baseEntity = EntityUtil.resolveToEntity(entityType, entityJson);

            // 获取对应服务并保存
            BaseService service = getService(entityType);

            // 强制类型转换（因泛型擦除，需手动确认类型安全）
            @SuppressWarnings("unchecked")
            IService<BaseEntity> targetService = (IService<BaseEntity>) service;

            return targetService.save(EntityUtil.fillCreateTime(baseEntity));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("保存失败: " + e.getMessage());
        }
    }

    /**
     *
     */
    @PostMapping("/update/{entityType}")
    public boolean update(@PathVariable String entityType, @RequestBody String entityJson) {
        try {
            BaseEntity baseEntity = EntityUtil.resolveToEntity(entityType, entityJson);

            // 获取对应服务并保存
            BaseService service = getService(entityType);

            // 强制类型转换（因泛型擦除，需手动确认类型安全）
            @SuppressWarnings("unchecked")
            IService<BaseEntity> targetService = (IService<BaseEntity>) service;

            return targetService.updateById(EntityUtil.fillUpdateTime(baseEntity));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("更新失败: " + e.getMessage());
        }
    }

    /**
     * 通用查询所有数据方法
     * 支持查询指定实体类型的全量数据
     * @param entityType 实体类型编码（如：ATHLETE、MATCH_RECORD）
     * @return 该实体类型的所有数据列表
     */
    @GetMapping("/list/{entityType}")
    public List<? extends BaseEntity> listAll(@PathVariable String entityType) {
        // 1. 验证实体类型合法性
        BusiObjEnum typeEnum = BusiObjEnum.getByCode(entityType);
        if (typeEnum == null) {
            throw new IllegalArgumentException("未知的实体类型: " + entityType);
        }

        // 2. 获取对应的服务
        IService service = getService(entityType);
        if (service == null) {
            throw new RuntimeException("未找到实体服务: " + entityType);
        }

        // 3. 调用服务的list方法查询所有数据
        // 注意：这里调用的是IService自带的list()方法，无需额外实现
        return service.list();
    }
}