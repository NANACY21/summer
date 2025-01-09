package com.matchacloud.basic.datastructure;
import com.matchacloud.basic.datastructure.pojo.BinaryTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 二叉树遍历者
 */
@Component
public class BinaryTreeTraverser {

    /**
     * 二叉树创建者
     */
    @Autowired
    private BinaryTreeBuilder binaryTreeBuilder;

    /**
     * 递归法二叉树先序遍历
     * 递归注意参数、返回值
     *
     * @param root 二叉树根节点
     */
    public static void preOrderTraversalRecursive(BinaryTreeNode root) {
        System.out.print(root.getData() + "\t");
        if (root.getLeftChild() != null) {
            preOrderTraversalRecursive(root.getLeftChild());
        }
        if (root.getRightChild() != null) {
            preOrderTraversalRecursive(root.getRightChild());
        }
    }

    /**
     * 非递归法二叉树先序遍历
     *
     * @param root 二叉树根节点
     * @return 二叉树先序遍历序列
     */
    public static List<String> preOrderTraversal(BinaryTreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        BinaryTreeNode node = root;
        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
        while (node != null || stack.size() > 0) {//将所有左孩子入栈
            if (node != null) {//压栈之前先访问
                result.add(node.getData());
                stack.push(node);
                node = node.getLeftChild();
            } else {
                node = stack.pop();
                node = node.getRightChild();
            }
        }
        return result;
    }

    /**
     * 递归法中序遍历
     *
     * @param root
     */
    public static void inOrderTraversalRecursive(BinaryTreeNode root) {
        if (root.getLeftChild() != null) {
            inOrderTraversalRecursive(root.getLeftChild());
        }
        System.out.print(root.getData() + "\t");
        if (root.getRightChild() != null) {
            inOrderTraversalRecursive(root.getRightChild());
        }
    }

    /**
     * 非递归中序遍历
     * @return 二叉树中序遍历序列
     */
    public static List<String> inOrderTraversal(BinaryTreeNode root) {
        List<String> result = new ArrayList<>();
        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
        BinaryTreeNode node = root;
        while (node != null || stack.size() > 0) {
            if (node != null) {
                stack.push(node);//直接入栈
                node = node.getLeftChild();
            } else {
                node = stack.pop();//出栈并访问
                result.add(node.getData());
                node = node.getRightChild();
            }
        }
        return result;
    }

    /**
     * 递归法后序遍历
     *
     * @param root
     */
    public static void postOrderTraversalRecursive(BinaryTreeNode root) {
        if (root.getLeftChild() != null) {
            postOrderTraversalRecursive(root.getLeftChild());
        }
        if (root.getRightChild() != null) {
            postOrderTraversalRecursive(root.getRightChild());
        }
        System.out.print(root.getData() + "\t");
    }

    /**
     * 非递归后序遍历
     * @return 二叉树后序遍历序列
     */
    public static List<String> postOrderTraversal(BinaryTreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        BinaryTreeNode node = root;
        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();
        Stack<BinaryTreeNode> output = new Stack<BinaryTreeNode>();//构造一个中间栈来存储逆后序遍历的结果
        while (node != null || stack.size() > 0) {
            if (node != null) {
                output.push(node);
                stack.push(node);
                node = node.getRightChild();
            } else {
                node = stack.pop();
                node = node.getLeftChild();
            }
        }
        while (output.size() > 0) {
            String a = output.pop().getData().toString();
            result.add(a);
        }
        return result;
    }

    /**
     * 层次遍历
     * @param root
     * @return 二叉树层次遍历序列
     */
    public static List<String> layerTraversal(BinaryTreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        LinkedList<BinaryTreeNode> list = new LinkedList<BinaryTreeNode>();
        list.add(root);
        BinaryTreeNode tempNode;
        while (!list.isEmpty()) {
            tempNode = list.poll();
            result.add(tempNode.getData());
            if (tempNode.getLeftChild() != null) {
                list.add(tempNode.getLeftChild());
            }
            if (tempNode.getRightChild() != null) {
                list.add(tempNode.getRightChild());
            }
        }
        return result;
    }

    /**
     * @param root 二叉树
     * @return 该二叉树是不是一颗完全二叉树
     */
    public static boolean isCompleteBinaryTree(BinaryTreeNode root) {
        if (null == root) {
            return false;
        }
        BinaryTreeNode leftChild = null;
        BinaryTreeNode rightChild = null;
        boolean left = false;
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            BinaryTreeNode head = queue.poll();
            leftChild = head.getLeftChild();
            rightChild = head.getRightChild();

            /*右孩子不为空，左孩子为空 -> false
            开启叶节点判断标志位时，如果层次遍历中的后继节点不是叶节点 -> false*/
            if ((null != rightChild && null == leftChild) || (left && (null != rightChild || null != leftChild))) {
                return false;
            }
            if (null != leftChild) {
                queue.offer(leftChild);
            }
            if (null != rightChild) {
                queue.offer(rightChild);
            } else {
                left = true;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        BinaryTreeBuilder builder = new BinaryTreeBuilder();
        System.out.println("先序创建二叉树（输入序列为先序序列，输入'n' --> 空节点）");
        //创建二叉树并返回二叉树根节点
        BinaryTreeNode rootNode = builder.createBinaryTreeByPreOrder();

        System.out.println("二叉树创建成功!");
        //System.out.println(isCompleteBinaryTree(rootNode));
        System.out.println("该二叉树先序遍历：");
        preOrderTraversalRecursive(rootNode);
    }

    //二叉树 节点数31 7个叶子节点 则度为1的节点个数？

}
