package com.matchacloud.basic.datastructure.pojo;

import lombok.Data;

import java.util.List;

/**
 * 树节点
 */
@Data
public class TreeNode extends BasicTreeNode {
    /**
     * 展示名称
     */
    private String showName;

    private List<TreeNode> children;
}
