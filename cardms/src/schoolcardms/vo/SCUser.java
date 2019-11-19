package schoolcardms.vo;

/**
 * 一卡通用户
 */
public class SCUser {

    private long studentID;//学号(主键)
    private String name;//姓名
    private String password;//一卡通密码
    private long parent_cardID;//父银行卡号
    private double money;//一卡通金额
    private int flag;//一卡通状态标记：0->已注册，1->已挂失(卡已注册)，2->已发送补卡请求

    //private String cardID;//一卡通卡号
    public SCUser() {
        super();
    }

    public SCUser(long studentID, String name, String password, long parent_cardID, double money, int flag) {
        super();
        this.studentID = studentID;
        this.name = name;
        this.password = password;
        this.parent_cardID = parent_cardID;
        this.money = money;
        this.flag = flag;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getParent_cardID() {
        return parent_cardID;
    }

    public void setParent_cardID(long parent_cardID) {
        this.parent_cardID = parent_cardID;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "SCUser{" +
                "studentID=" + studentID +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", parent_cardID=" + parent_cardID +
                ", money=" + money +
                ", flag=" + flag +
                '}';
    }
}
