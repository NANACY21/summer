package schoolcardms.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static package1.Tool.is;
import static package1.Tool.same;

/**
 * 银行卡（账户）激活对话框
 */
public class AccountActivation extends JDialog implements ActionListener, DocumentListener {

    /*
    银行卡号、姓名、初始密码、创建密码、确认密码
     */
    private JLabel jl_cardID, jl_name, jl_pw_initial, jl_pw_st, jl_pw_nd;
    /*
    银行卡号、姓名
     */
    private JTextField jtf_cardID, jtf_name;
    /*
    初始密码、创建密码、确认密码
     */
    private JPasswordField jpf_pw_initial, jpf_pw_st, jpf_pw_nd;
    /*
    确定
     */
    private JButton confirm;

    /**
     * @param parent 父窗体对象
     * @param title
     * @param modal  true
     */
    public AccountActivation(JFrame parent, String title, boolean modal) {
        super(parent, title, modal);
        jl_cardID = new JLabel("银行卡号(16位数字):", JLabel.CENTER);
        jl_name = new JLabel("姓名:", JLabel.CENTER);
        jl_pw_st = new JLabel("创建密码(6位数字):", JLabel.CENTER);
        jl_pw_nd = new JLabel("确认密码(6位数字):", JLabel.CENTER);
        jtf_cardID = new JTextField();
        jtf_name = new JTextField();
        jpf_pw_st = new JPasswordField();
        jpf_pw_nd = new JPasswordField();
        confirm = new JButton("确认");
        /*设置帐号激活对话框布局为空布局*/
        this.setLayout(null);
        jl_cardID.setFont(new Font("宋体", 0, 20));//把CardID_的字体设置为宋体,不加粗,20号
        jl_cardID.setBounds(5, 20, 190, 50); //设置CardID_的位置,宽高
        this.add(jl_cardID);//将CardID_添加到当前窗体
        jl_name.setFont(new Font("宋体", 0, 20));
        jl_name.setBounds(145, 80, 50, 50);
        this.add(jl_name);
        jl_pw_st.setFont(new Font("宋体", 0, 20));
        jl_pw_st.setBounds(15, 140, 180, 50);
        this.add(jl_pw_st);
        jl_pw_nd.setFont(new Font("宋体", 0, 20));
        jl_pw_nd.setBounds(15, 200, 180, 50);
        this.add(jl_pw_nd);
        jtf_cardID.setFont(new Font("宋体", 0, 20));
        jtf_cardID.setBounds(215, 20, 200, 50);
        jtf_cardID.setBorder(null);
        jtf_cardID.addMouseListener
                (
                        new MouseAdapter()//接口的实现类类体
                        {
                            public void mouseClicked(MouseEvent arg0) {
                                jtf_cardID.setText("");
                            }
                        }
                );
        jtf_cardID.getDocument().addDocumentListener(this);
        this.add(jtf_cardID);
        jtf_name.setFont(new Font("宋体", 0, 20));
        jtf_name.setBounds(215, 80, 200, 50);
        jtf_name.setBorder(null);
        jtf_name.addMouseListener
                (
                        new MouseAdapter()//接口的实现类类体
                        {
                            public void mouseClicked(MouseEvent arg0) {
                                jtf_name.setText("");
                            }
                        }
                );
        jtf_name.getDocument().addDocumentListener(this);
        this.add(jtf_name);
        jpf_pw_st.setFont(new Font("宋体", 0, 20));
        jpf_pw_st.setBounds(215, 140, 200, 50);
        jpf_pw_st.setBorder(null);
        jpf_pw_st.addMouseListener
                (
                        new MouseAdapter()//接口的实现类类体
                        {
                            public void mouseClicked(MouseEvent arg0) {
                                jpf_pw_st.setText("");
                            }
                        }
                );
        jpf_pw_st.getDocument().addDocumentListener(this);
        this.add(jpf_pw_st);
        jpf_pw_nd.setFont(new Font("宋体", 0, 20));
        jpf_pw_nd.setBounds(215, 200, 200, 50);
        jpf_pw_nd.setBorder(null);
        jpf_pw_nd.addMouseListener
                (
                        new MouseAdapter()//接口的实现类类体
                        {
                            public void mouseClicked(MouseEvent arg0) {
                                jpf_pw_nd.setText("");
                            }
                        }
                );
        jpf_pw_nd.getDocument().addDocumentListener(this);
        this.add(jpf_pw_nd);
        confirm.setFont(new Font("宋体", 0, 20));
        confirm.setBounds(230, 280, 170, 50);
        confirm.setBorder(null);
        confirm.addActionListener(this);
        confirm.setToolTipText("确认激活该银行卡");
        confirm.setEnabled(false);
        this.add(confirm);

        this.setSize(520, 400);//设置当前窗体界面尺寸
        this.setLocationRelativeTo(null);//将当前窗体界面显示在屏幕中央
        this.setResizable(false);
        this.setVisible(true);//可见
    }

    public boolean check() {
        if (is(jtf_cardID.getText(), 16) && is(String.valueOf(jpf_pw_st.getPassword()), 6) && same(String.valueOf(jpf_pw_st.getPassword()), String.valueOf(jpf_pw_nd.getPassword())) && jtf_name.getText().length() > 0)
            return true;
        return false;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {//点击了确定按钮
            this.dispose();//关闭GUI
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
