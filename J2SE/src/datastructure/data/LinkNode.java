package datastructure.data;

/**
 * 单向链表节点
 */
public class LinkNode {

    public int data;
    public LinkNode next;

    public LinkNode(int data) {
        this.data = data;
        this.next = null;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public LinkNode getNext() {
        return next;
    }

    public void setNext(LinkNode next) {
        this.next = next;
    }
}
