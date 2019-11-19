package partition1;

import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

// STOPSHIP: 2019/9/5 字节跳动笔试
public class ByteDance {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String s = scanner.nextLine();//闹钟个数
        int iiii = Integer.parseInt(s);
        Vector<Vector<Integer>> nao = new Vector<>();
        for (int i = 0; i < iiii; i++) {//输入
            Vector<Integer> tuple = new Vector<>();
            int hour = scanner.nextInt();
            int min = scanner.nextInt();
            tuple.addElement(hour);
            tuple.addElement(min);
            nao.addElement(tuple);
        }

        Vector<Integer> ttt = new Vector<>();
        for (int i = 0; i < nao.size(); i++) {
            Vector<Integer> tuple = nao.elementAt(i);
            ttt.addElement(tuple.elementAt(0) * 60 + tuple.elementAt(1));
        }
        nao.removeAllElements();
        Collections.sort(ttt, Collections.reverseOrder());
        for (int i = 0; i < ttt.size(); i++) {
            Vector<Integer> t = new Vector<>();
            int i1 = ttt.elementAt(i) / 60;
            t.addElement(i1);
            int i2 = ttt.elementAt(i) % 60;
            t.addElement(i2);
            nao.addElement(t);
        }


        String s1 = scanner.nextLine();//需要的时间/分钟
        s1 = scanner.nextLine();
        int hour1 = scanner.nextInt();//上班时间
        int min1 = scanner.nextInt();//上班时间
        int ss1 = Integer.parseInt(s1);//需要的时间/分钟


        //现在算最晚起床时间
        int laH = 0;
        int laM = 0;
        while (laH == 0) {
            if(min1>=ss1){
                laH = hour1;
                laM = min1 - ss1;
            }
            else {
                int temp = ss1 - min1;
                laM = 0;
                if (temp < 60) {
                    laH = hour1 - 1;
                    laM = 60 - temp;
                }
                else {
                    laH=hour1-(int)(temp/60)-1;
                    laM = 60 - (temp - 60);
                }

            }
        }

        int lastHour = laH;//现在算最晚起床时间（从闹钟里选的）
        int lastMin = laM;//现在算最晚起床时间（从闹钟里选的）

        for (int i = 0; i < nao.size(); i++) {
            Vector<Integer> tup = nao.elementAt(i);
            if (tup.elementAt(0) > laH) {
                continue;
            } else if (tup.elementAt(0) <= laH) {
                if (tup.elementAt(1) > laM) {
                    continue;
                } else if (tup.elementAt(1) <= laH) {
                    lastHour = tup.elementAt(0);
                    lastMin = tup.elementAt(1);
                    break;
                }
            }
        }
        System.out.println(lastHour+" "+lastMin);

    }
}