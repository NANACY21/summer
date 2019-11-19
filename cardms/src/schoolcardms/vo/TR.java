package schoolcardms.vo;

public class TR {

    private int ID;//ID(主键)
    private String time;//时间
    private long studentID;//学号
    private int cmID;//卡机ID
    private double money;//金额
    private int type;//交易类型，0->转账，1->刷卡

    public TR() {
        super();
    }

    public TR(int iD, String time, long studentID, int cm_ID, double money, int type) {
        super();
        ID = iD;
        this.time = time;
        this.studentID = studentID;
        this.cmID = cm_ID;
        this.money = money;
        this.type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public int getCm_ID() {
        return cmID;
    }

    public void setCm_ID(int cm_ID) {
        this.cmID = cm_ID;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
