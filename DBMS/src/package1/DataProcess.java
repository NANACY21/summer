package package1;
import java.util.Vector;

/**
 * 该类用作动作和数据处理
 * @author 李箎
 */
public class DataProcess {

    /**
     * 生成权限表
     */
    public static void generatePowerTable() {
        Vector<Vector<String>> powerTable = new Vector<Vector<String>>();
        Vector<String> tuple = new Vector<String>();
        tuple.addElement("");
        tuple.addElement("insert");
        tuple.addElement("delete");
        tuple.addElement("update");
        tuple.addElement("select");
        tuple.addElement("createDatabase");
        tuple.addElement("createTable");
        tuple.addElement("createIndex");
        tuple.addElement("useDatabase");
        tuple.addElement("showDatabases");
        tuple.addElement("showTables");
        tuple.addElement("showTableDataDictionary");
        tuple.addElement("showIndex");
        tuple.addElement("dropDatabase");
        tuple.addElement("dropTable");
        tuple.addElement("dropIndex");
        tuple.addElement("addField");
        tuple.addElement("dropField");
        tuple.addElement("createUser");
        tuple.addElement("dropUser");
        tuple.addElement("grantPower");
        tuple.addElement("revokePower");
        tuple.addElement("showPowerTable");
        powerTable.addElement(tuple);
        //存权限表列数
        int colNumber = tuple.size();
        tuple = new Vector<String>();
        tuple.addElement("root");
        for (int i = 1; i < colNumber; i++) {
            tuple.addElement("1");
        }
        powerTable.addElement(tuple);
        Tool.vectorDepositFile(powerTable, SqlParser.getPowerTable());
    }

    /**
     * 登录
     *
     * @param userId
     * @param password
     * @return
     */
    public static String login(String userId, String password) {
        Vector<String> users = Tool.getFileData(SqlParser.getUsersPath());
        for (int i = 0; i < users.size(); i++) {
            String row = users.elementAt(i);
            String[] idPw = row.split("\\s+");
            idPw = Tool.myTrim(idPw);
            if (userId.equals(idPw[0])) {
                if (password.equals(idPw[1])) {
                    SqlParser.setCurrentUser(getPowers(userId));
                    return "";
                } else {
                    return ConstPool.PWE;
                }
            }
        }
        return ConstPool.UNE;
    }

    /**
     * @param userId 用户id
     * @return 这个用户权限表的那一行
     */
    public static Vector<String> getPowers(String userId) {
        //打开权限表
        Vector<Vector<String>> powerTable = Tool.fileDepositVector(SqlParser.getPowerTable());
        for (int i = 1; i < powerTable.size(); i++) {
            Vector<String> tuple = powerTable.elementAt(i);
            if (tuple.elementAt(0).compareTo(userId) == 0) {
                return tuple;
            }
        }
        //返回空向量
        return new Vector<>();
    }
}
