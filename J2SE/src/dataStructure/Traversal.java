package dataStructure;

import dataStructure.data.BinaryTree;
import dataStructure.data.Node;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

/**
 * 遍历算法
 */
public class Traversal {

    /**
     * 递归二叉树先序遍历
     * 递归注意参数、返回值
     *
     * @param root 二叉树根节点
     */
    public static void PreOrderTraversal(Node root) {
        System.out.print(root.getData() + "\t");
        if (root.getLeftChild() != null) {
            PreOrderTraversal(root.getLeftChild());
        }
        if (root.getRightChild() != null) {
            PreOrderTraversal(root.getRightChild());
        }
    }

    /**
     * @param root 二叉树根节点
     * @return 二叉树先序遍历序列
     */
    public static Vector<String> preOrderTraversal(Node root) {
        if (root == null) {
            return new Vector<String>();
        } else {
            Vector<String> v = new Vector<String>();
            Node node = root;
            Stack<Node> stack = new Stack<Node>();
            while (node != null || stack.size() > 0) {//将所有左孩子入栈
                if (node != null) {//压栈之前先访问
                    v.addElement(node.getData());
                    stack.push(node);
                    node = node.getLeftChild();
                } else {
                    node = stack.pop();
                    node = node.getRightChild();
                }
            }
            return v;
        }
    }

    /**
     * 递归中序遍历
     *
     * @param root
     */
    public static void InOrderTraversal(Node root) {
        if (root.getLeftChild() != null) {
            InOrderTraversal(root.getLeftChild());
        }
        System.out.print(root.getData() + "\t");
        if (root.getRightChild() != null) {
            InOrderTraversal(root.getRightChild());
        }
    }

    /**
     * @return 二叉树中序遍历序列
     */
    public static Vector<String> inOrderTraversal(Node root) {
        Vector<String> v = new Vector<String>();
        Stack<Node> stack = new Stack<Node>();
        Node node = root;
        while (node != null || stack.size() > 0) {
            if (node != null) {
                stack.push(node);//直接入栈
                node = node.getLeftChild();
            } else {
                node = stack.pop();//出栈并访问
                v.addElement(node.getData());
                node = node.getRightChild();
            }
        }
        return v;
    }

    /**
     * 递归后序遍历
     *
     * @param root
     */
    public static void PostOrderTraversal(Node root) {
        if (root.getLeftChild() != null) {
            PostOrderTraversal(root.getLeftChild());
        }
        if (root.getRightChild() != null) {
            PostOrderTraversal(root.getRightChild());
        }
        System.out.print(root.getData() + "\t");
    }

    /**
     * @return 二叉树后序遍历序列
     */
    public static Vector<String> postOrderTraversal(Node root) {
        if (root == null) {
            return new Vector<String>();
        } else {
            Vector<String> v = new Vector<String>();
            Node node = root;
            Stack<Node> stack = new Stack<Node>();
            Stack<Node> output = new Stack<Node>();//构造一个中间栈来存储逆后序遍历的结果
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
                String a = output.pop().getData();
                v.addElement(a);
            }
            return v;
        }
    }

    /**
     * @param root
     * @return 二叉树层次遍历序列
     */
    public static Vector<String> layerTraversal(Node root) {
        if (root == null) {
            return new Vector<>();
        }
        Vector<String> v = new Vector<>();
        LinkedList<Node> list = new LinkedList<Node>();
        list.add(root);
        Node tempNode;
        while (!list.isEmpty()) {
            tempNode = list.poll();
            v.addElement(tempNode.getData());
            if (tempNode.getLeftChild() != null) {
                list.add(tempNode.getLeftChild());
            }
            if (tempNode.getRightChild() != null) {
                list.add(tempNode.getRightChild());
            }
        }
        return v;
    }

    /**
     * @param root 二叉树
     * @return 该二叉树是不是一颗完全二叉树
     */
    public static boolean isCBT(Node root) {
        if (null == root) {
            return false;
        }
        Node leftChild = null;
        Node rightChild = null;
        boolean left = false;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node head = queue.poll();
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
        //a b d n n e n n c n f n n
        BinaryTree binaryTree = new BinaryTree();
        System.out.println(isCBT(binaryTree.getRoot()));

    }

    //二叉树 节点数31 7个叶子节点 则度为1的节点个数？

}
