package inherit.demo3;

/**
 * 客车
 */
public class Bus implements Vehicle{
    @Override
    public int parkingFee() {
        return 15;
    }
}
