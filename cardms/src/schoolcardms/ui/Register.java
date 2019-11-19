package schoolcardms.ui;
import schoolcardms.dao.Database;
import schoolcardms.vo.SCUser;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import static package1.Tool.is;
import static package1.Tool.same;

/**
 * 一卡通注册对话框
 */
public class Register extends JDialog implements ActionListener, DocumentListener {

    private JLabel studentID, name, password_st, password_nd, parent_cardID;//学号、姓名、创建密码、确定密码、父银行卡号标签
    private JTextField studentID1, name1, parent_cardID1;//学号、姓名、父银行卡号文本框
    private JPasswordField password_st1, password_nd1;
    private JButton confirm;
    private Database db;

    public Register(JFrame parent, String title, boolean modal, Database db) {
        super(parent, title, modal);
        studentID = new JLabel("学号(8位数字):", JLabel.CENTER);
        studentID1 = new JTextField();
        name = new JLabel("姓名:", JLabel.CENTER);
        name1 = new JTextField();
        password_st = new JLabel("创建密码(6位数字):", JLabel.CENTER);
        password_st1 = new JPasswordField();
        password_nd = new JLabel("确认密码(6位数字):", JLabel.CENTER);
        password_nd1 = new JPasswordField();
        parent_cardID = new JLabel("绑定银行卡(16位数字):", JLabel.CENTER);
        parent_cardID1 = new JTextField("输入父银行卡号");
        confirm = new JButton("确认");
        this.db = db;
        /*设置一卡通注册对话框布局为空布局*/
        this.setLayout(null);
        studentID.setFont(new Font("宋体", 0, 20));//把studentID的字体设置为宋体,不加粗,20号
        studentID.setBounds(75, 20, 140, 50);//设置studentID的位置,宽高
        this.add(studentID);//将studentID添加到当前窗体
        name.setFont(new Font("宋体", 0, 20));
        name.setBounds(165, 80, 50, 50);
        this.add(name);
        password_st.setFont(new Font("宋体", 0, 20));
        password_st.setBounds(35, 140, 180, 50);
        this.add(password_st);
        password_nd.setFont(new Font("宋体", 0, 20));
        password_nd.setBounds(35, 200, 180, 50);
        this.add(password_nd);
        parent_cardID.setFont(new Font("宋体", 0, 20));
        parent_cardID.setBounds(5, 260, 210, 50);
        this.add(parent_cardID);
        studentID1.setFont(new Font("宋体", 0, 20));
        studentID1.setBounds(235, 20, 200, 50);
        studentID1.setBorder(null);
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
        this.add(studentID1);
        name1.setFont(new Font("宋体", 0, 20));
        name1.setBounds(235, 80, 200, 50);
        name1.setBorder(null);
        name1.addMouseListener
                (
                        new MouseAdapter()//接口的实现类类体
                        {
                            public void mouseClicked(MouseEvent arg0) {
                                name1.setText("");
                            }
                        }
                );
        name1.getDocument().addDocumentListener(this);
        this.add(name1);
        parent_cardID1.setFont(new Font("宋体", 0, 20));
        parent_cardID1.setBounds(235, 260, 200, 50);
        parent_cardID1.setBorder(null);
        parent_cardID1.addMouseListener
                (
                        new MouseAdapter()//接口的实现类类体
                        {
                            public void mouseClicked(MouseEvent arg0) {
                                parent_cardID1.setText("");
                            }
                        }
                );
        parent_cardID1.getDocument().addDocumentListener(this);
        this.add(parent_cardID1);
        password_st1.setFont(new Font("宋体", 0, 20));
        password_st1.setBounds(235, 140, 200, 50);
        password_st1.setBorder(null);
        password_st1.addMouseListener
                (
                        new MouseAdapter()//接口的实现类类体
                        {
                            public void mouseClicked(MouseEvent arg0) {
                                password_st1.setText("");
                            }
                        }
                );
        password_st1.getDocument().addDocumentListener(this);
        this.add(password_st1);
        password_nd1.setFont(new Font("宋体", 0, 20));
        password_nd1.setBounds(235, 200, 200, 50);
        password_nd1.setBorder(null);
        password_nd1.addMouseListener
                (
                        new MouseAdapter()//接口的实现类类体
                        {
                            public void mouseClicked(MouseEvent arg0) {
                                password_nd1.setText("");
                            }
                        }
                );
        password_nd1.getDocument().addDocumentListener(this);
        this.add(password_nd1);
        confirm.setFont(new Font("宋体", 0, 20));
        confirm.setBounds(250, 340, 170, 50);
        confirm.setBorder(null);
        confirm.addActionListener(this);
        confirm.setToolTipText("确认注册");
        confirm.setEnabled(false);
        this.add(confirm);

        this.setSize(520, 460);//设置当前窗体界面尺寸
        this.setLocationRelativeTo(null);//将当前窗体界面显示在屏幕中央
        this.setResizable(false);
        this.setVisible(true);//可见
    }

    public boolean check() {
        if (is(studentID1.getText(), 8) && is(String.valueOf(password_st1.getPassword()), 6) && same(String.valueOf(password_st1.getPassword()), String.valueOf(password_nd1.getPassword())) && is(parent_cardID1.getText(), 16) && name1.getText().length() > 0)
            return true;
        return false;
    }

    public void actionPerformed(ActionEvent arg0) {
        long studentid = Long.parseLong(studentID1.getText());
        long parent_cardid = Long.parseLong(parent_cardID1.getText());
        if (arg0.getSource() == confirm) {
            SCUser scuser = new SCUser(studentid, name1.getText(), String.valueOf(password_st1.getPassword()), parent_cardid, 0, 0);
            JOptionPane.showMessageDialog(this, db.add_scuser(scuser), "一卡通注册", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        confirm.setEnabled(this.check());
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        confirm.setEnabled(this.check());
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        confirm.setEnabled(this.check());
    }
}
