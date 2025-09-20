package com.matchacloud.utils;

import com.alibaba.fastjson.JSON;
import com.matchacloud.entity.*;
import com.matchacloud.enums.BusiObjEnum;

import java.time.LocalDateTime;

public class EntityUtil {
    public static BaseEntity fillCreateTime(BaseEntity entity) {
        entity.setCreateTime(LocalDateTime.now());

        return entity;
    }

    public static BaseEntity fillUpdateTime(BaseEntity entity) {
        entity.setUpdateTime(LocalDateTime.now());

        return entity;
    }

    /**
     * 通用保存接口的参数解析成实体对象
     * @param entityType
     * @param entityJson
     * @return
     */
    public static BaseEntity resolveToEntity(String entityType, String entityJson) {
        // 1. 验证实体类型合法性
        BusiObjEnum typeEnum = BusiObjEnum.getByCode(entityType);
        if (typeEnum == null) {
            throw new IllegalArgumentException("未知的实体类型: " + entityType);
        }

        // 2. 验证实体类型匹配（接口参数类型与实际类型）
        Class<? extends BaseEntity> entityClass = getEntityClass(entityType);
        if (!entityClass.isInstance(entityJson)) {
            //throw new IllegalArgumentException("实体类型不匹配，期望: " + entityClass.getSimpleName());
        }
        return JSON.parseObject(entityJson, entityClass);
    }

    /**
     * 获取实体类Class（与枚举对应）
     */
    private static Class<? extends BaseEntity> getEntityClass(String entityType) {
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
}
