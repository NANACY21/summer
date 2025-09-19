package com.matchacloud.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 运动项目表实体类（标准化基础数据）
 * 对应数据库表：sport_event
 */
@Data
@TableName("sport_event")
public class SportEvent {

    /**
     * 项目ID（主键）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 国际标准项目编码（如：ATH-M-100M，ATH=田径，M=男子，100M=100米）
     */
    @TableField("event_code")
    private String eventCode;

    /**
     * 项目中文名称（如：男子100米短跑）
     */
    @TableField("event_name_cn")
    private String eventNameCn;

    /**
     * 项目英文名称（如：Men's 100m Sprint）
     */
    @TableField("event_name_en")
    private String eventNameEn;

    /**
     * 项目大类编码（如：ATH=田径，BAS=篮球，SWM=游泳）
     */
    @TableField("category_code")
    private String categoryCode;

    /**
     * 项目大类名称（如：田径）
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 性别分组（0：混合；1：男子；2：女子）
     */
    @TableField("gender_type")
    private Integer genderType;

    /**
     * 是否团体项目（0：个人；1：团体）
     */
    @TableField("is_team")
    private Integer isTeam;

    /**
     * 成绩单位（如：s=秒，m=米，pt=分）
     */
    @TableField("result_unit")
    private String resultUnit;

    /**
     * 成绩计算方式（0：数值越小越好；1：数值越大越好）
     */
    @TableField("result_calc_type")
    private Integer resultCalcType;

    /**
     * 项目描述（如：奥运会核心田径项目，距离100米，分预赛/半决赛/决赛）
     */
    @TableField("description")
    private String description;

    /**
     * 状态（0：废弃；1：在用）
     */
    @TableField("status")
    private Integer status;

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
