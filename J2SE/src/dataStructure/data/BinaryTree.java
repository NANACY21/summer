package dataStructure.data;

import java.util.Scanner;

/**
 * 二叉树
 */
public class BinaryTree {

    private Node root;//二叉树根节点

    public BinaryTree() {
        System.out.println("先序创建二叉树（输入序列为先序序列，输入'n' --> 空节点）");
        this.root = null;
        this.root = this.createBinaryTree(root);
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    /**
     * 创建二叉树
     * 创建二叉树可以用先序遍历，也可以用插入法，如二叉链表二叉排序树的创建
     */
    public Node createBinaryTree(Node node) {
        String NodeData = input();
        if (NodeData.equals("n") || NodeData.equals("N")) {
            node = null;
        } else {
            node = new Node();
            node.setData(NodeData);
            node.setLeftChild(createBinaryTree(node.getLeftChild()));
            node.setRightChild(createBinaryTree(node.getRightChild()));
        }
        return node;
    }

    /**
     * @return 输入的数据
     */
    public static String input() {

        return new Scanner(System.in).nextLine();
    }
}
