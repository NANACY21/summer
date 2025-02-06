package com.matchacloud.basic.thread.pc;

import java.util.ArrayList;
import java.util.List;

/**
 * 栈
 * 同步控制
 * 共享对象的锁控制
 */
public class Stack {

    /**
     * 底层数据结构
     */
    private List<String> list = new ArrayList<>();

    /**
     * 初始指针
     */
    private int index = 0;

    public synchronized void push(String element) {
        System.out.println("生产了data:" + element);
        list.add(element);
        this.index++;

        /**
         * 唤醒（释放）锁对象
         * 这会影响同步情况
         */
        this.notify();
    }

    public synchronized String pop() {
        while (list.size() == 0) {
            try {

                /**等待锁对象，等拿到锁对象再执行pop()
                 * 这会影响同步情况
                 */
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        index--;
        String result = list.remove(index);
        System.out.println("取走了data:" + result);
        return result;
    }

    public int getSize() {
        return list.size();
    }
}
