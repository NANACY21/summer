package com.matchacloud.basic.datastructure;

import com.matchacloud.basic.datastructure.data.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lichi
 */
public class TreeBuilder {
    /**
     * 获取根节点
     * @return
     */
    public TreeNode getRootNode() {
        return new TreeNode();
    }

    /**
     * 获取2级节点列表
     * @return
     */
    public List<TreeNode> getLevel2Nodes() {
        return new ArrayList<>();
    }

    /**
     * 创建任意层级节点
     * @return
     */
    public TreeNode createNode() {
        return new TreeNode();
    }
}
