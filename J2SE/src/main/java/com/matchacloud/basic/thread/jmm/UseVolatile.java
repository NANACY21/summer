package thread.jmm;

/**
 * volatile适用场景:读多写少!!!
 * volatile写之前的操作，都禁止重排序到volatile之后
 * volatile读之后的操作，都禁止重排序到volatile之前
 * volatile写之后volatile读，禁止重排序
 */
public class UseVolatile {

    private volatile int race = 0;

    public synchronized void increase() {
        race++;
    }

    public int getRace() {
        return race;
    }
}
