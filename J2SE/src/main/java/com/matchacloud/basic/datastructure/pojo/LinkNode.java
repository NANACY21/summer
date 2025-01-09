package com.matchacloud.basic.datastructure.pojo;

import lombok.Data;

/**
 * 单向链表节点
 */
@Data
public class LinkNode {

    public int data;
    public LinkNode next;

    public LinkNode(int data) {
        this.data = data;
        this.next = null;
    }
}
