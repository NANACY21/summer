package com.matchacloud.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 运动员比赛成绩表实体类（精细化成绩数据）
 * 对应数据库表：athlete_match_result
 */
@Data
@TableName("athlete_match_result")
public class AthleteMatchResult extends BaseEntity {

    /**
     * 成绩ID（主键）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 场次ID（关联match_session.id）
     */
    @TableField("session_id")
    private Long sessionId;

    /**
     * 运动员ID（关联athlete.id）
     */
    @TableField("athlete_id")
    private Long athleteId;

    /**
     * 所属队伍ID（关联team.id，个人项目可为NULL）
     */
    @TableField("team_id")
    private Long teamId;

    /**
     * 赛道号（如田径/游泳项目）
     */
    @TableField("lane_num")
    private Integer laneNum;

    /**
     * 小组号（如资格赛分组）
     */
    @TableField("group_num")
    private Integer groupNum;

    /**
     * 成绩数值（如：10.010秒，8.950米）
     */
    @TableField("result_value")
    private BigDecimal resultValue;

    /**
     * 成绩文本（如：DNS=未出发，DQ=取消资格，DNF=未完成）
     */
    @TableField("result_text")
    private String resultText;

    /**
     * 反应时间（如田径项目，单位：秒）
     */
    @TableField("reaction_time")
    private BigDecimal reactionTime;

    /**
     * 风速（如田径短跑，单位：m/s，+为顺风）
     */
    @TableField("wind_speed")
    private BigDecimal windSpeed;

    /**
     * 小组排名
     */
    @TableField("group_ranking")
    private Integer groupRanking;

    /**
     * 最终排名
     */
    @TableField("final_ranking")
    private Integer finalRanking;

    /**
     * 奖牌类型（0：无；1：金牌；2：银牌；3：铜牌；4：其他奖牌）
     */
    @TableField("medal_type")
    private Integer medalType;

    /**
     * 是否破世界纪录（0：否；1：是）
     */
    @TableField("is_world_record")
    private Integer isWorldRecord;

    /**
     * 是否破赛事纪录（0：否；1：是）
     */
    @TableField("is_tournament_record")
    private Integer isTournamentRecord;

    /**
     * 备注（如：抢跑犯规，二次出发）
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
