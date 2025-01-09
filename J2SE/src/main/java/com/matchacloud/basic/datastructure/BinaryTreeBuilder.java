package com.matchacloud.basic.datastructure;

import com.matchacloud.basic.datastructure.pojo.BinaryTreeNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.util.Scanner;

/**
 * 二叉树创建者
 */
@Component
public class BinaryTreeBuilder {

    /**
     * 先序创建二叉树
     * 创建二叉树 数据输入顺序可以用先序顺序，也可以用插入法，如二叉链表二叉排序树的创建
     *
     */
    public BinaryTreeNode createBinaryTreeByPreOrder() {
        BinaryTreeNode thisNode;
        String data = new Scanner(System.in).nextLine();

        if (StringUtils.isEmpty(data) || data.equalsIgnoreCase("N")) {
            thisNode = null;
        } else {
            thisNode = new BinaryTreeNode();
            thisNode.setData(data);
            thisNode.setLeftChild(createBinaryTreeByPreOrder());
            thisNode.setRightChild(createBinaryTreeByPreOrder());
        }
        return thisNode;
    }
}
