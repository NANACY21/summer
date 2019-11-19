package base.thread.pc;

/**
 * 生产者 用于生产数据，入栈
 * <p>
 * 这里确保stack不被其它方法修改，但这不影响同步
 */
public class Producer extends Thread {

    private Stack stack;
    private boolean flag;

    public Producer(Stack stack) {
        this.stack = stack;
        this.flag = false;
    }

    @Override
    public void run() {
        while (!this.flag) {
            stack.push((int) (Math.random() * 100) + "");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (stack.getSize() == 5) {//可以改值
                this.myStop();
            }
        }
    }

    public void myStop() {
        this.flag = true;
    }
}
