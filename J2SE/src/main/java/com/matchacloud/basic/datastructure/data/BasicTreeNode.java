package com.matchacloud.basic.datastructure.data;

import lombok.Data;

/**
 * 基础树节点
 */
@Data
public abstract class BasicTreeNode {
    /**
     * id
     */
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否是叶子节点
     */
    private boolean leaf;

    /**
     * 层级
     */
    private int level;
}
