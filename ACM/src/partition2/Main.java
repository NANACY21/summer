package partition2;

import java.util.Scanner;
import java.util.Vector;

/**
 * 运行结果：
 * a b c d e *
 * b c f g *
 * 7
 * 3
 */
public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        String a="";
        String b="";
        a=cin.nextLine();
        b=cin.nextLine();
        a=a.replace('*',' ');
        b=b.replace('*',' ');
        a.trim();
        b.trim();
        String[] split_a = a.split("[ ]");
        String[] split_b = b.split("[ ]");
        Vector<String> aa=new Vector<String>();
        Vector<String> bb=new Vector<String>();
        for(int i=0;i<split_a.length;i++){
            aa.addElement(split_a[i]);
        }
        for(int i=0;i<split_b.length;i++){
            bb.addElement(split_b[i]);
        }
        for(int i=0;i<split_b.length;i++){
            if(aa.contains(split_b[i])){
                continue;
            }
            else{
                aa.addElement(split_b[i]);
            }
        }
        System.out.print(aa.size()+" ");
        for(int i=0;i<bb.size();i++){
            if(!aa.contains(bb.elementAt(i))){
                bb.remove(i);
            }
        }
        aa.removeAll(bb);
        System.out.print(aa.size());
    }
}
