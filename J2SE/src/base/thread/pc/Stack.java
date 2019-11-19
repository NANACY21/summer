package base.thread.pc;

import java.util.ArrayList;
import java.util.List;

/**
 * 栈
 * 同步控制
 * 共享对象的锁控制
 *
 */
public class Stack {

    private List<String> l = new ArrayList<>();//底层数据结构
    private int index = 0;//初始指针

    public void push(String element) {
        synchronized (this) {
            System.out.println("生产了data:" + element);
            l.add(element);
            this.index++;

            /**
             * 唤醒（释放）锁对象
             * 这会影响同步情况
             */
            this.notify();
        }
    }

    public synchronized String pop() {
        if (l.size() == 0) {
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
        String result = l.remove(index);
        System.out.println("取走了data:" + result);
        return result;
    }

    public int getSize() {
        return l.size();
    }
}
