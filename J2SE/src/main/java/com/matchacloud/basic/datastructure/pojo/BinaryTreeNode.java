package com.matchacloud.basic.datastructure.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 二叉树节点
 * 二叉树遍历方式:
 * 1 前序(先序)遍历:中->左->右
 * 2 中序遍历:左->中->右
 * 3 后序遍历:左->右->中
 * 4 层次遍历:从根节点开始，按照层次从上到下，每层从左到右的顺序依次访问节点
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BinaryTreeNode {

    private String data;
    private BinaryTreeNode leftChild;
    private BinaryTreeNode rightChild;
}