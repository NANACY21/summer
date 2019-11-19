package dataStructure.data;

/**
 * 树节点
 */
public class Node {

    private String data;
    private Node leftChild;
    private Node rightChild;

    public Node(String data, Node leftChild, Node rightChild) {
        this.data = data;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     * 无参构造 成员变量为null
     */
    public Node() {

    }

    public String getData() {
        return data;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }
}