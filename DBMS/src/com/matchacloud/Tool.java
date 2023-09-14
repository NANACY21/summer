package com.matchacloud;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java正则表达式、字符串操作示例
 * 工具类
 * 该类中的方法名不可轻易更改
 * @author 李箎
 *
 */
public class Tool {
    private static Pattern p1 = Pattern.compile("(?<=\\()[^\\)]+");
    private static Pattern p2 = Pattern.compile("(?<=\\()[^\\)]+");
    private static Pattern p3 = Pattern.compile("(?<=url\\()[^\\)]+");

    /**
     * 正则表达式
     */
    private static Pattern p4 = Pattern.compile("^[-\\+]?[\\d]*$");

    /**
     * @param s
     * @return s首字母大写，其余小写
     */
    public static String standard(String s) {
        //截取字符串首字母
        char ch = s.substring(0, 1).charAt(0);
        //截取剩余的字符串;
        return (ch + "").toUpperCase() + s.substring(1).toLowerCase();
    }

    /**
     * @param s
     * @return s的括号是否正确配对
     */
    public static boolean matching(String s) {
        Stack<Character> stack = new Stack<>();
        char c, temp;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (c == '{' || c == '[' || c == '(' || c == '<') {
                stack.push(c);
            } else if (c == '}' || c == ']' || c == ')' || c == '>') {
                if (stack.isEmpty()) {
                    return false;
                }
                temp = stack.peek();
                switch (c) {
                    case '}':
                        if (temp == '{') {
                            stack.pop();
                            break;//跳出离该关键字最近的循环
                        } else {
                            return false;
                        }
                    case ']':
                        if (temp == '[') {
                            stack.pop();
                            break;
                        } else {
                            return false;
                        }
                    case ')':
                        if (temp == '(') {
                            stack.pop();
                            break;
                        } else {
                            return false;
                        }
                    case '>':
                        if (temp == '<') {
                            stack.pop();
                            break;
                        } else {
                            return false;
                        }
                    default:
                        return false;
                }
            }
        }
        //此时栈中不应该再有左括号
        return stack.isEmpty();
    }

    /**
     * 使用该方法需要查看DBMS模块
     *
     * @param s
     * @param rgex
     * @return s指定两个子字符串之间的内容(该方法不能试图获取左右括号之间的内容)
     */
    public static String getObjective(String s, String rgex) {
        //匹配的模式
        Pattern pattern = Pattern.compile(rgex);
        Matcher m = pattern.matcher(s);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

    /**
     * @param s
     * @return s中第一个小括号里的内容
     */
    public static String getBracketsObjective(String s) {

        Matcher m = p1.matcher(s);
        while (m.find()) {
            return m.group();
        }
        return "";
    }

    /**
     * @param s
     * @return s中所有小括号里的内容
     */
    public static Vector<String> getAllBracketsObjective(String s) {
        Vector<String> v = new Vector<String>();

        Matcher m = p2.matcher(s);
        while (m.find()) {
            v.addElement(m.group());
        }
        return v;
    }

    /**
     * @param s
     * @return s中所有小括号中url里的内容
     */
    public static Vector<String> getAllUrlObjective(String s) {

        Vector<String> v = new Vector<String>();

        Matcher m = p3.matcher(s);
        while (m.find()) {
            v.addElement(m.group());
        }
        return v;
    }

    /**
     * 去掉数组s中每个元素的前后空白
     *
     * @param s
     * @return s
     */
    public static String[] myTrim(String[] s) {
        for (int i = 0; i < s.length; i++) {
            s[i] = s[i].trim();
        }
        return s;
    }

    /**
     * 删除s中的子串AppointWord（若AppointWord出现多次，全删）
     *
     * @param s
     * @param appointWord
     * @return 删除后的字符串
     */
    public static String deleteAppointWord(String s, String appointWord) {
        StringBuffer sb = new StringBuffer(s);
        //记录子串AppointWord出现的次数
        int deleteCount = 0;
        while (true) {
            int index = sb.indexOf(appointWord);
            if (index == -1) {
                break;
            }
            sb.delete(index, index + appointWord.length());
            deleteCount++;
        }
        if (deleteCount != 0) {
            return sb.toString();
        } else {
            //返回原来的字符串
            return s;
        }
    }

    /**
     * 删除s中的子串AppointWord（若AppointWord出现多次，删其中一个）
     *
     * @param s
     * @param appointWord
     * @return 删除后的字符串
     */
    public static String deleteAppointWordOnce(String s, String appointWord) {
        StringBuffer sb = new StringBuffer(s);
        //记录子串AppointWord出现的次数
        int deleteCount = 0;
        while (true) {
            int index = sb.indexOf(appointWord);
            if (index == -1) {
                break;
            }
            sb.delete(index, index + appointWord.length());
            deleteCount++;
            break;
        }
        if (deleteCount != 0) {
            return sb.toString();
        } else {
            //返回原来的字符串
            return s;
        }
    }

    /**
     * @param s
     * @param appointWord
     * @return s中子串AppointWord出现的次数
     */
    public static int subStrTimes(String s, String appointWord) {
        StringBuffer sb = new StringBuffer(s);
        //记录子串AppointWord出现的次数
        int deleteCount = 0;
        while (true) {
            int index = sb.indexOf(appointWord);
            if (index == -1) {
                break;
            }
            sb.delete(index, index + appointWord.length());
            deleteCount++;
        }
        return deleteCount;
    }

    /**
     * @param s
     * @param appointWord
     * @return s中子串AppointWord出现的次数
     */
    public static int substrTimes(String s, String appointWord) {
        int index = 0, count = 0;
        while ((index = s.indexOf(appointWord, index)) != -1) {
            count++;
            index = index + appointWord.length();
        }
        return count;
    }

    /**
     * @param s
     * @param appointWord
     * @param index       从s的索引index处开始
     * @return s中子串AppointWord出现的次数
     */
    public static int subStrTimes(String s, String appointWord, int index) {
        if (s.indexOf(appointWord, index) == -1) {
            return 0;
        } else {
            int inde = s.indexOf(appointWord, index);
            return 1 + subStrTimes(s, appointWord, inde + appointWord.length());
        }
    }

    /**
     * 求s中每个字符出现的次数
     *
     * @param s
     */
    public static void charCount(String s) {
        Map<Character, Integer> map = new HashMap<>(20);
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            //判断map容器中是否包含key ch
            if (map.containsKey(ch)) {
                Integer count = map.get(ch);
                count += 1;
                map.put(ch, count);
            } else {
                map.put(ch, 1);
            }
        }
        //出现次数最多的字符
        Character mostKey = ' ';
        //该字符出现的次数
        Integer mostCount = 0;
        for (Character key : map.keySet()) {


            Integer value = map.get(key);
            if (value > mostCount) {
                mostCount = value;
                mostKey = key;
            }
            System.out.println(key + "出现次数:" + value);

        }
        System.out.println("出现次数最多的字符:" + mostKey + "，" + mostKey + "出现次数:" + mostCount);
    }

    /**
     * @param s
     * @return s冒号后面的内容(若没冒号 ， 返回原串s)
     */
    public static String afterColon(String s) {
        return s.substring(s.indexOf(":") + 1);
    }

    /**
     * @param path 文本文件路径
     * @return 该路径的文件行数
     */
    public static int fileRow(String path) {
        int count = 0;
        String s;
        try {
            InputStreamReader ir = new InputStreamReader(new FileInputStream(path));
            BufferedReader bf = new BufferedReader(ir);
            while ((s = bf.readLine()) != null) {
                count++;
            }
            bf.close();
            ir.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * @param f f必须指向1个.txt文件
     * @return 该文件是否为空
     */
    public static boolean fileEmpty(File f) {
        if (f.exists() && f.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 清空.txt内全部内容
     *
     * @param path .txt路径
     */
    public static void clear(String path) {
        File f = new File(path);
        FileWriter fw = null;
        try {
            fw = new FileWriter(f);
            fw.write("");
            /*
            强制写出缓冲区中的数据
             */
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 空行也读
     *
     * @param path 文件路径
     * @return 把.txt文件中内容按行读取到Vector
     */
    public static Vector<String> getFileData(String path) {
        Vector<String> v = new Vector<>();
        String s = null;
        File f = new File(path);
        try {
            InputStreamReader ir = new InputStreamReader(new FileInputStream(f));
            BufferedReader bf = new BufferedReader(ir);
            while ((s = bf.readLine()) != null) {
                s = s.trim();
                v.addElement(s);
            }
            bf.close();
            ir.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return v;
    }

    /**
     * 把v顺序写入.txt文件
     *
     * @param v
     * @param path
     */
    public static void setFileData(Vector<String> v, String path) {
        Tool.clear(path);
        File file = new File(path);
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < v.size(); i++) {
                bw.write(v.elementAt(i) + "\r\n");
            }
            bw.close();//这样就可以不调用flush()方法
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提取二维向量对象到内存
     * 提取文件中对象（都是Vector对象，只是泛型不同），是否可以共用这一个方法？？
     * 反序列化
     * 必须用字节流
     * <p>
     * 调用该方法时再次强转
     *
     * @param path
     * @return
     */
    public static Vector fileDepositVector(String path) {
        Vector<Object> v = new Vector<>();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            /*
            被串行化的类必须实现Serializable接口
            Vector已实现Serializable接口
             */
            v = (Vector<Object>) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return v;
    }

    /**
     * 把v存回已有的文件
     * 序列化
     * 必须用字节流
     * 文件中是字节序列
     * 通配符泛型集合
     *
     * @param v    v：
     *             Vector<Vector<String>> Vector<TR> Vector<User> Vector<CM> Vector<SCUser> Vector<TR>均可
     * @param path
     */
    public static void vectorDepositFile(Vector<? extends Object> v, String path) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        /*
        清空.txt文件中内容
         */
        Tool.clear(path);
        try {
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            /*
            被串行化的类必须实现Serializable接口
            Vector已实现Serializable接口
             */
            oos.writeObject(v);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 删除f
     *
     * @param f f为目录或文件
     */
    public static void deleteDir(File f) {
        if (f.isDirectory()) {
            File[] filelists = f.listFiles();
            for (int i = 0; i < filelists.length; i++) {
                //递归删除
                deleteDir(filelists[i]);
            }
            //f如果为目录，目录必须为空才可使用此方法
            f.delete();
        } else {
            //删除文件
            f.delete();
        }
    }

    /**
     * 遍历f下所有内容
     *
     * @param f 目录
     */
    public static void traversalDir(File f) {
        System.out.println("目录" + f.getName() + "中的内容:");
        File[] filelists = f.listFiles();
        for (File file : filelists) {
            if (file.isDirectory()) {
                traversalDir(file);
            } else {
                System.out.println(file.getName());
            }
        }
    }

    /**
     * 随机生成一个16位数字
     */
    public static String get16bit() {
        Random r = new Random();
        int r1 = r.nextInt(99999999);
        int r2 = r.nextInt(99999999);
        long r3 = r1 * 100000000L + r2;
        DecimalFormat r4 = new DecimalFormat("0000000000000000");
        return r4.format(r3);
    }

    /**
     * 实际开发中表的数量很大 -> 需要分表 -> 就不能使用mysql主键自增
     *
     * @return 线程安全的全局唯一id（全局统一订单id）
     */
    public static String getGlobalUniqueId() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder(5);

        //5位随机数
        int num = 5;
        for (int i = 0; i < num; i++) {
            sb.append(String.valueOf(r.nextInt(10)));
        }
        //14位
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmhhss");
        //19位
        return sdf.format(new Date()) + sb.toString();
    }

    /**
     * @param n
     * @return n的阶乘
     */
    public static long getFactorial(int n) {
        long sum = 1;
        for (int i = 1; i <= n; i++) {
            sum = sum * i;
        }
        return sum;
    }

    /**
     * @param n
     * @return n的阶乘
     */
    public static long factorial(int n) {
        if (n == 0) {
            //0的阶乘是1
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    /**
     * 九九乘法表
     */
    public static void jiuJiu() {
        int num = 9;
        for (int i = 1; i <= num; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "*" + i + "=" + j * i + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 使用try catch也可以判断
     *
     * @param s
     * @return s能否转换为数字 / 是否全为数字
     */
    public static boolean transfer(String s) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param s
     * @param bit
     * @return s是否是bit位数字
     */
    public static boolean is(String s, int bit) {

        if (s.length() == bit && p4.matcher(s).matches()) {
            return true;
        }
        return false;
    }

    /**
     * @param s1
     * @param s2
     * @return s1是否等于s2
     */
    public static boolean same(String s1, String s2) {
        if (s1.equals(s2)) {
            return true;
        }
        return false;
    }

    /**
     * @param email
     * @return 邮件格式是否正确
     */
    public static boolean mailFormat(String email) {
        int index = -1;
        char ch = '@';
        char ch2 = '.';
        if (email.indexOf(ch) != index && email.indexOf(ch2) > email.indexOf(ch)) {
            return true;
        }
        return false;
    }

    /**
     * 把11位手机号中间4位用*代替
     *
     * @param phoneNumber
     * @return
     */
    public static String replace(String phoneNumber) {
        StringBuffer sb = new StringBuffer(phoneNumber);
        sb.delete(3, 7);
        char ch = '*';
        int length = 11;
        while (sb.length() != length) {
            sb.insert(3, ch);
        }
        return sb.toString();
    }

    /**
     * @return 当前日期 2019/05/10	09:10:44
     */
    public static String getTime() {
        //设置日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd\thh:mm:ss");
        //日期转字符串，new Date() -- 获取当前系统时间
        return sdf.format(new Date());
    }

    /**
     * @param date1   "2019-12-08"
     * @param date2   "2019-05-09"
     * @param pattern "yyyy-MM-dd" "yyyy-MM-dd hh:mm:ss"
     * @return 两个日期格式的字符串中较晚的日期
     */
    public static Date getLaterTime(String date1, String date2, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            //字符串转日期
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            //d1.after(d2);
            //d2.before(d1);
            return d1.compareTo(d2) > 0 ? d1 : d2;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * @param date1
     * @param pattern
     * @param x
     * @return 过没过期
     */
    public static boolean o(String date1, String pattern, int x) {
        SimpleDateFormat sdf1 = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();
        try {
            Date d = sdf1.parse(date1);
//            c.set(2020,5,8);
//            c.getWeekYear();
            c.add(Calendar.DATE, 0 - x);
            Date d2 = c.getTime();
            return d2.compareTo(d) > 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
}