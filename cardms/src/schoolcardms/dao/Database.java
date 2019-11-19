package schoolcardms.dao;
import schoolcardms.util.ConstPool;
import schoolcardms.vo.CM;
import schoolcardms.vo.SCUser;

import java.util.*;
import javax.swing.JComboBox;
import java.sql.*;

import static package1.Tool.*;

/**
 * dao层
 * 数据访问对象
 */
public class Database {

    public static final String URL = "jdbc:mysql://localhost:3306/schoolcardms?serverTimezone=UTC";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "xy201619";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String INFO1 = "数据库加载成功";
    public static final String INFO2 = "数据库连接成功";
    public static final String INFO3 = "数据库已断开与" + "\"" + "一卡通管理系统" + "\"" + "的连接";
    public static final String INFO4 = "数据库未断开与" + "\"" + "一卡通管理系统" + "\"" + "的连接";
    private Connection conn;//连接
    private PreparedStatement pres;
    private ResultSet res;

    public Database() {
        String url = URL;
        String username = USERNAME;
        String password = PASSWORD;
        try {
            /**
             * Class.forName作用：加载驱动类，并将其初始化，
             * 将其注入到DriverManager中
             * jdbc2.0中，Class.forName不用写了
             */
            Class.forName(DRIVER);
            System.out.println(INFO1);
            conn = DriverManager.getConnection(url, username, password);//建立连接
            System.out.println(INFO2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            this.conn.close();
            if (this.conn.isClosed())
                System.out.println(INFO3);
            else
                System.out.println(INFO4);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    SCUserDatabase部分
     */

    /*一卡通注册*/
    public String add_scuser(SCUser scuser) {
        String sql = "insert into scuser (studentID,name,password,parent_cardID,money,flag) values(?,?,?,?,?,?)";
        try {
            res = (conn.prepareStatement("select * from scuser where studentID='" + scuser.getStudentID() + "'")).executeQuery();
            if (res.next() == true)
                return "该一卡通已经注册";
            res = (conn.prepareStatement("select * from users where cardID='" + scuser.getParent_cardID() + "'")).executeQuery();
            if (res.next() == false || res.getInt(5) != 1)
                return "绑定父银行卡时出错";
            pres = conn.prepareStatement(sql);
            pres.setLong(1, scuser.getStudentID());
            pres.setString(2, scuser.getName());
            pres.setString(3, scuser.getPassword());
            pres.setLong(4, scuser.getParent_cardID());
            pres.setDouble(5, scuser.getMoney());
            pres.setInt(6, scuser.getFlag());
            pres.executeUpdate();
            pres.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "一卡通注册成功";
    }

    /*学生登录*/
    public String scuser_login(String studentID, String password) {
        long studentid = Long.parseLong(studentID);
        try {
            res = (conn.prepareStatement("select * from scuser where studentID='" + studentid + "'")).executeQuery();
            if (res.next() == false)
                return "该一卡通不存在";
            else if (res.getInt(6) != 0)
                return "登录失败";
            else if (res.getString(3).equals(password) == false)
                return "密码错误";
            else
                return "登录成功";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "登录失败";
    }

    /*解挂*/
    public String scuser_remove_report(String studentID, String password) {
        long studentid = Long.parseLong(studentID);
        try {
            res = (conn.prepareStatement("select * from scuser where studentID='" + studentid + "'")).executeQuery();
            if (res.next() && res.getInt(6) == 1 && res.getString(3).equals(password)) {
                pres = conn.prepareStatement("update scuser set flag = ? where studentID='" + studentID + "'");
                pres.setInt(1, 0);
                pres.executeUpdate();
                return "解除挂失成功";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "无效的操作";
    }

    /*查看一卡通余额*/
    public double get_money(String studentID) {
        long studentid = Long.parseLong(studentID);
        try {
            res = (conn.prepareStatement("select * from scuser where studentID='" + studentid + "'")).executeQuery();
            res.next();
            return res.getDouble(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*查看父银行卡余额*/
    public double get_baba_money(String studentID) {
        long studentid = Long.parseLong(studentID);
        try {
            res = (conn.prepareStatement("select * from users where cardID=(select parent_cardID from scuser where studentID='" + studentid + "')")).executeQuery();
            res.next();
            return res.getDouble(4);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*学生刷卡（影响刷的卡机的余额）(产生1条交易记录)，返回刷卡结果*/
    public String pay(String studentID, String cm_ID, String money) {
        if (!transfer(money) || money.equals("") || money.equals("输入刷卡金额"))
            return "无效的操作";
        long studentid = Long.parseLong(studentID);
        try {
            res = (conn.prepareStatement("select * from scuser where studentID='" + studentid + "'")).executeQuery();
            res.next();
            double m = Double.parseDouble(money);//字符串转数值
            if (m <= 0)
                return "无效的操作";
            double yuan = res.getDouble(5);
            if (yuan < m)
                return "你的一卡通余额不足";
            pres = conn.prepareStatement("update scuser set money = ? where studentID='" + studentid + "'");
            pres.setDouble(1, yuan - m);
            pres.executeUpdate();
            String q = "";
            for (int i = 0; i < cm_ID.length(); i++) {
                char c = cm_ID.charAt(i);
                if (c >= '0' && c <= '9')
                    q = q + c;
            }
            int cmid = Integer.parseInt(q);//字符串转数值
            this.add_tr(getTime(), studentid, cmid, m, 1);
            res = (conn.prepareStatement("select * from cm where ID='" + cmid + "'")).executeQuery();
            res.next();
            pres = conn.prepareStatement("update cm set money = ? where ID='" + cmid + "'");
            pres.setDouble(1, res.getDouble(3) + m);
            pres.executeUpdate();
            pres.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return "刷卡成功";
    }

    /**
     * 学生转账(影响父银行卡的钱数)(产生2条交易记录)
     *
     * @param studentID
     * @param money
     * @return 转账结果
     */
    public String transfer_accounts(String studentID, double money) {
        long studentid = Long.parseLong(studentID);
        try {
            res = (conn.prepareStatement("select * from users where cardID=(select parent_cardID from scuser where studentID='" + studentid + "')")).executeQuery();
            res.next();
            double baba_money = res.getDouble(4);
            long baba_cardID = res.getLong(6);
            if (baba_money < money)
                return "转账失败\n你的父银行卡余额不足";
            pres = conn.prepareStatement("update scuser set money = ? where studentID='" + studentid + "'");
            res = (conn.prepareStatement("select * from scuser where studentID='" + studentid + "'")).executeQuery();
            res.next();
            pres.setDouble(1, res.getDouble(5) + money);
            pres.executeUpdate();
            this.add_tr(getTime(), studentid, 0, money, 0);
            int mm = (int) money;
            /*
            相当于执行银行用户取钱操作
             */
            res = (conn.prepareStatement("select * from users where cardID='" + baba_cardID + "'")).executeQuery();
            res.next();//得到父银行卡

            double yuan = res.getDouble(4) - mm;
            pres = conn.prepareStatement("update users set money = ? where cardID='" + baba_cardID + "'");
            pres.setDouble(1, yuan);
            pres.executeUpdate();
            pres = conn.prepareStatement("insert into trs (time,cardID,money,type) values(?,?,?,?)");
            pres.setString(1, getTime());
            pres.setLong(2, baba_cardID);
            pres.setDouble(3, money);
            pres.setInt(4, 1);
            pres.executeUpdate();
            pres.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "转账成功\n转账金额:" + ConstPool.TAB_4 + "￥" + money;
    }

    /*学生更改一卡通密码*/
    public String change_password(String studentID, String newpassword) {
        if (newpassword.length() == 0)
            return "无效的操作";
        if (!is(newpassword, 6))//若新密码不是6位数字
            return "更改一卡通密码失败";
        long studentid = Long.parseLong(studentID);
        try {
            pres = conn.prepareStatement("update scuser set password = ? where studentID='" + studentid + "'");
            pres.setString(1, newpassword);
            pres.executeUpdate();
            pres.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "更改一卡通密码成功";
    }

    /*学生挂失，返回挂失结果*/
    public String scuser_report(String studentID) {
        long studentid = Long.parseLong(studentID);
        try {
            pres = (PreparedStatement) conn.prepareStatement("update scuser set flag = ? where studentID='" + studentid + "'");
            pres.setInt(1, 1);
            pres.executeUpdate();
            pres.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "挂失成功";
    }

    /*学生申请补卡，返回申请补卡结果*/
    public String fill_card_st(String studentID) {
        long studentid = Long.parseLong(studentID);
        try {
            pres = (PreparedStatement) conn.prepareStatement("update scuser set flag = ? where studentID='" + studentid + "'");
            pres.setInt(1, 2);
            pres.executeUpdate();
            pres.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "已发送补卡请求\n请到黑龙江大学主楼D111室领取新卡";
    }

    /*管理员帮学生补卡(即点击给他补卡按钮 触发这个函数)，补卡结果*/
    public String fill_card_rd(String studentID) {
        long studentid = Long.parseLong(studentID);
        try {
            pres = conn.prepareStatement("update scuser set flag = ? where studentID='" + studentid + "'");
            pres.setInt(1, 0);
            pres.executeUpdate();
            pres.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "补卡成功";
    }

    /*通过学号删除其用户*/
    public void remove_scuser_through_studentID(String studentID) {
        long studentid = Long.parseLong(studentID);
        try {
            pres = conn.prepareStatement("delete from scuser where studentID='" + studentid + "'");
            pres.executeUpdate();
            pres.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*返回学生学号集合数组，管理员查所有学生的一卡通交易记录时*/
    public String[] get_all_studentIDs() {
        Vector<String> vec = new Vector<String>();
        try {
            res = conn.prepareStatement("select * from scuser").executeQuery();
            if (res.next() == false) {
            } else {
                res.beforeFirst();
                while (res.next()) {
                    vec.addElement(res.getLong(1) + "");
                }
                String str[] = new String[vec.size()];
                for (int i = 0; i < vec.size(); i++)
                    str[i] = vec.elementAt(i);
                return str;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*返回所有学生*/
    public Vector<String> get_all_scuser() {
        Vector<String> vec = new Vector<String>();
        try {
            res = conn.prepareStatement("select * from scuser").executeQuery();
            if (res.next() == false)
                vec.addElement("无一卡通");
            else {
                res.beforeFirst();
                while (res.next()) {
                    String str = "学号：" + res.getLong(1) + ConstPool.TAB_2 + "姓名：" + res.getString(2) + ConstPool.TAB_2 + "一卡通余额：￥" + res.getDouble(5) + ConstPool.TAB_2 + "一卡通状态：";
                    if (res.getInt(6) == 0)
                        str = str + "已注册";
                    if (res.getInt(6) == 1)
                        str = str + "已挂失";
                    if (res.getInt(6) == 2)
                        str = str + "已发送补卡请求";
                    vec.addElement(str);
                }
                res = conn.prepareStatement("select avg(money) from scuser").executeQuery();
                res.next();
                vec.addElement("平均一卡通余额：" + res.getDouble(1));
                res = conn.prepareStatement("select sum(money) from scuser").executeQuery();
                res.next();
                vec.addElement("总一卡通余额：" + res.getDouble(1));
                res = conn.prepareStatement("select count(*) from scuser").executeQuery();
                res.next();
                vec.addElement("共" + res.getInt(1) + "张一卡通");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vec;
    }

    /*返回请求补卡的学生下拉列表*/
    public JComboBox<String> get_all_fill_card_studentIDs() {
        JComboBox<String> jco = new JComboBox<String>();
        try {
            res = conn.prepareStatement("select * from scuser where flag=2").executeQuery();
            while (res.next()) {
                jco.addItem(res.getLong(1) + "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jco;
    }

    /*
    CMDatabase部分
     */

    /*通过卡机ID查找卡机*/
    public CM get_cm_through_ID(int ID) {
        try {
            res = conn.prepareStatement("select * from cm where ID='" + ID + "'").executeQuery();
            if (res.next()) {
                CM cm = new CM();
                cm.setID(res.getInt(1));
                cm.setPosition(res.getString(2));
                cm.setMoney(res.getDouble(3));
                return cm;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*添加新卡机，返回添加结果*/
    public String add_cm(String position, double money) {
        if (position.length() == 0)
            return "无效的操作，请输入卡机所在位置";
        try {
            pres = conn.prepareStatement("insert into cm (position,money) values(?,?)");
            pres.setString(1, position);
            pres.setDouble(2, money);
            pres.executeUpdate();
            res = conn.prepareStatement("select * from cm").executeQuery();
            res.last();
            pres.close();
            return "已添加新卡机，新卡机ID：" + res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "已添加新卡机，新卡机ID：?";
    }

    /*移除卡机，返回移除结果*/
    public String remove_cm_through_ID(String cm_ID, String position) {
        if (!transfer(cm_ID))//若卡机ID含非数字字符
            return "卡机ID是数字";
        int cmid = Integer.parseInt(cm_ID);//字符串转数值
        try {
            res = conn.prepareStatement("select * from cm where ID = cmid").executeQuery();
            if (res.next() == false)
                return "该卡机不存在";
            if (res.getString(2).equals(position) == false)
                return "卡机位置有误";
            pres = conn.prepareStatement("delete from cm where ID='" + cmid + "'");
            pres.executeUpdate();
            pres.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cm_ID + "号卡机已移除";
    }

    /*返回卡机ID集合数组，管理员查所有卡机的交易记录时*/
    public String[] get_all_cmIDs() {
        Vector<String> vec = new Vector<String>();
        try {
            res = conn.prepareStatement("select * from cm").executeQuery();
            if (res.next() == false) {
            } else {
                res.beforeFirst();
                while (res.next()) {
                    vec.addElement(res.getInt(1) + "号卡机");
                }
                String str[] = new String[vec.size()];
                for (int i = 0; i < vec.size(); i++)
                    str[i] = vec.elementAt(i);
                return str;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JComboBox<String> get_all_cmIDs1()//返回卡机ID集合下拉列表
    {
        JComboBox<String> jco = new JComboBox<String>();
        try {
            res = conn.prepareStatement("select * from cm").executeQuery();
            while (res.next()) {
                jco.addItem(res.getInt(1) + "号卡机");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jco;
    }

    /*返回所有卡机*/
    public Vector<String> get_all_cm() {
        Vector<String> vec = new Vector<String>();
        try {
            res = conn.prepareStatement("select * from cm").executeQuery();
            if (res.next() == false)
                vec.addElement("无卡机");
            else {
                res.beforeFirst();
                while (res.next()) {
                    String str = "卡机ID：" + res.getInt(1) + ConstPool.TAB_2 + "位置：" + res.getString(2) + ConstPool.TAB_2 + "余额：￥" + res.getDouble(3);
                    vec.addElement(str);
                }
                res = conn.prepareStatement("select avg(money) from cm").executeQuery();
                res.next();
                vec.addElement("平均卡机余额：" + res.getDouble(1));
                res = conn.prepareStatement("select sum(money) from cm").executeQuery();
                res.next();
                vec.addElement("总卡机余额：" + res.getDouble(1));
                res = conn.prepareStatement("select count(*) from cm").executeQuery();
                res.next();
                vec.addElement("共" + res.getInt(1) + "台卡机");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vec;
    }

    /*
    TRDatabase部分
     */

    public void add_tr(String time, long studentID, int cm_ID, double money, int type) {
        try {
            if (type == 0) {
                //不传cm_ID
                pres = conn.prepareStatement("insert into schoolcardtr (time,studentID,money,type) values(?,?,?,?)");
                pres.setString(1, time);
                pres.setLong(2, studentID);
                pres.setDouble(3, money);
                pres.setInt(4, type);
            } else {
                pres = conn.prepareStatement("insert into schoolcardtr (time,studentID,cm_ID,money,type) values(?,?,?,?,?)");
                pres.setString(1, time);
                pres.setLong(2, studentID);
                pres.setInt(3, cm_ID);
                pres.setDouble(4, money);
                pres.setInt(5, type);
            }
            pres.executeUpdate();
            pres.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*返回studentID学号的一卡通交易记录*/
    public Vector<String> get_scuser_tr(String studentID) {
        long studentid = Long.parseLong(studentID);
        Vector<String> v = new Vector<String>();
        try {
            res = conn.prepareStatement("select * from schoolcardtr where studentID='" + studentid + "'").executeQuery();
            if (res.next() == false)
                v.addElement("无交易记录");
            else {
                res.beforeFirst();
                while (res.next()) {
                    if (res.getInt(6) == 0)//若是转账
                        v.addElement(res.getString(2) + ConstPool.TAB_4 + "+￥" + res.getDouble(5));
                    else//若是刷卡
                        v.addElement(res.getString(2) + ConstPool.TAB_4 + "在" + res.getInt(4) + "号卡机" + ConstPool.TAB_2 + "刷卡￥" + res.getDouble(5));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    /*返回cm_ID卡机ID的卡机交易记录*/
    public Vector<String> get_cm_tr(String cm_ID) {
        String s = "";
        for (int i = 0; i < cm_ID.length(); i++) {
            char c = cm_ID.charAt(i);
            if (c >= '0' && c <= '9')
                s = s + c;
        }
        int cmid = Integer.parseInt(s);//字符串转数值
        Vector<String> vec = new Vector<String>();
        try {
            res = conn.prepareStatement("select * from schoolcardtr where cm_ID is not null and cm_ID='" + cmid + "'").executeQuery();
            if (res.next() == false)
                vec.addElement("无交易记录");
            else {
                res.beforeFirst();
                while (res.next()) {
                    vec.addElement(res.getString(2) + ConstPool.TAB_2 + res.getLong(3) + "(学号)在该卡机刷卡￥" + res.getDouble(5));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vec;
    }
}
