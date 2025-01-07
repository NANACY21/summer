package com.matchacloud.basic.thread.pc;

/**
 * 一个栈，不可能同时出栈入栈
 */
public class Test {

    public static void main(String[] args) {
        Stack stack = new Stack();//一个实例
        Producer p = new Producer(stack);
        Consumer c = new Consumer(stack);
        p.start();
        c.start();
    }
}
