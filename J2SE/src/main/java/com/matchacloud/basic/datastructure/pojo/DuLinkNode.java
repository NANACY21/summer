package com.matchacloud.basic.datastructure.pojo;

import lombok.Data;

/**
 * 双向链表节点
 */
@Data
public class DuLinkNode {
    DuLinkNode prev;
    Object data;
    DuLinkNode next;
}
