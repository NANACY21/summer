package com.matchacloud.basic.thread.pc;

/**
 * 一个栈，不可能同时出栈入栈
 */
public class Test {

    public static void main(String[] args) {
        Stack stack = new Stack();
        Producer producer = new Producer(stack);
        Consumer consumer = new Consumer(stack);
        producer.start();
        consumer.start();
    }
}
