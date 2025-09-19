package com.matchacloud.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 运动员信息表实体类（非学校场景）
 * 对应数据库表：athlete
 */
@Data
@TableName("athlete") // 指定对应数据库表名
public class Athlete extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO) // 主键自增
    private Long id;

    /**
     * 姓名
     */
    @TableField("name") // 对应表字段名（字段名一致时可省略）
    private String name;

    /**
     * 登录密码(MD5加密)
     */
    @TableField("password")
    private String password;

    /**
     * 运动员编号(唯一标识)
     */
    @TableField("athlete_code")
    private String athleteCode;

    /**
     * 出生日期
     */
    @TableField("birthday")
    private LocalDate birthday; // 使用LocalDate映射date类型

    /**
     * 性别（1：男；2：女）
     */
    @TableField("sex")
    private Integer sex; // 用Integer接收tinyint类型

    /**
     * 民族
     */
    @TableField("nation")
    private String nation;

    /**
     * 国籍
     */
    @TableField("nationality")
    private String nationality;

    /**
     * 所属队伍ID
     */
    @TableField("team_id")
    private Long teamId;

    /**
     * 所属队伍名称
     */
    @TableField("team_name")
    private String teamName;

    /**
     * 主项运动（如：篮球、田径）
     */
    @TableField("event")
    private String event;

    /**
     * 场上位置（如：前锋、守门员）
     */
    @TableField("position")
    private String position;

    /**
     * 身份证号/护照号
     */
    @TableField("identity")
    private String identity;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 籍贯
     */
    @TableField("native_place")
    private String nativePlace;

    /**
     * 状态（1:现役；2：退役；3：伤停；4：禁赛）
     */
    @TableField("status")
    private Integer status;

    /**
     * 入队时间
     */
    @TableField("entry_time")
    private LocalDate entryTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT) // 插入时自动填充
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE) // 插入和更新时自动填充
    private LocalDateTime updateTime;
}
