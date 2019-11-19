package schoolcardms.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * 构造器参数v内容：
 * 银行卡交易记录 / 一卡通交易记录 / 卡机交易记录 / 所有卡机信息 / 所有一卡通信息
 */
public class TR extends JDialog {

    private JTable tr;
    private JScrollPane jsp;//滚动窗体

    public TR(JFrame jFrame, String title, boolean modal, Vector<String> v) {
        super(jFrame, title, modal);
        tr = new JTable(v.size(), 1);
        tr.getTableHeader().setPreferredSize(new Dimension(1, 0));
        tr.setRowHeight(40);
        tr.setEnabled(false);
        for (int i = 0; i < v.size(); i++) {
            tr.setValueAt(v.elementAt(i), i, 0);
        }
        jsp = new JScrollPane();
        jsp.setViewportView(tr);//把表格放到滚动窗体里，<=>将tr设置为构造器参数
        this.add(jsp);//把jsp放到this里，<=>this.getContentPane().add(jsp);
        this.setSize(500, 500);//窗体宽高
        this.setLocationRelativeTo(null);//窗体显示在屏幕中央
        this.setResizable(false);
        this.setVisible(true);//可见
    }
}
