package base.vehicle;

/**
 * 货车
 */
public class Truck implements Vehicle {

    /**
     * 货车停车费12元
     * @return
     */
    @Override
    public int parkingFee() {
        return 12;
    }
}
