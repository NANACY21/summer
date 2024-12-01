package base.vehicle;

/**
 * 车辆模型来体会Java面向对象 万物皆对象
 *
 * 公共汽车、巴士
 */
public class Bus implements Vehicle {

    /**
     * 公共汽车停车费15
     *
     * @return
     */
    @Override
    public int parkingFee() {
        return 15;
    }
}
