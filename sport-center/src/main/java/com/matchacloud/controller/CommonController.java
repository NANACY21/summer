package com.matchacloud.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.matchacloud.entity.*;
import com.matchacloud.enums.BusiObjEnum;
import com.matchacloud.service.*;
import com.matchacloud.utils.FillFieldUtil;
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
        //todo 有问题
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
    public boolean save(@PathVariable String entityType, @RequestBody String entity) {
        try {
            // 1. 验证实体类型合法性
            BusiObjEnum typeEnum = BusiObjEnum.getByCode(entityType);
            if (typeEnum == null) {
                throw new IllegalArgumentException("未知的实体类型: " + entityType);
            }

            // 2. 验证实体类型匹配（接口参数类型与实际类型）
            Class<? extends BaseEntity> entityClass = getEntityClass(entityType);
            if (!entityClass.isInstance(entity)) {
                //throw new IllegalArgumentException("实体类型不匹配，期望: " + entityClass.getSimpleName());
            }
            BaseEntity baseEntity = JSON.parseObject(entity, entityClass);

            // 4. 获取对应服务并保存
            BaseService service = getService(entityType);

            // 5. 强制类型转换（因泛型擦除，需手动确认类型安全）
            @SuppressWarnings("unchecked")
            IService<BaseEntity> targetService = (IService<BaseEntity>) service;

            return targetService.save(FillFieldUtil.fillField(baseEntity));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("保存失败: " + e.getMessage());
        }
    }

    /**
     * 获取实体类Class（与枚举对应）
     */
    private Class<? extends BaseEntity> getEntityClass(String entityType) {
        switch (entityType) {
            case "SPORT_EVENT":
                return SportEvent.class;
            case "SPORTS_TOURNAMENT":
                return SportsTournament.class;
            case "MATCH_RECORD":
                return MatchRecord.class;
            case "ATHLETE_MATCH_RESULT":
                return AthleteMatchResult.class;
            case "ATHLETE":
                return Athlete.class;
            default:
                return null;
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