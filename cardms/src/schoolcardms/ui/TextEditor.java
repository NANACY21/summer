package schoolcardms.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * 文本编辑器
 */
public class TextEditor extends JFrame implements ActionListener {

    public static final String SHIYAN_7_TXT = "D:\\allproject\\projects\\cardms\\src\\schoolcardms\\dao\\shiyan7.txt";
    private JMenuItem open, save, save_as, exit;//菜单项
    private JCheckBoxMenuItem cItem;
    private JSeparator div_line;//分割线
    private JMenu menu1, menu2, menu2_1;//菜单
    private JMenuBar menubar;//菜单条
    private JTextArea ta;//文本区类
    private JScrollPane jsp;//滚动窗体类
    private JFileChooser chooser;//文件选择器类
    private FileDialog fd;//

    TextEditor() {
        super("文本编辑器");
        open = new JMenuItem("打开");
        save = new JMenuItem("保存");
        save_as = new JMenuItem("另存为");
        div_line = new JSeparator();
        exit = new JMenuItem("退出");
        cItem = new JCheckBoxMenuItem("try me");
        menu1 = new JMenu("文件");
        menu2 = new JMenu("常规");
        menu2_1 = new JMenu("常规-子菜单");
        menubar = new JMenuBar();
        ta = new JTextArea();
        jsp = new JScrollPane();
        chooser = new JFileChooser();
        fd = new FileDialog(this, "文件对话框", FileDialog.LOAD);//第三参数常量值指示文件对话框窗口的作用是查找要读取的文件
        /*添加组件*/
        menu1.add(open);
        menu1.add(save);
        menu1.add(save_as);
        menu1.addSeparator();//等价于menu1.add(div_line);
        menu1.add(exit);
        menubar.add(menu1);
        menu2_1.add(cItem);
        menu2.add(menu2_1);
        menubar.add(menu2);
        this.setJMenuBar(menubar);//把菜单条放到当前类(TextEditor)的对象里
        jsp.setViewportView(ta);//把文本区放到滚动窗体里,等价于jsp=new JScrollPane(ta);
        this.add(jsp);//把滚动窗体放到当前类(TextEditor)的对象里,等价于this.getContentPane().add(jsp);
        /*添加监听器*/
        open.addActionListener(this);
        save.addActionListener(this);
        save_as.addActionListener(this);
        exit.addActionListener(this);
        exit.addActionListener
                (
                        new ActionListener()//接口的实现类类体
                        {
                            public void actionPerformed(ActionEvent arg0) {
                                removeNotify();
                            }
                        }
                );
        this.setSize(800, 600);//设置当前类(TextEditor)的对象的长宽
        this.setVisible(true);//设置为可见的
        this.setLocationRelativeTo(null);//将当前窗体界面显示在屏幕中央
    }

    public void actionPerformed(ActionEvent e) {
//        fd.setVisible(true);//点任何按钮都触发该行
        if (e.getSource() == open)//打开一个文件，读入，在文本区显示
        {
//            fd.setVisible(true);
            int state = chooser.showOpenDialog(null);//弹出一个 "Open File" 文件选择器对话框
            if (state == chooser.APPROVE_OPTION)//APPROVE_OPTION:选择确认（yes、ok）后返回该值
            {
                File F = chooser.getSelectedFile();//返回选中的文件
                FileInputStream I = null;//文件字节输入流（数据流向内存）
                try {
                    I = new FileInputStream(F);
                } catch (FileNotFoundException es) {
                    JOptionPane.showMessageDialog(null, "文件不存在!", "文件错误", 2);
                    es.printStackTrace();
                }
                byte buf[] = new byte[1024];
                ta.setText("");
                try {
                    while (I.read(buf) != -1) {
                        ta.append(new String(buf, "gbk"));//将给定文本追加到文档结尾
                        //该String构造方法：通过使用指定的 charset 解码指定的 byte 数组，构造一个新的 String。
                    }
                } catch (IOException m) {
                    JOptionPane.showMessageDialog(null, "文件读入异常!", "IO错误", JOptionPane.YES_NO_OPTION);
                }
            }
            //File f=chooser.getSelectedFile();
            //io
        }
        if (e.getSource() == save)//把文本区内容存入指定文件
        {
            //chooser.showSaveDialog(null);
            try {
                String s = ta.getText();
                FileWriter fr = new FileWriter("d:\\shiyan7.txt");//FileWriter：文件字符输出流；FileWriter类构造方法参数为给定文件名，这个文件没有会自动创建
                fr.write(s);
                fr.close();
            } catch (IOException es) {
                JOptionPane.showMessageDialog(null, "文件保存异常!", "IO错误", JOptionPane.YES_NO_OPTION);
                es.printStackTrace();
            }
        }
        if (e.getSource() == save_as) {
            try {
                chooser.showSaveDialog(null);//弹出一个 "Save File" 文件选择器对话框。
                String s = ta.getText();
                File file = chooser.getSelectedFile();//返回选中的文件
                FileWriter fr = new FileWriter(file);
                fr.write(s);
                fr.close();
            } catch (IOException es) {
                JOptionPane.showMessageDialog(null, "文件保存异常!", "IO错误", JOptionPane.YES_NO_OPTION);
                es.printStackTrace();
            }
        }
        if (e.getSource() == exit) {
            int choose = JOptionPane.showConfirmDialog(null, "是否保存？", "退出", JOptionPane.YES_NO_CANCEL_OPTION);
            if (choose == 1)//不保存
            {
                System.exit(0);
            } else if (choose == 0)//保存
            {
                try {
                    String s = ta.getText();
                    FileWriter fr = new FileWriter(SHIYAN_7_TXT);
                    fr.write(s);
                    fr.close();
                    System.exit(0);
                } catch (IOException es) {
                    JOptionPane.showMessageDialog(null, "文件保存异常!", "IO错误", JOptionPane.YES_NO_OPTION);
                    es.printStackTrace();
                }
            } else//取消
            {

            }
        }
    }
}