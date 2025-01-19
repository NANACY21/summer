package com.matchacloud.basic.datastructure;

import com.matchacloud.basic.datastructure.pojo.LinkNode;

/**
 * 单向链表
 */
public class SingleLinkedList {
    /**
     * 头节点 不存储数据
     */
    LinkNode head;

    /**
     * 单向链表初始化
     */
    SingleLinkedList() {
        head = new LinkNode(null);
    }

    /**
     * 插入节点
     * @param value 插入的节点的数据
     * @param index 插入的节点的下标
     */
    void insert(int value, int index) {
        LinkNode newNode = new LinkNode(value);
        //头节点后面插入新节点
        if (index == 0) {
            newNode.next = head.next;
            head.next = newNode;
        } else {
            LinkNode curr = head;
            for (int i = 0; i < index; i++) {
                if (curr.next!= null) {
                    curr = curr.next;
                } else {
                    throw new IndexOutOfBoundsException("Position out of range");
                }
            }
            newNode.next = curr.next;
            curr.next = newNode;
        }
    }


    /**
     * 删除节点
     * @param index 删除该下标的节点
     */
    void delete(int index) {
        if (index == 0) {
            if (head.next!= null) {
                head.next = head.next.next;
            } else {
                throw new IndexOutOfBoundsException("List is empty");
            }
        } else {
            LinkNode curr = head;
            for (int i = 0; i < index; i++) {
                if (curr.next!= null) {
                    curr = curr.next;
                } else {
                    throw new IndexOutOfBoundsException("Position out of range");
                }
            }
            if (curr.next!= null) {
                curr.next = curr.next.next;
            } else {
                throw new IndexOutOfBoundsException("Position out of range");
            }
        }
    }


    /**
     * 打印单向链表
     */
    void printList() {
        LinkNode curr = head.next;
        StringBuilder sb = new StringBuilder();
        while (curr!= null) {
            sb.append(curr.data).append(" -> ");
            curr = curr.next;
        }
        sb.append("null");
        System.out.println(sb);
    }

    /**
     * 打印单向链表
     * @param linkedList 单向链表
     */
    public static void printList(SingleLinkedList linkedList) {
        LinkNode current = linkedList.head.next;
        while (current != null) {
            System.out.print(current.data + "\t");
            current = current.next;
        }
    }

    /**
     * 创建单向链表
     *
     * @param nodes 链表节点 如null(头节点) 1 2 3
     * @return 单向链表
     */
    public static SingleLinkedList createLinkedList(int... nodes) {
        if (nodes == null || nodes.length == 0) {
            return null;
        }
        SingleLinkedList list = new SingleLinkedList();
        LinkNode current = list.head;
        for (int i = 0; i < nodes.length; i++) {
            LinkNode newNode = new LinkNode(nodes[i]);
            current.next = newNode;
            current = current.next;
        }
        System.out.println("创建单向链表成功!");
        return list;
    }

    /**
     * 单向链表反转：1 头插法 2 利用栈
     *
     * 头插法单向链表反转
     * @param linkedList 单向链表
     * @return 反转之后的新单向链表
     */
    public static SingleLinkedList reverseLink(SingleLinkedList linkedList) {
        if (linkedList == null || linkedList.head.next == null) {
            return linkedList;
        }
        //只有一个节点，则直接返回
        if (linkedList.head.next.next == null) {
            return linkedList;
        }
        SingleLinkedList result = new SingleLinkedList();
        //基准位置索引
        LinkNode current = linkedList.head.next;
        LinkNode newNode = new LinkNode(current.data);
        result.head.next = newNode;
        current = current.next;
        //头插到链表头部
        while (current != null) {
            newNode = new LinkNode(current.data);
            newNode.next = result.head.next;
            result.head.next = newNode;
            current = current.next;
        }
        return result;
    }


    /**
     * 测试单向链表
     * @param args
     */
    public static void main(String[] args) {
        SingleLinkedList list = new SingleLinkedList();
        list.insert(1, 0);
        list.insert(2, 1);
        list.insert(3, 2);
        list.insert(4, 3);
        list.printList();
        list.delete(2);
        list.printList();
//        SingleLinkedList linkedList = SingleLinkedList.createLinkedList(1, 2, 3, 4, 5);
//        SingleLinkedList.printList(linkedList);
//
//        SingleLinkedList reversedLink = SingleLinkedList.reverseLink(linkedList);
//        SingleLinkedList.printList(reversedLink);


    }
}
