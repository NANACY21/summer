package com.matchacloud.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 实体类型枚举类
 * 包含系统中核心实体的类型定义
 */
@Getter
@AllArgsConstructor
public enum BusiObjEnum {

    /**
     * 运动项目实体
     */
    SPORT_EVENT("SPORT_EVENT", "运动项目", "对应sport_event表，存储标准化的运动项目信息"),

    /**
     * 体育赛事实体
     */
    SPORTS_TOURNAMENT("SPORTS_TOURNAMENT", "体育赛事", "对应sports_tournament表，存储核心赛事信息"),

    /**
     * 比赛记录实体
     */
    MATCH_RECORD("MATCH_RECORD", "比赛记录", "对应match_record表，存储具体比赛场次信息"),

    /**
     * 运动员比赛成绩实体
     */
    ATHLETE_MATCH_RESULT("ATHLETE_MATCH_RESULT", "运动员比赛成绩", "对应athlete_match_result表，存储精细化的成绩数据"),

    /**
     * 运动员实体
     */
    ATHLETE("ATHLETE", "运动员", "对应athlete表，存储运动员信息"),
    ;

    /**
     * 实体类型编码（唯一标识）
     */
    private final String code;

    /**
     * 实体类型中文名称
     */
    private final String name;

    /**
     * 实体类型描述
     */
    private final String description;

    /**
     * 根据编码获取枚举实例
     *
     * @param code 实体类型编码
     * @return 对应的枚举实例，若未找到则返回null
     */
    public static BusiObjEnum getByCode(String code) {
        for (BusiObjEnum type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
}
