package package1;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

/**
 * @author 李箎
 *
 */
public class Ll extends JFrame implements KeyListener {

    public Ll() {
        this.addKeyListener(this);
        this.setSize(600, 600);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                System.out.println("你按的向上箭头");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("你按的向下箭头");
                break;
            default:
                System.out.println();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        Ll ll = new Ll();

    }

    public void haha() {
        Scanner s = new Scanner(System.in);
        s.next();
    }
}
