package schoolcardms.vo;

/**
 * 卡机
 */
public class CM {

    private int ID;//ID(主键)
    private String position;//位置
    private double money;//余额

    public CM() {
        super();
    }

    public CM(int iD, String position, double money) {
        super();
        ID = iD;
        this.position = position;
        this.money = money;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
