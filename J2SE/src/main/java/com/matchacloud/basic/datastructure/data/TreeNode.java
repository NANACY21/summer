package com.matchacloud.basic.datastructure.data;

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
