package partition1;
import java.util.ArrayList;
import java.util.List;
public class Queue implements SimpleQueue {
    private List<Integer> l = new ArrayList<>();
    private int index  = 0;
    @Override
    public void push(int n) {
        synchronized (this) {
            l.add(n);
            this.index++;
            this.notify();
        }
    }

    @Override
    public int pop() {
        if(l.size()==0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int result = l.remove(0);
        index--;
        return result;
    }
}
