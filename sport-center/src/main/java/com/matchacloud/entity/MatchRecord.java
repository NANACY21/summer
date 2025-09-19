package com.matchacloud.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 比赛记录表实体类
 * 对应数据库表：match_record
 */
@Data
@TableName("match_record")
public class MatchRecord {

    /**
     * 记录ID（主键）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 比赛编码（唯一标识，如：OLY-2024-ATH-100M-001）
     */
    @TableField("match_code")
    private String matchCode;

    /**
     * 赛事ID（关联sports_tournament.id）
     */
    @TableField("tournament_id")
    private Long tournamentId;

    /**
     * 项目ID（关联sport_event.id）
     */
    @TableField("event_id")
    private Long eventId;

    /**
     * 比赛名称（如：2024巴黎奥运会-男子100米决赛）
     */
    @TableField("match_name")
    private String matchName;

    /**
     * 比赛轮次（如：预赛、半决赛、决赛）
     */
    @TableField("match_round")
    private String matchRound;

    /**
     * 比赛日期
     */
    @TableField("match_date")
    private LocalDate matchDate;

    /**
     * 比赛开始时间
     */
    @TableField("match_time")
    private LocalTime matchTime;

    /**
     * 比赛场馆（如：法兰西体育场）
     */
    @TableField("venue")
    private String venue;

    /**
     * 主裁判姓名
     */
    @TableField("referee_name")
    private String refereeName;

    /**
     * 比赛状态（0：未开始；1：进行中；2：已结束；3：延期）
     */
    @TableField("match_status")
    private Integer matchStatus;

    /**
     * 结果描述（如：打破世界纪录）
     */
    @TableField("result_desc")
    private String resultDesc;

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
