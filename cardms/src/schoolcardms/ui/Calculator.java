package schoolcardms.ui;


import schoolcardms.vo.SequenceOfNumber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 计算器类
 */
public class Calculator extends JFrame implements ActionListener, ItemListener {

    /*计算器组件*/
    private TextField operand1, operand2, result;//operand：操作数
    private Label operator1, operator2;//operator：运算符
    private Button add, sub, mul, div;//add：加，sub：减，mul：乘，div：除
    /*求数列和组件*/
    private SequenceOfNumber son;//数列类
    private TextField question;//文本框类
    private JComboBox plus;//下拉列表类(只有单击)
    private JList toplus;//滚动列表类(可以单击，也可以双击)

    Calculator()//标题：title
    {
        super("简易计算器");
        operand1 = new TextField(20);
        operand2 = new TextField(20);
        result = new TextField(20);
        operator1 = new Label("");
        operator2 = new Label("=");
        add = new Button("+");
        sub = new Button("-");
        mul = new Button("*");
        div = new Button("/");
        son = new SequenceOfNumber(10, 2, 50);
        question = new TextField("等差数列首项为10,公差为2", 30);
        plus = new JComboBox();
        for (int i = 1; i <= 3; i++) {
            plus.addItem("\t\t\t\t求" + i + "次方和\t\t\t\t");
        }
        toplus = new JList();
        String[] data = new String[51];
        for (int i = 1; i <= 50; i++) {
            data[i] = "前" + i + "项";
        }
        toplus.setListData(data);
        this.setLayout(new FlowLayout());
        this.add(operand1);
        this.add(operator1);
        this.add(operand2);
        this.add(operator2);
        this.add(result);
        add.addActionListener(this);//按钮事件源获得监视器的方法
        this.add(add);
        sub.addActionListener(this);
        this.add(sub);
        mul.addActionListener(this);
        this.add(mul);
        div.addActionListener(this);
        this.add(div);
        this.add(question);
        plus.addItemListener(this);//下拉列表事件源获得监视器的方法
        this.add(plus);
//        toplus.addActionListener(this);//双击滚动列表事件源获得监视器的方法
        //toplus.addItemListener(this);//单击滚动列表事件源获得监视器的方法
        this.add(toplus);

        result.setEditable(false);
        question.setEditable(false);
        //this.setTitle("简易计算器");//设置容器名称
        this.setSize(800, 400);//设置容器尺寸
        //this.setLocation(200,400);//设置容器位置
        this.setVisible(true);//设置容器为可见的
    }

    public void actionPerformed(ActionEvent a)//按钮,滚动列表双击
    {
        try {
            if (a.getSource() == add)//按钮返回事件源
            {
                double d = Double.parseDouble(operand1.getText());//得到文本框对象operand1中的文本,字符串转数值
                double e = Double.parseDouble(operand2.getText());//得到文本框对象operand2中的文本,字符串转数值
                operator1.setText("+");
                result.setText(d + e + "");
            }
            if (a.getSource() == sub)//按钮返回事件源
            {
                double d = Double.parseDouble(operand1.getText());//得到文本框对象operand1中的文本,字符串转数值
                double e = Double.parseDouble(operand2.getText());//得到文本框对象operand2中的文本,字符串转数值
                operator1.setText("-");
                result.setText(d - e + "");
            }
            if (a.getSource() == mul)//按钮返回事件源
            {
                double d = Double.parseDouble(operand1.getText());//得到文本框对象operand1中的文本,字符串转数值
                double e = Double.parseDouble(operand2.getText());//得到文本框对象operand2中的文本,字符串转数值
                operator1.setText("*");
                result.setText(d * e + "");
            }
            if (a.getSource() == div)//按钮返回事件源
            {
                double d = Double.parseDouble(operand1.getText());//得到文本框对象operand1中的文本,字符串转数值
                double e = Double.parseDouble(operand2.getText());//得到文本框对象operand2中的文本,字符串转数值
                operator1.setText("/");
                result.setText(d / e + "");
            }
            if (a.getSource() == toplus)//滚动列表双击返回事件源
            {
                int sum = 0;
                for (int i = 0; i < toplus.getSelectedIndex() + 1; i++) {
                    sum = sum + son.getData()[i];
                }
                question.setText(sum + "");
            }
        } catch (NumberFormatException g) {
            result.setText("只能进行数值运算");
        } catch (ArithmeticException g) {
            result.setText("除数不能为零");
        }
    }

    public void itemStateChanged(ItemEvent linshi)//下拉列表,滚动列表单击
    {
        if (linshi.getSource() == plus)//下拉列表返回事件源
        {
            if (plus.getSelectedIndex() == 0) {
                //
            }
            if (plus.getSelectedIndex() == 1) {
                for (int i = 0; i < 50; i++) {
                    son.getData()[i] = son.getData()[i] * son.getData()[i];
                }
            }
            if (plus.getSelectedIndex() == 2) {
                for (int i = 0; i < 50; i++) {
                    son.getData()[i] = son.getData()[i] * son.getData()[i] * son.getData()[i];
                }
            }
        }
    }
}
