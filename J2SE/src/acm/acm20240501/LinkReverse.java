package acm.acm20240501;

import datastructure.data.LinkNode;

/**
 * 单向链表反转
 * 1 头插法 2 利用栈
 *
 * 判断链表存不存在环：
 * 1 快慢指针!!!遍历一个链表 看next是否是已遍历过的节点
 * 2 哈希表
 */
public class LinkReverse {

    /**
     * 创建链表
     *
     * @param nodes 链表节点 如1(头节点) 2 3
     * @return 链表头节点
     */
    public static LinkNode createLink(int... nodes) {
        if(nodes==null ||nodes.length==0) return null;
        LinkNode head = new LinkNode(nodes[0]);
        LinkNode current = head;
        for (int i = 1; i < nodes.length; i++) {
            LinkNode newNode = new LinkNode(nodes[i]);
            current.next = newNode;
            current = current.next;
        }
        System.out.println("创建链表成功!");
        return head;
    }

    /**
     * 显示链表
     * @param head
     */
    public static void displayLink(LinkNode head) {
        LinkNode current = head;
        while (current != null) {
            System.out.print(current.data + "\t");
            current = current.next;
        }
    }

    /**
     * 头插法反转链表
     * @param head 链表头节点
     * @return 新链表的头节点
     */
    public static LinkNode reverseLink(LinkNode head) {
        // 头节点为空或者只有一个节点，则直接返回
        if (head == null || head.next == null) {

            return head;
        }
        //基准位置索引
        LinkNode current = head;
        //新的头节点
        LinkNode newHead = head;
        //拆下来的节点 头插到链表头部
        LinkNode newNode = null;
        while (current.next != null) {
            newNode = current.next;
            current.next = current.next.next;
            newNode.next = newHead;
            newHead = newNode;
        }
        return newHead;
    }
    public static void main(String[] args) {
//        displayLink(createLink(1, 2, 3, 4, 5));
        displayLink(reverseLink(createLink(1, 2, 3, 4)));


    }
}
