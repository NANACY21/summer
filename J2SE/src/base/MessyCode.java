package base;

import java.io.UnsupportedEncodingException;

/**
 * 乱码问题
 */
public class MessyCode {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String original = "这是一个字符串，是Unicode编码的。恭喜發財";
        System.out.println("原有字符串：" + original);

        //将Unicode编码按照gbk编码格式进行编码
        byte[] gbkBytes = original.getBytes("gbk");

        /**
         * 本来是gbk编码的字节数组，使用utf-8来解码，当然此时必然会是乱码
         * 若第一次解码时使用这种错误的解码方式，则后面将无法再恢复，下面的例子可以看到
         */
        String utf8Str = new String(gbkBytes, "utf-8");
        System.out.println("UTF-8解码后的字符串：" + utf8Str);

        /**
         * 然后把该字符串按照UTF-8的编码格式再编码回去
         * 但这时 gbkBytes 和 utf8Bytes 已经完全不同了
         */
        byte[] utf8Bytes = utf8Str.getBytes("utf-8");

        // 接着再按照GBK的编码格式来解码 得到的自然是乱码
        String s = new String(utf8Bytes, "gbk");
        System.out.println("GBK解码UTF-8编码后的字符串：" + s);

        /**
         * 但是，若第一次解码时使用单字节的编码格式来解码，则后面是可以再恢复的
         * 但前提要保证该单字节的编码会利用上所有的8位
         * 也就是说可以表示256个字符的才行
         * <p>
         * 单字节的编码格式有两种，ASCII和ISO-8859-?
         * ASCII使用了7位来表示字符，最高位永远为0，其可以表示的范围只有128个字符
         * ISO-8859-?（问号表示1,2,...,15,16（没有12），表示15个字符集）
         * 使用8位来表示字符，总共可以表示256个字符
         * <p>
         * 这两种单字节编码，ISO-8859-1最常用
         */

        //转换为ASCII码的字符串
        String asciiStr = new String(gbkBytes, "ASCII");
        System.out.println("ASCII解码后的字符串：" + asciiStr);

        //再将ASCII转换为GBK
        String gbkStr = new String(asciiStr.getBytes("ASCII"), "gbk");
        System.out.println("将ASCII编码按照gbk编码格式解码：" + gbkStr);

        String isoStr = new String(gbkBytes, "ISO-8859-1");
        System.out.println("ISO-8859-1解码后的字符串：" + asciiStr);

        //再将ASCII转换为gbk
        String gbkStr1 = new String(isoStr.getBytes("ISO-8859-1"), "gbk");
        System.out.println("将ISO-8859-1编码按照GBK编码格式解码：" + gbkStr1);
    }
}
