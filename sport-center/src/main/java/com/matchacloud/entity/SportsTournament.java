package com.matchacloud.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 体育赛事表实体类（核心赛事信息）
 * 对应数据库表：sports_tournament
 */
@Data
@TableName("sports_tournament")
public class SportsTournament extends BaseEntity {

    /**
     * 赛事ID（主键）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 赛事唯一编码（如：OLY-2024-PAR，OLY=奥运会，2024=年份，PAR=巴黎）
     */
    @TableField("tournament_code")
    private String tournamentCode;

    /**
     * 赛事中文名称（如：2024年巴黎夏季奥林匹克运动会）
     */
    @TableField("tournament_name_cn")
    private String tournamentNameCn;

    /**
     * 赛事英文名称（如：2024 Paris Summer Olympics）
     */
    @TableField("tournament_name_en")
    private String tournamentNameEn;

    /**
     * 赛事级别编码（关联tournament_level.level_code）
     */
    @TableField("level_code")
    private String levelCode;

    /**
     * 主办机构（如：国际奥林匹克委员会）
     */
    @TableField("host_org")
    private String hostOrg;

    /**
     * 主办国家
     */
    @TableField("host_country")
    private String hostCountry;

    /**
     * 主办国家ISO代码（如：FR=法国）
     */
    @TableField("host_country_code")
    private String hostCountryCode;

    /**
     * 主办城市
     */
    @TableField("host_city")
    private String hostCity;

    /**
     * 赛事开始日期
     */
    @TableField("start_date")
    private LocalDate startDate;

    /**
     * 赛事结束日期
     */
    @TableField("end_date")
    private LocalDate endDate;

    /**
     * 赛事LOGO URL
     */
    @TableField("logo_url")
    private String logoUrl;

    /**
     * 赛事官方网站
     */
    @TableField("official_url")
    private String officialUrl;

    /**
     * 赛事状态（0：筹备中；1：报名中；2：进行中；3：已结束；4：延期；5：取消）
     */
    @TableField("status")
    private Integer status;

    /**
     * 取消/延期原因（状态为4/5时必填）
     */
    @TableField("cancel_reason")
    private String cancelReason;

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
