package com.matchacloud.basic.datastructure.pojo;

import lombok.Data;

/**
 * 单向链表节点
 * 单向链表的头节点不存储数据
 */
@Data
public class LinkNode {

    public Integer data;

    public LinkNode next;

    public LinkNode(Integer data) {
        this.data = data;
        this.next = null;
    }
}
