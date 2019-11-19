package package1;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Enumeration;
import java.util.Vector;

/**
 * 该类用于显示二维向量对象
 * @author 李箎
 */
public class Display extends JFrame {

    private JTable tr;
    /**
     * 滚动窗体
     */
    private JScrollPane jsp;

    public Display(Vector<Vector<String>> vec) {
        super();
        Vector<String> v = vec.elementAt(0);
        int width = 500, height = 500;
        int fieldNum = 5;
        if (v.size() > fieldNum) {
            width = 1000;
        }
        tr = new JTable(vec.size(), v.size());
        tr.getTableHeader().setPreferredSize(new Dimension(1, 0));
        tr.setRowHeight(40);
        tr.setEnabled(false);
        for (int i = 0; i < vec.size(); i++) {
            v = vec.elementAt(i);
            for (int j = 0; j < v.size(); j++) {
                String str = v.elementAt(j);
                tr.setValueAt(str, i, j);
            }
        }
        jsp = new JScrollPane();
        //把表格放到滚动窗体里，<=>将tr设置为构造器参数
        jsp.setViewportView(tr);
        //把滚动窗体放到this里，<=>this.getContentPane().add(jsp);
        this.add(jsp);
        //tr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//关闭表格自动调整
        //this.fitTableColumns(tr);
        //设置水平滚动条需要时可见
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//设置水平滚动条不可见
        //jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//设置水平滚动条总是可见
        //设置当前窗体界面尺寸
        this.setSize(width, height);
        //将当前窗体界面显示在屏幕中央
        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        //可见
        this.setVisible(true);
    }

    /**
     * 列宽自适应内容
     *
     * @param t
     */
    public void fitTableColumns(JTable t) {
        JTableHeader th = t.getTableHeader();
        int rowCount = t.getRowCount();
        Enumeration columns = t.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = th.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int) t.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(t, column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();
            for (int row = 0; row < rowCount; row++) {
                int preferedWidth = (int) t.getCellRenderer(row, col).getTableCellRendererComponent(t, t.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            //关键行
            th.setResizingColumn(column);
            column.setWidth(width + t.getIntercellSpacing().width);
        }
    }
}