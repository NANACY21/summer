package schoolcardms.ui;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import schoolcardms.dao.Database;
import schoolcardms.service.SCUserService;
import schoolcardms.util.ConstPool;

import static package1.Tool.is;

public class MainUI extends JFrame implements ActionListener, DocumentListener {

    private SCUserService ss = new SCUserService();//依赖
    private Database db;
    private String student_ID;//登录成功者
    /*一级菜单*/
    private JLabel info1;
    private JLabel info1_1;
    private JLabel studentID;//学号标签
    private JTextField studentID1;//学号文本框
    private JLabel password;
    private JPasswordField password1;
    private JButton Calculator;//计算器，供用户使用
    private JButton TextEditor;//文本编辑器，供用户使用
    private JButton login;//登录
    private JButton remove_report;//一卡通解挂
    private JButton exit;//退出
    /*二级菜单*/
    private JLabel info2;
    private JButton get_money;//查询余额
    private JButton transfer_accounts;//父银行卡向一卡通内转钱
    private JButton pay_st;//刷卡
    private JButton change_password;//改一卡通密码
    private JButton get_tr;
    private JButton report;//一卡通挂失
    private JButton fill_card_st;//补卡
    private JButton back_1;//返回
    /*三级菜单*/
    private JLabel info3;
    private JLabel info3_1;
    private JLabel cm_ID;
    private JTextField cm_ID1;
    private JLabel cm_position;
    private JTextField cm_position1;
    private JButton cms_manage;
    private JButton get_cm_tr;
    private JButton add_cm;
    private JButton remove_cm;
    private JButton scusers_manage;
    private JButton get_tr1;//查看一卡通交易记录
    private JButton register;//注册
    private JButton fill_card_nd;//补卡
    private JButton exit0;//退出
    /*四级菜单*/
    private JLabel info4;
    private JLabel choose;
    private JComboBox<String> choose1;
    private JButton fill_card_rd;
    private JButton back_3;
    /*五级菜单*/
    private JLabel info5;
    private JLabel choose2;
    private JComboBox<String> choose3;
    private JButton confirm;
    private JButton get_parent_cardID_money;
    private JButton back_2;
    /*六级菜单*/
    private JLabel info6;
    private JLabel choose4;
    private JComboBox<String> choose5;
    private JTextField money;
    private JButton pay_nd;

    private CardLayout card;//卡片布局对象
    private JPanel menu1_Interface;//一级菜单面板
    private JPanel menu2_Interface;//二级菜单面板
    private JPanel menu3_Interface;//三级菜单面板
    private JPanel menu4_Interface;//四级菜单面板
    private JPanel menu5_Interface;//五级菜单面板
    private JPanel menu6_Interface;//六级菜单面板
    private JPanel menu_Interface;

    public MainUI() {
        super();
        this.db = new Database();
        /*初始化一级菜单*/
        info1 = new JLabel("一卡通管理系统", JLabel.CENTER);
        info1_1 = new JLabel("用户登录/管理员登录/用户解除挂失", JLabel.CENTER);
        studentID = new JLabel("学号(8位数字):", JLabel.CENTER);
        studentID1 = new JTextField();
        password = new JLabel("密码(6位数字):", JLabel.CENTER);
        password1 = new JPasswordField();
        Calculator = new JButton("计算器");
        TextEditor = new JButton("文本编辑器");
        login = new JButton("登录");
        remove_report = new JButton("解除挂失");
        exit = new JButton("退出");


        /*初始化二级菜单*/
        info2 = new JLabel("一卡通自助业务", JLabel.CENTER);
        get_money = new JButton("查询余额");
        transfer_accounts = new JButton("转账");
        pay_st = new JButton("刷卡");
        change_password = new JButton("更改密码");
        get_tr = new JButton("交易记录");
        report = new JButton("挂失");
        fill_card_st = new JButton("补卡");
        back_1 = new JButton("返回");
        /*初始化三级菜单*/
        info3 = new JLabel("管理员模块", JLabel.CENTER);
        info3_1 = new JLabel("移除卡机", JLabel.CENTER);
        cm_ID = new JLabel("卡机ID:", JLabel.CENTER);
        cm_ID1 = new JTextField();
        cm_position = new JLabel("卡机位置:", JLabel.CENTER);
        cm_position1 = new JTextField();
        cms_manage = new JButton("卡机管理");
        get_cm_tr = new JButton("卡机交易记录");
        add_cm = new JButton("添加卡机");
        remove_cm = new JButton("移除卡机");
        scusers_manage = new JButton("一卡通管理");
        get_tr1 = new JButton("一卡通交易记录");
        register = new JButton("注册");
        fill_card_nd = new JButton("补卡");
        exit0 = new JButton("退出");
        /*初始化四级菜单*/
        info4 = new JLabel("补卡", JLabel.CENTER);
        choose = new JLabel("发送补卡请求的学生学号", JLabel.RIGHT);
        this.choose1 = db.get_all_fill_card_studentIDs();//加载出需要补卡的学生
        fill_card_rd = new JButton("给他补卡");
        back_3 = new JButton("返回");
        /*初始化五级菜单*/
        info5 = new JLabel("父银行卡向一卡通内转账", JLabel.CENTER);
        choose2 = new JLabel("选择转账金额:", JLabel.RIGHT);
        choose3 = new JComboBox<String>();
        confirm = new JButton("确认");
        get_parent_cardID_money = new JButton("父银行卡余额");
        back_2 = new JButton("返回");
        /*初始化六级菜单*/
        info6 = new JLabel("刷卡", JLabel.CENTER);
        choose4 = new JLabel("选择卡机:", JLabel.RIGHT);
        this.choose5 = db.get_all_cmIDs1();//加载出卡机
        money = new JTextField("输入刷卡金额");
        pay_nd = new JButton("刷卡");

        card = new CardLayout();//初始化卡片布局对象
        menu1_Interface = new JPanel();//初始化一级菜单面板
        menu2_Interface = new JPanel();//初始化二级菜单面板
        menu3_Interface = new JPanel();//初始化三级菜单面板
        menu4_Interface = new JPanel();//初始化四级菜单面板
        menu5_Interface = new JPanel();//初始化五级菜单面板
        menu6_Interface = new JPanel();//初始化六级菜单面板
        menu_Interface = new JPanel(card);

        /*设置一级菜单面板布局为空布局*/
        menu1_Interface.setLayout(null);
        info1.setFont(new Font("宋体", 0, 40));//把info1的字体设置为宋体,不加粗,40号
        info1.setForeground(Color.BLUE);//把info1的前景色设置为蓝色
        info1.setBounds(300, 60, 400, 40);//设置info1的位置,宽高
        menu1_Interface.add(info1);//将info1添加到欢迎界面
        info1_1.setFont(new Font("宋体", 0, 20));
        info1_1.setBounds(300, 120, 400, 20);
        menu1_Interface.add(info1_1);
        studentID.setFont(new Font("宋体", 0, 20));
        studentID.setBounds(320, 160, 140, 50);
        menu1_Interface.add(studentID);
        studentID1.setFont(new Font("宋体", 0, 20));
        studentID1.setBounds(480, 160, 200, 50);
        studentID1.addMouseListener
                (
                        new MouseAdapter()//接口的实现类类体
                        {
                            public void mouseClicked(MouseEvent arg0) {
                                studentID1.setText("");
                            }
                        }
                );
        studentID1.getDocument().addDocumentListener(this);
        menu1_Interface.add(studentID1);
        password.setFont(new Font("宋体", 0, 20));
        password.setBounds(320, 210, 140, 50);
        menu1_Interface.add(password);
        password1.setFont(new Font("宋体", 0, 20));
        password1.setBounds(480, 210, 200, 50);
        password1.addMouseListener
                (
                        new MouseAdapter()//接口的实现类类体
                        {
                            public void mouseClicked(MouseEvent arg0) {
                                password1.setText("");
                            }
                        }
                );
        password1.getDocument().addDocumentListener(this);
        menu1_Interface.add(password1);
        Calculator.setFont(new Font("宋体", 0, 20));//把Calculator的字体设置为宋体,不加粗,20号
        Calculator.setBounds(235, 290, 170, 50);//设置Calculator的位置,宽高
        Calculator.setBorder(null);
        Calculator.addActionListener(this);//按钮事件源获得监视器的方法
        Calculator.setToolTipText("计算器");
        menu1_Interface.add(Calculator);//将Calculator按钮组件添加到一级菜单面板
        TextEditor.setFont(new Font("宋体", 0, 20));
        TextEditor.setBounds(415, 290, 170, 50);
        TextEditor.setBorder(null);
        TextEditor.addActionListener(this);
        TextEditor.setToolTipText("文本编辑器");
        menu1_Interface.add(TextEditor);
        login.setFont(new Font("宋体", 0, 20));
        login.setBounds(235, 350, 170, 50);
        login.setBorder(null);
        login.addActionListener(this);//按钮事件源获得监视器的方法
        login.setToolTipText("用户登录/管理员登录");
        login.setEnabled(false);
        menu1_Interface.add(login);
        remove_report.setFont(new Font("宋体", 0, 20));
        remove_report.setBounds(415, 350, 170, 50);
        remove_report.setBorder(null);
        remove_report.addActionListener(this);
        remove_report.setToolTipText("用户解除挂失");
        remove_report.setEnabled(false);
        menu1_Interface.add(remove_report);
        exit.setFont(new Font("宋体", 0, 20));
        exit.setBounds(595, 350, 170, 50);
        exit.setBorder(null);
        exit.addActionListener(this);
        exit.setToolTipText("退出一卡通管理系统");
        menu1_Interface.add(exit);


        /*设置二级菜单面板布局为空布局*/
        menu2_Interface.setLayout(null);
        info2.setFont(new Font("宋体", 0, 40));//把title的字体设置为宋体,不加粗,40号
        info2.setForeground(Color.BLUE);//把title的前景色设置为蓝色
        info2.setBounds(300, 60, 400, 40);//设置title的位置,宽高
        menu2_Interface.add(info2);//将title添加到欢迎界面
        get_money.setFont(new Font("宋体", 0, 20));
        get_money.setBounds(145, 290, 170, 50);
        get_money.setBorder(null);
        get_money.addActionListener(this);
        get_money.setToolTipText("查询你的一卡通余额");
        menu2_Interface.add(get_money);
        transfer_accounts.setFont(new Font("宋体", 0, 20));
        transfer_accounts.setBounds(325, 290, 170, 50);
        transfer_accounts.setBorder(null);
        transfer_accounts.addActionListener(this);
        transfer_accounts.setToolTipText("你的父银行卡向你的一卡通内转账");
        menu2_Interface.add(transfer_accounts);
        pay_st.setFont(new Font("宋体", 0, 20));
        pay_st.setBounds(505, 290, 170, 50);
        pay_st.setBorder(null);
        pay_st.addActionListener(this);
        pay_st.setToolTipText("刷卡");
        menu2_Interface.add(pay_st);
        change_password.setFont(new Font("宋体", 0, 20));
        change_password.setBounds(685, 290, 170, 50);
        change_password.setBorder(null);
        change_password.addActionListener(this);
        change_password.setToolTipText("更改你的一卡通密码");
        menu2_Interface.add(change_password);
        get_tr.setFont(new Font("宋体", 0, 20));
        get_tr.setBounds(145, 350, 170, 50);
        get_tr.setBorder(null);
        get_tr.addActionListener(this);
        get_tr.setToolTipText("查询你的一卡通交易记录");
        menu2_Interface.add(get_tr);
        report.setFont(new Font("宋体", 0, 20));
        report.setBounds(325, 350, 170, 50);
        report.setBorder(null);
        report.addActionListener(this);
        report.setToolTipText("挂失你的一卡通");
        menu2_Interface.add(report);
        fill_card_st.setFont(new Font("宋体", 0, 20));
        fill_card_st.setBounds(505, 350, 170, 50);
        fill_card_st.setBorder(null);
        fill_card_st.addActionListener(this);
        fill_card_st.setToolTipText("仅在你的一卡通丢失后点击此按钮以补办一卡通(慎点)");
        menu2_Interface.add(fill_card_st);
        back_1.setFont(new Font("宋体", 0, 20));
        back_1.setBounds(685, 350, 170, 50);
        back_1.setBorder(null);
        back_1.addActionListener(this);
        back_1.setToolTipText("返回上一级");
        menu2_Interface.add(back_1);

        /*设置三级菜单面板布局为空布局*/
        menu3_Interface.setLayout(null);
        info3.setFont(new Font("宋体", 0, 40));
        info3.setForeground(Color.BLUE);
        info3.setBounds(300, 60, 400, 40);
        menu3_Interface.add(info3);
        info3_1.setFont(new Font("宋体", 0, 20));
        info3_1.setBounds(660, 120, 190, 20);
        menu3_Interface.add(info3_1);
        cm_ID.setFont(new Font("宋体", 0, 20));
        cm_ID.setBounds(565, 160, 70, 50);
        menu3_Interface.add(cm_ID);
        cm_ID1.setFont(new Font("宋体", 0, 20));
        cm_ID1.setBounds(655, 160, 200, 50);
        cm_ID1.addMouseListener
                (
                        new MouseAdapter()//接口的实现类类体
                        {
                            public void mouseClicked(MouseEvent arg0) {
                                cm_ID1.setText("");
                            }
                        }
                );
        menu3_Interface.add(cm_ID1);
        cm_position.setFont(new Font("宋体", 0, 20));
        cm_position.setBounds(545, 210, 90, 50);
        menu3_Interface.add(cm_position);
        cm_position1.setFont(new Font("宋体", 0, 20));
        cm_position1.setBounds(655, 210, 200, 50);
        cm_position1.addMouseListener
                (
                        new MouseAdapter()//接口的实现类类体
                        {
                            public void mouseClicked(MouseEvent arg0) {
                                cm_position1.setText("");
                            }
                        }
                );
        menu3_Interface.add(cm_position1);
        cms_manage.setFont(new Font("宋体", 0, 20));
        cms_manage.setBounds(145, 290, 170, 50);
        cms_manage.setBorder(null);
        cms_manage.addActionListener(this);
        cms_manage.setToolTipText("显示该一卡通管理系统所有卡机信息");
        menu3_Interface.add(cms_manage);
        get_cm_tr.setFont(new Font("宋体", 0, 20));
        get_cm_tr.setBounds(325, 290, 170, 50);
        get_cm_tr.setBorder(null);
        get_cm_tr.addActionListener(this);
        get_cm_tr.setToolTipText("查看该一卡通管理系统所有卡机的交易记录");
        menu3_Interface.add(get_cm_tr);
        add_cm.setFont(new Font("宋体", 0, 20));
        add_cm.setBounds(505, 290, 170, 50);
        add_cm.setBorder(null);
        add_cm.addActionListener(this);
        add_cm.setToolTipText("输入卡机位置以添加新卡机");
        menu3_Interface.add(add_cm);
        remove_cm.setFont(new Font("宋体", 0, 20));
        remove_cm.setBounds(685, 290, 170, 50);
        remove_cm.setBorder(null);
        remove_cm.addActionListener(this);
        remove_cm.setToolTipText("输入卡机ID及其位置以移除卡机");
        menu3_Interface.add(remove_cm);
        scusers_manage.setFont(new Font("宋体", 0, 20));
        scusers_manage.setBounds(145, 350, 170, 50);
        scusers_manage.setBorder(null);
        scusers_manage.addActionListener(this);
        scusers_manage.setToolTipText("显示该一卡通管理系统所有一卡通信息");
        menu3_Interface.add(scusers_manage);
        get_tr1.setFont(new Font("宋体", 0, 20));
        get_tr1.setBounds(325, 350, 170, 50);
        get_tr1.setBorder(null);
        get_tr1.addActionListener(this);
        get_tr1.setToolTipText("查看该一卡通管理系统所有一卡通的交易记录");
        menu3_Interface.add(get_tr1);
        register.setFont(new Font("宋体", 0, 20));
        register.setBounds(505, 350, 170, 50);
        register.setBorder(null);
        register.addActionListener(this);
        register.setToolTipText("注册一张新的一卡通");
        menu3_Interface.add(register);
        fill_card_nd.setFont(new Font("宋体", 0, 20));
        fill_card_nd.setBounds(685, 350, 170, 50);
        fill_card_nd.setBorder(null);
        fill_card_nd.addActionListener(this);
        fill_card_nd.setToolTipText("补卡");
        menu3_Interface.add(fill_card_nd);
        exit0.setFont(new Font("宋体", 0, 20));
        exit0.setBounds(685, 410, 170, 50);
        exit0.setBorder(null);
        exit0.addActionListener(this);
        exit0.setToolTipText("退出一卡通管理系统");
        menu3_Interface.add(exit0);

        /*设置四级菜单面板布局为空布局*/
        menu4_Interface.setLayout(null);
        info4.setFont(new Font("宋体", 0, 40));
        info4.setForeground(Color.BLUE);
        info4.setBounds(300, 60, 400, 40);
        menu4_Interface.add(info4);
        choose.setFont(new Font("宋体", 0, 20));
        choose.setBounds(160, 160, 220, 50);
        menu4_Interface.add(choose);
        choose1.setFont(new Font("宋体", 0, 20));
        choose1.setBounds(400, 160, 200, 50);
        menu4_Interface.add(choose1);
        fill_card_rd.setFont(new Font("宋体", 0, 20));
        fill_card_rd.setBounds(620, 160, 170, 50);
        fill_card_rd.setBorder(null);
        fill_card_rd.addActionListener(this);
        fill_card_rd.setToolTipText("给他补卡");
        menu4_Interface.add(fill_card_rd);
        back_3.setFont(new Font("宋体", 0, 20));
        back_3.setBounds(620, 230, 170, 50);
        back_3.setBorder(null);
        back_3.addActionListener(this);
        back_3.setToolTipText("返回上一级");
        menu4_Interface.add(back_3);

        /*设置五级菜单面板布局为空布局*/
        menu5_Interface.setLayout(null);
        info5.setFont(new Font("宋体", 0, 40));
        info5.setForeground(Color.BLUE);
        info5.setBounds(200, 60, 600, 40);
        menu5_Interface.add(info5);
        choose2.setFont(new Font("宋体", 0, 20));
        choose2.setBounds(160, 160, 220, 50);
        menu5_Interface.add(choose2);
        choose3.setFont(new Font("宋体", 0, 20));
        choose3.setBounds(400, 160, 200, 50);
        choose3.addItem(" 50元人民币");
        choose3.addItem("100元人民币");
        choose3.addItem("150元人民币");
        choose3.addItem("200元人民币");
        choose3.addItem("250元人民币");
        choose3.addItem("300元人民币");
        choose3.addItem("350元人民币");
        choose3.addItem("400元人民币");
        choose3.addItem("450元人民币");
        choose3.addItem("500元人民币");
        menu5_Interface.add(choose3);
        confirm.setFont(new Font("宋体", 0, 20));
        confirm.setBounds(620, 160, 170, 50);
        confirm.setBorder(null);
        confirm.addActionListener(this);
        confirm.setToolTipText("确认转账");
        menu5_Interface.add(confirm);
        get_parent_cardID_money.setFont(new Font("宋体", 0, 20));
        get_parent_cardID_money.setBounds(620, 230, 170, 50);
        get_parent_cardID_money.setBorder(null);
        get_parent_cardID_money.addActionListener(this);
        get_parent_cardID_money.setToolTipText("查询你绑定的银行卡的余额");
        menu5_Interface.add(get_parent_cardID_money);
        back_2.setFont(new Font("宋体", 0, 20));
        back_2.setBounds(620, 300, 170, 50);
        back_2.setBorder(null);
        back_2.addActionListener(this);
        back_2.setToolTipText("返回上一级");
        menu5_Interface.add(back_2);

        /*设置六级菜单面板布局为空布局*/
        menu6_Interface.setLayout(null);
        info6.setFont(new Font("宋体", 0, 40));
        info6.setForeground(Color.BLUE);
        info6.setBounds(300, 60, 400, 40);
        menu6_Interface.add(info6);
        choose4.setFont(new Font("宋体", 0, 20));
        choose4.setBounds(290, 160, 90, 50);
        menu6_Interface.add(choose4);
        choose5.setFont(new Font("宋体", 0, 20));
        choose5.setBounds(400, 160, 200, 50);
        menu6_Interface.add(choose5);
        money.setFont(new Font("宋体", 0, 20));
        money.setBounds(620, 160, 200, 50);
        money.setBorder(null);
        money.addMouseListener
                (
                        new MouseAdapter()//接口的实现类类体
                        {
                            public void mouseClicked(MouseEvent arg0) {
                                money.setText("");
                            }
                        }
                );
        menu6_Interface.add(money);
        pay_nd.setFont(new Font("宋体", 0, 20));
        pay_nd.setBounds(635, 230, 170, 50);
        pay_nd.setBorder(null);
        pay_nd.addActionListener(this);
        pay_nd.setToolTipText("刷卡");
        menu6_Interface.add(pay_nd);

        this.set_background_picture();
        menu_Interface.add(menu1_Interface, "1");
        menu_Interface.add(menu2_Interface, "2");
        menu_Interface.add(menu3_Interface, "3");
        menu_Interface.add(menu4_Interface, "4");
        menu_Interface.add(menu5_Interface, "5");
        menu_Interface.add(menu6_Interface, "6");
        this.getContentPane().add(menu_Interface);
        //this.setUndecorated(true);
        //this.getGraphicsConfiguration().getDevice().setFullScreenWindow(this);

        //AWTUtilities.setWindowOpacity(this,0.7f);
        //this.setLocationRelativeTo(null);
        this.addWindowListener
                (
                        new WindowAdapter() {
                            public void windowClosing(WindowEvent e) {
                                db.closeConnection();
                                System.exit(0);
                            }
                        }
                );
        this.setSize(1000, 500);//设置欢迎界面尺寸
        this.setLocationRelativeTo(null);//将欢迎界面显示在屏幕中央
        this.setResizable(false);
        this.setVisible(true);//可见
    }

    public void set_background_picture() {
        ((JPanel) this.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon(ConstPool.imgPath);
        JLabel background = new JLabel(img);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        this.menu1_Interface.setOpaque(false);
        this.menu2_Interface.setOpaque(false);
        this.menu3_Interface.setOpaque(false);
        this.menu4_Interface.setOpaque(false);
        this.menu5_Interface.setOpaque(false);
        this.menu6_Interface.setOpaque(false);
        this.menu_Interface.setOpaque(false);
    }

    public boolean check1() {
        if ((studentID1.getText().length() == 0 && String.valueOf(password1.getPassword()).equals("zxcvbnm2018")) || (is(studentID1.getText(), 8) && is(String.valueOf(password1.getPassword()), 6)))
            return true;
        return false;
    }

    public boolean check2() {
        if (is(studentID1.getText(), 8) && is(String.valueOf(password1.getPassword()), 6))
            return true;
        return false;
    }

    /*按钮返回事件源*/
    public void actionPerformed(ActionEvent arg0) {
        /*一级菜单*/

        if (arg0.getSource() == Calculator)/*点击计算器按钮*/
            new Calculator();
        if (arg0.getSource() == TextEditor)/*点击文本编辑器按钮*/
            new TextEditor();
        if (arg0.getSource() == login)/*点击登录按钮*/ {
            if (studentID1.getText().equals("") && String.valueOf(password1.getPassword()).equals("zxcvbnm2018"))
                this.card.show(menu_Interface, "3");
            else {
                if (db.scuser_login(studentID1.getText(), String.valueOf(password1.getPassword())).equals("登录成功")) {
                    this.student_ID = studentID1.getText();
                    card.next(menu_Interface);
                } else
                    JOptionPane.showMessageDialog(this, db.scuser_login(studentID1.getText(), String.valueOf(password1.getPassword())), "登录", JOptionPane.ERROR_MESSAGE);
            }
            this.studentID1.setText("");
            this.password1.setText("");
        }
        if (arg0.getSource() == remove_report)/*点击解除挂失按钮*/ {
            JOptionPane.showMessageDialog(this, db.scuser_remove_report(studentID1.getText(), String.valueOf(password1.getPassword())), "解除挂失", JOptionPane.INFORMATION_MESSAGE);
            this.studentID1.setText("");
            this.password1.setText("");
        }
        if (arg0.getSource() == exit)/*点击退出按钮*/ {
            db.closeConnection();
            System.exit(0);
        }
        /*二级菜单*/
        if (arg0.getSource() == get_money)/*点击查询余额按钮*/
            JOptionPane.showMessageDialog(this, "你的一卡通余额：￥" + db.get_money(student_ID), "查询余额", JOptionPane.INFORMATION_MESSAGE);
        if (arg0.getSource() == transfer_accounts)/*点击转账按钮*/
            card.show(menu_Interface, "5");
        if (arg0.getSource() == pay_st)/*点击刷卡按钮*/
            card.show(menu_Interface, "6");
        if (arg0.getSource() == change_password)/*点击更改密码按钮*/ {
            //JOptionPane类集中 见JDK API
            //JOptionPane.showMessageDialog(this,"解除挂失成功");
            String new_password = JOptionPane.showInputDialog(this, "创建新6位数密码", "更改密码", JOptionPane.PLAIN_MESSAGE);
            if (new_password == null) {
            } else
                JOptionPane.showMessageDialog(this, db.change_password(student_ID, new_password), "更改密码", JOptionPane.INFORMATION_MESSAGE);
        }
        if (arg0.getSource() == get_tr)/*点击交易记录按钮*/
            new TR(this, "一卡通交易记录", true, db.get_scuser_tr(student_ID));
        if (arg0.getSource() == report)/*点击挂失按钮*/ {
            JOptionPane.showMessageDialog(this, db.scuser_report(student_ID), "挂失", JOptionPane.INFORMATION_MESSAGE);
            card.show(menu_Interface, "1");
        }
        if (arg0.getSource() == fill_card_st)/*点击补卡按钮*/ {
            this.choose1.addItem(student_ID);//增完
            JOptionPane.showMessageDialog(this, db.fill_card_st(student_ID), "补卡", JOptionPane.INFORMATION_MESSAGE);
            card.show(menu_Interface, "1");
        }
        if (arg0.getSource() == back_1)/*点击返回按钮*/
            card.show(menu_Interface, "1");
        /*三级菜单*/
        if (arg0.getSource() == cms_manage)/*点击卡机管理按钮*/
            new TR(this, "所有卡机", true, db.get_all_cm());
        if (arg0.getSource() == get_cm_tr)/*点击卡机交易记录按钮*/ {
            if (db.get_all_cmIDs() == null)
                JOptionPane.showMessageDialog(this, "无卡机", "", JOptionPane.INFORMATION_MESSAGE);
            else {
                String str = (String) JOptionPane.showInputDialog(this, "选择一个卡机以查询其交易记录", "", JOptionPane.PLAIN_MESSAGE, null, db.get_all_cmIDs(), db.get_all_cmIDs()[0]);
                if (str == null) {
                } else
                    new TR(this, "卡机交易记录", true, db.get_cm_tr(str));
            }
        }
        if (arg0.getSource() == add_cm)/*点击添加卡机按钮*/ {
            //自增不用手动输入卡机ID
            String position = JOptionPane.showInputDialog(this, "输入新卡机位置", "添加新卡机", JOptionPane.PLAIN_MESSAGE);
            if (position == null) {
            } else
                JOptionPane.showMessageDialog(this, db.add_cm(position, 0), "添加卡机", JOptionPane.INFORMATION_MESSAGE);
            this.cm_ID1.setText("");
            this.cm_position1.setText("");
        }
        if (arg0.getSource() == remove_cm)/*点击移除卡机按钮*/ {
            if (this.cm_ID1.getText().equals("") || this.cm_position1.getText().equals("")) {
            } else
                JOptionPane.showMessageDialog(this, db.remove_cm_through_ID(this.cm_ID1.getText(), this.cm_position1.getText()), "移除卡机", JOptionPane.INFORMATION_MESSAGE);
            this.cm_ID1.setText("");
            this.cm_position1.setText("");
        }
        if (arg0.getSource() == scusers_manage)/*点击一卡通管理按钮*/
            new TR(this, "所有一卡通", true, db.get_all_scuser());
        if (arg0.getSource() == get_tr1)/*点击一卡通交易记录按钮*/ {
            if (db.get_all_studentIDs() == null)
                JOptionPane.showMessageDialog(this, "无一卡通", "", JOptionPane.INFORMATION_MESSAGE);
            else {
                String str = (String) JOptionPane.showInputDialog(this, "选择一个学生学号以查询其一卡通交易记录", "", JOptionPane.PLAIN_MESSAGE, null, db.get_all_studentIDs(), db.get_all_studentIDs()[0]);
                if (str == null) {
                } else
                    new TR(this, "一卡通交易记录", true, db.get_scuser_tr(str));
            }
        }
        if (arg0.getSource() == register)/*点击注册按钮*/
            new Register(this, "一卡通注册", true, this.db);
        if (arg0.getSource() == fill_card_nd)/*点击补卡按钮*/
            card.show(menu_Interface, "4");
        if (arg0.getSource() == exit0)/*点击退出按钮*/ {
            db.closeConnection();
            System.exit(0);
        }
        /*四级菜单*/
        if (arg0.getSource() == fill_card_rd)/*点击给他补卡按钮*/ {
            if (this.choose1.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "暂无发送补卡请求的学生", "补卡", JOptionPane.INFORMATION_MESSAGE);
                card.show(menu_Interface, "3");
            } else {
                String str = (String) this.choose1.getSelectedItem();
                this.choose1.removeItemAt(this.choose1.getSelectedIndex());//减完
                JOptionPane.showMessageDialog(this, db.fill_card_rd(str), "补卡", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (arg0.getSource() == back_3)/*点击返回按钮*/
            card.show(menu_Interface, "3");
        /*五级菜单*/
        if (arg0.getSource() == confirm)/*点击确认按钮*/ {
            double money = (this.choose3.getSelectedIndex() + 1) * 50;//转账金额
            JOptionPane.showMessageDialog(this, db.transfer_accounts(student_ID, money), "转账", JOptionPane.INFORMATION_MESSAGE);
            card.show(menu_Interface, "2");
        }
        if (arg0.getSource() == get_parent_cardID_money)/*点击父银行卡余额按钮*/
            JOptionPane.showMessageDialog(this, "你的父银行卡余额：￥" + db.get_baba_money(student_ID), "父银行卡余额", JOptionPane.INFORMATION_MESSAGE);
        if (arg0.getSource() == back_2)/*点击返回按钮*/
            card.show(menu_Interface, "2");
        /*六级菜单*/
        if (arg0.getSource() == pay_nd)/*点击刷卡按钮*/ {
            if (this.choose5.getItemCount() == 0)
                JOptionPane.showMessageDialog(this, "无效的操作", "刷卡", JOptionPane.ERROR_MESSAGE);
            else {
                String cm = (String) this.choose5.getSelectedItem();
                JOptionPane.showMessageDialog(this, db.pay(student_ID, cm, money.getText()), "刷卡", JOptionPane.INFORMATION_MESSAGE);
            }
            this.money.setText("输入刷卡金额");
            card.show(menu_Interface, "2");
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        login.setEnabled(this.check1());
        remove_report.setEnabled(this.check2());
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        login.setEnabled(this.check1());
        remove_report.setEnabled(this.check2());
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        login.setEnabled(this.check1());
        remove_report.setEnabled(this.check2());
    }

    //工具类
    public String input(String str) {
        System.out.println(str);
        return new Scanner(System.in).next();
    }

    //在本类构造器中调用
    public void inputSCUser() {
        String studentName = input("请输入学生姓名:");
        String money = input("请输入一卡通金额:");
        ss.save(studentName, Double.parseDouble(money));
        ss.show();
    }
}
