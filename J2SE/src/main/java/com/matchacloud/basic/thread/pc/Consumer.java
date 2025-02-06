package com.matchacloud.basic.thread.pc;

/**
 * 消费者 用于从栈中取数据
 */
public class Consumer extends Thread {

    /**
     * 消费者从这个栈里拿数据
     */
    private Stack stack;

    private boolean flag;

    public Consumer(Stack stack) {
        this.stack = stack;
        this.flag = false;
    }

    @Override
    public void run() {
        while (!flag) {
            stack.pop();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (stack.getSize() == 0) {
                this.myStop();
            }
        }

    }

    public void myStop() {
        flag = true;
    }
}
