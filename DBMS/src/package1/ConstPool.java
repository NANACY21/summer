package package1;

/**
 * 常量池
 * 方便修改数据
 * 该常量池中变量一般不改变
 * 一个系统 -- 一个常量池
 * @author 李箎
 *
 */
public class ConstPool {

    /**
     * DBMS项目根目录路径
     */
    public static final String PATH = "D:\\allproject\\personal\\projects\\DBMS\\src\\package1\\data";

    /**
     * 用户id密码文件路径
     */
    public static final String USERS_PATH = "D:\\allproject\\personal\\projects\\DBMS\\src\\package1\\data\\users.txt";

    /**
     * 所有用户的权限表文件路径
     */
    public static final String POWER_TABLE = "D:\\allproject\\personal\\projects\\DBMS\\src\\package1\\data\\powerTable.txt";

    /**
     * 用户登录密码错误，显示该信息
     */
    public static final String PWE = "user password error";

    /**
     * 用户登录id错误，显示该信息
     */
    public static final String UNE = "user id error";
}
