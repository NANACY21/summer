package package1;
import java.io.*;
import java.util.LinkedList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL解析器
 * 正则表达式
 * String
 */
public class SqlParser {

    private static Vector<String> currentUser = new Vector<String>();//当前用户权限表的那一行
    private static String sql = "";//当前输入的sql语句
    private static String databasePath = "";//当前库路径
    private static String tablePath = "";//当前操作的表的路径
    private static String dataDictionaryPath = "";//当前操作的表的数据字典的路径
    private static Vector<String> dds = new Vector<String>();//多表做笛卡儿积后，这些表总的数据字典
    private static Vector<String> aboutIndexs = new Vector<String>();//多表做笛卡儿积后，这些表总的索引字典

    public static void setCurrentUser(Vector<String> currentUser) {
        SqlParser.currentUser = currentUser;
    }

    public static String getPowerTable() {
        return ConstPool.POWER_TABLE;
    }

    public static String getUsersPath() {
        return ConstPool.USERS_PATH;
    }

    /**
     * 预处理sql语句
     *
     * @param originalSql
     */
    public static void preProcess(String originalSql) {
        SqlParser.sql = originalSql;
        SqlParser.sql = SqlParser.sql.trim();//去掉前后空白
        SqlParser.sql = SqlParser.sql.toLowerCase();//全变为小写
        SqlParser.sql = SqlParser.sql.replaceAll("\\s{1,}", " ");//规范单词间距
        if (SqlParser.sql.endsWith(";")) {
            SqlParser.sql = SqlParser.sql.substring(0, SqlParser.sql.length() - 1);//去掉';'
            SqlParser.sql = SqlParser.sql + " ;";//在';'前加一个空格
        }
    }

    /**
     * 预识别，共22个权限
     * 执行每个功能都要输入一个sql语句
     * 优化正则匹配
     *
     * @param originalSql
     */
    public static void recognition(String originalSql) {
        SqlParser.preProcess(originalSql);//预处理
        if (!SqlParser.sql.endsWith(";"))
            System.out.println("error! Your input must end with character ';'");
        else if (!Tool.matching(SqlParser.sql))
            System.out.println("error! Your input are not matching {} or [] or ()");
        else if (!SqlParser.power())
            System.out.println("error! You have not this power");
        else if (contains(SqlParser.sql, "(exit)"))
            System.out.println("Bye!");
        else if (contains(SqlParser.sql, "(insert)(.+)(into)(.+)(values)(.+)"))
            insert();
        else if (contains(SqlParser.sql, "(delete)(.+)(from)(.+)"))
            delete();
        else if (contains(SqlParser.sql, "(update)(.+)(set)(.+)"))
            update();
        else if (contains(SqlParser.sql, "(select)(.+)(from)(.+)"))
            select();
        else if (contains(SqlParser.sql, "(create)(.+)(database)(.+)"))
            createDatabase();
        else if (contains(SqlParser.sql, "(create)(.+)(table)(.+)"))
            createTable();
        else if (contains(SqlParser.sql, "(create)(.+)(index)(.+)"))
            createIndex();
        else if (contains(SqlParser.sql, "(use)( )(.+)"))//这种匹配匹配到就行，use前有字符，也匹配到
            switchDatabase();
        else if (contains(SqlParser.sql, "(show)(.+)(databases)"))
            showDatabases();
        else if (contains(SqlParser.sql, "(show)(.+)(tables)"))
            showTables();
        else if (contains(SqlParser.sql, "(desc)(.+)"))
            descTable();
        else if (contains(SqlParser.sql, "(show)(.+)(index)(.+)(from)(.+)"))
            showIndex();
        else if (contains(SqlParser.sql, "(drop)(.+)(database)(.+)"))
            dropDatabase();
        else if (contains(SqlParser.sql, "(drop)(.+)(table)(.+)"))
            dropTable();
        else if (contains(SqlParser.sql, "(drop)(.+)(index)(.+)(on)(.+)"))
            dropIndex();
        else if (contains(SqlParser.sql, "(alter)(.+)(table)(.+)(add)(.+)"))
            addField();
        else if (contains(SqlParser.sql, "(alter)(.+)(table)(.+)(drop)(.+)"))
            dropField();
        else if (contains(SqlParser.sql, "(create)(.+)(user)(.+)(identified)(.+)(by)(.+)"))
            createUser();
        else if (contains(SqlParser.sql, "(drop)(.+)(user)(.+)"))
            dropUser();
        else if (contains(SqlParser.sql, "(grant)(.+)(to)(.+)"))
            grantPower();
        else if (contains(SqlParser.sql, "(revoke)(.+)(from)(.+)"))
            revokePower();
        else if (contains(SqlParser.sql, "(show)(.+)(powertable)(.+)"))
            showPowerTable();


        else if (contains(SqlParser.sql, "(show)(.+)(log)"))
            showLog();
        else
            System.out.println("error! You input a wrong sql");
    }

    /**
     * 看word是否在lineText中存在，支持正则表达式
     *
     * @param sql    要解析的sql语句
     * @param regExp 正则表达式
     * @return
     */
    private static boolean contains(String sql, String regExp) {
        Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        return matcher.find();
    }

    /*实际执行(execute)sql语句*/

    /**
     * 插入方式①可指定列插②顺序插  这里②。不可以"若不匹配插到下一列"
     */
    public static void insert() {
        int i = 0, j = 2;
        if (SqlParser.databasePath.equals("")) {
            System.out.println("please appoint a database at first!");
            return;//退出当前函数
        }
        String tN = Tool.getObjective(sql, "into(.*?)values");
        tN = tN.trim();//得到表名
        if (!SqlParser.switchTablePath(tN)) {
            System.out.println("error! table " + "\"" + tN + "\"" + " is not exists");
            return;
        }
        String data = Tool.getBracketsObjective(sql);//得到所有要插入的数据
        String[] d = data.split("[,]");//d数组中每个元素为每列值
        d = Tool.myTrim(d);
        Vector<String> dd = Tool.getFileData(SqlParser.dataDictionaryPath);//打开数据字典
        if (d.length > Integer.parseInt(dd.elementAt(0) + "") || d.length < 1) {//字段数多或少了
            System.out.println("error! insert fail");
            return;
        }
        Vector<Vector<String>> T = Tool.fileDepositVector(SqlParser.tablePath);//打开表
        Vector<String> tuple = new Vector<String>();//新一行元组
        /*
        for循环尽量不带等号
         */
        for (i = 0, j = 2; i < d.length; i++, j++) {//i：要插入的数据的指针，j：数据字典行指针
            String temp = (String) dd.elementAt(j);//temp：数据字典某一行
            String[] info = temp.split("\\s+");//为了获取数据字典这一行的字段名
            info = Tool.myTrim(info);
            if (temp.indexOf("primary key") != -1 && SqlParser.repetition(SqlParser.tablePath, info[0], d[i]) != -1) {
                /*若该字段有主键约束。这个indexOf在使用时注意是不是前缀后缀的问题,temp里含"primary key"这个子字符串*/
                System.out.println("error! insert fail");
                return;
            }
            if (SqlParser.fieldMatching(info[1], d[i]))//判断字段数据类型和待插入数据类型是否匹配
                tuple.addElement(d[i]);
            else {
                System.out.println("error! insert fail");
                return;
            }
        }
        if (j < dd.size()) {//插入字段数<字段数
            for (i = j; i < dd.size(); i++) {
                String temp = (String) dd.elementAt(i);//temp为数据字典的某一行
                if (temp.indexOf("not null") != -1) {
                    System.out.println("error! insert fail");
                    return;
                }
            }
        }
        T.addElement(tuple);
        Tool.vectorDepositFile(T, SqlParser.tablePath);//把表存回已有的文件
        reloadIndexFile(tN);
        System.out.println("insert data successful!");
    }

    /**
     * 删
     */
    public static void delete() {
        SqlParser.dds = new Vector<String>();
        SqlParser.aboutIndexs = new Vector<String>();//删操作不访问dds和aboutIndexs
        if (SqlParser.databasePath.equals("")) {
            System.out.println("please appoint a database at first!");
            return;
        }
        sql = sql.trim();
        String[] s = sql.split("\\s+");//按空格拆分sql语句，允许有多个空格
        s = Tool.myTrim(s);
        if (!SqlParser.switchTablePath(s[2])) {//s[2]：表名，单表，该方法执行该方法才可分析where子句
            System.out.println("error! table " + "\"" + s[2] + "\"" + " is not exists,create index fail");
            return;
        }
        Vector<Vector<String>> T = Tool.fileDepositVector(SqlParser.tablePath);//打开表
        int deleteRow = 0;//此次删操作删除的行数
        if (sql.indexOf("where") == -1) {//若没有where子句
            deleteRow = T.size() - 1;
            Vector<String> tuple = (Vector<String>) T.elementAt(0);
            T.removeAllElements();
            T.addElement(tuple);
        } else {//若有where子句
            String afterWhere = Tool.getObjective(sql, "where(.*?);");//得到where后面的内容
            Vector<Vector<String>> deleteTuples = SqlParser.whereAnalyse(T, afterWhere);
            if (deleteTuples.size() == 1) {
                System.out.println("error! delete data fail");
                return;
            }
            deleteTuples.remove(0);
            deleteRow = deleteTuples.size();
            T.removeAll(deleteTuples);//从表中移除选中元组
        }
        System.out.println("delete data successful," + deleteRow + " rows data has deleted");
        Tool.vectorDepositFile(T, SqlParser.tablePath);
        reloadIndexFile(s[2]);
    }

    /**
     * 改
     */
    public static void update() {
        SqlParser.dds = new Vector<String>();
        SqlParser.aboutIndexs = new Vector<String>();//改操作不访问dds和aboutIndexs
        if (SqlParser.databasePath.equals("")) {
            System.out.println("please appoint a database at first!");
            return;
        }
        String tN = Tool.getObjective(sql, "update(.*?)set");
        tN = tN.trim();//得到表名，单表
        if (!SqlParser.switchTablePath(tN)) {//该方法执行才可分析where子句
            System.out.println("error! table " + "\"" + tN + "\"" + " is not exists");
            return;
        }
        Vector<Vector<String>> T = Tool.fileDepositVector(SqlParser.tablePath);//打开表
        if (sql.indexOf("where") != -1) {//若有where子句
            String afterWhere = Tool.getObjective(sql, "where(.*?);");//得到where后面的内容
            Vector<Vector<String>> updateTuples = (Vector<Vector<String>>) SqlParser.whereAnalyse(T, afterWhere);//存要改的元组集合
            if (updateTuples.size() == 1) {
                System.out.println("error! update fail");
                return;
            }
            Vector<String> tuple = updateTuples.elementAt(0);
            updateTuples.remove(0);
            T.removeAll(updateTuples);//先从原表移除要更改的元组
            updateTuples.insertElementAt(tuple, 0);
            String afterSet = Tool.getObjective(sql, "set(.*?)where");//存set后的内容
            SqlParser.updateTable(updateTuples, afterSet);
            updateTuples.remove(0);
            T.addAll(updateTuples);
        } else {//若没有where子句
            String afterSet = Tool.getObjective(sql, "set(.*?);");
            SqlParser.updateTable(T, afterSet);
        }
        Tool.vectorDepositFile(T, SqlParser.tablePath);
        SqlParser.reloadIndexFile(tN);
        System.out.println("update data successful!");
    }

    /**
     * 不对数据及结构做任何更改，底层执行顺序：from->where->select
     * 支持：优化查询顺序
     * 二叉树变换transfer -> 优化执行顺序 表名一定在叶节点
     * 各种select和优化查询顺序的实现还不完善
     */
    public static void select() {
        if (SqlParser.databasePath.equals("")) {
            System.out.println("please appoint a database at first!");
            return;
        }
        String tNs;//存所有表名
        String afterSelect;//存所有字段名
        Vector<Vector<String>> result = new Vector<Vector<String>>();//存查询结果
        if (sql.indexOf("where") != -1) {//若有where子句
            tNs = Tool.getObjective(sql, "from(.*?)where");//得到from后的所有表名
            result = SqlParser.tableAnalyse(tNs);//得到这些表的笛卡儿积
            if (result.size() == 1) {//二维向量result第一行第一列为不存在的表名
                System.out.println("error! table " + "\"" + ((Vector<String>) result.elementAt(0)).elementAt(0) + "\"" + " is not exists");
                return;
            }
            String afterWhere = Tool.getObjective(sql, "where(.*?);");//得到where后面的内容
            result = SqlParser.whereAnalyse(result, afterWhere);
            if (result.size() <= 1) {//二维向量result第一行为字段名集合
                System.out.println("error! select fail");
                return;
            }
        } else {//若没有where子句
            tNs = Tool.getObjective(sql, "from(.*?);");//得到from后的表名
            result = SqlParser.tableAnalyse(tNs);//得到这些表的笛卡儿积
            if (result.size() == 1) {//二维向量result第一行第一列为不存在的表名
                System.out.println("error! table " + "\"" + ((Vector<String>) result.elementAt(0)).elementAt(0) + "\"" + " is not exists");
                return;
            }
        }
        afterSelect = Tool.getObjective(sql, "select(.*?)from");//得到select后面的内容
        result = SqlParser.selectAnalyse(result, afterSelect);
        if (result.size() == 1) {//二维向量result第一行为字段名集合
            System.out.println("error! select fail");
            return;
        }
        new Display(result);
    }

    /**
     * 有索引文件且where中有索引字段可以用
     * 索引查
     *
     * @param sql
     * @return
     */
    public static boolean selectWithIndex(String sql) {
        int i = 0, j = 0, k = 0;
        Vector<Vector<String>> Result;//存总查询结果
        if (sql.indexOf("where") != -1) {//有where子句
            String tNs = Tool.getObjective(sql, "from(.*?)where");//得到from后的所有表名
            String[] tN = tNs.split("[,]");
            tN = Tool.myTrim(tN);//数组tN每个元素为一个表名
            Vector<Vector<String>>[] result = new Vector[tN.length];//存每个表的选择结果
            for (i = 0; i < tN.length; i++) {//每次循环得到一个表的选择结果
                if (SqlParser.switchTablePath(tN[i]))//分析from后内容合不合法
                    return false;
                String afterWhere = Tool.getObjective(sql, "where(.*?);");//得到where后面的内容
                afterWhere = afterWhere.trim();
                String[] equations0 = afterWhere.split("or");//equations0中每个元素要么为一简单式，要么仅含and
                for (j = 0; j < equations0.length; j++) {
                    String[] equations1 = equations0[j].split("and");//equations1数组每个元素为一个简单式
                    Vector<Vector<String>> tempResult = Tool.fileDepositVector(SqlParser.tablePath);
                    for (k = 0; k < equations1.length; k++) {
                        Vector<Vector<String>> resultt = new Vector<Vector<String>>();
                        equations1[k] = equations1[k].trim();
                        Vector<String> dd = Tool.getFileData(SqlParser.dataDictionaryPath);
                        resultt.addElement(tempResult.elementAt(0));
                        Vector<String> parts = SqlParser.fieldMatching(dd, equations1[k]);
                        if (parts.size() == 0) {
                        } else {
                            Vector<Vector<String>> DenseIndex = SqlParser.getDenseIndex(tN[i]);
                            Vector<Vector<String>> SparseIndex = SqlParser.getSparseIndex(tN[i]);
                            if (DenseIndex.size() > 0) {//这个表有辅助索引(稠密索引)
                                String DenseIndexField = ((Vector<String>) DenseIndex.elementAt(0)).elementAt(0) + "";
                                if (DenseIndexField.compareTo(parts.elementAt(0)) == 0) {//若这一简单式字段为辅助索引字段
                                    for (int f = 1; f < DenseIndex.size(); f++) {
                                        Vector<String> tuple = (Vector<String>) DenseIndex.elementAt(f);
                                        if (tuple.elementAt(0).compareTo(parts.elementAt(1)) == 0) {
                                            String rows = (String) tuple.elementAt(1);
                                            String[] row = rows.split("\\s+");
                                            row = Tool.myTrim(row);
                                            Vector<String> Row = new Vector<String>();
                                            for (int g = 0; g < row.length; g++)
                                                Row.addElement(row[g]);
                                            for (int x = 1; x < tempResult.size(); x++) {
                                                if (Row.contains(x))
                                                    resultt.addElement(tempResult.elementAt(x));
                                            }
                                        }
                                    }
                                }
                            } else if (SparseIndex.size() > 0) {//这个表有聚集索引(稀疏索引)
                                String SparseIndexField = ((Vector<String>) SparseIndex.elementAt(0)).elementAt(0) + "";
                                if (SparseIndexField.compareTo(parts.elementAt(0)) == 0) {//若这一简单式字段为聚集索引字段
                                    for (int f = 1; f < SparseIndex.size(); f++) {
                                        Vector<String> tuple = (Vector<String>) DenseIndex.elementAt(f);
                                        if (tuple.elementAt(0).compareTo(parts.elementAt(1)) == 0) {
                                            String rows = (String) tuple.elementAt(1);
                                            for (int x = 1; x < tempResult.size(); x++) {
                                                if (rows.compareTo(x + "") <= 0)
                                                    resultt.addElement(tempResult.elementAt(x));
                                            }
                                        }
                                    }
                                }
                            } else {
                                tempResult = whereAnalyse_i(tempResult, equations1[k]);
                                continue;
                            }
                        }
                        tempResult = resultt;
                    }
//                    if(tempResult.size()<=1)
//                        return false;
                    result[i].addAll(tempResult);
                }
            }
            Result = result[0];
            for (i = 1; i < tN.length; i++)
                Result = SqlParser.CartesianProduct(Result, result[i]);
            String afterSelect = Tool.getObjective(sql, "select(.*?)from");//得到select后面的内容
            Result = SqlParser.selectAnalyse(Result, afterSelect);
            if (Result.size() == 1) {//二维向量result第一行为字段名集合
                return false;
            }
            new Display(Result);
            return true;
        }
        return false;
    }

    /**
     * 建库
     */
    public static void createDatabase() {
        String dN = Tool.getObjective(sql, "database(.*?);");
        dN = dN.trim();
        File f = new File(ConstPool.PATH + File.separator + dN);
        f.mkdir();
        System.out.println("create new database successful!");
    }

    /**
     * 建表
     */
    public static void createTable() {
        int i = 0;
        if (SqlParser.databasePath.equals("")) {
            System.out.println("please appoint a database at first!");
            return;
        }
        sql = sql.trim();//该函数得有返回值
        String[] s = sql.split("\\s+");//按空格拆分sql语句，允许有多个空格
        s = Tool.myTrim(s);
        if (SqlParser.switchTablePath(s[2])) {//s[2]是表名，这时填充路径不妥
            System.out.println("error! The table " + "\"" + s[2] + "\"" + " is already exist");
            return;
        }
        String fieldInfos = Tool.getBracketsObjective(sql);//得到所有字段及信息
        String[] fIs = fieldInfos.split("[,]");//该表有 fIs.length 个字段
        fIs = Tool.myTrim(fIs);
        File f = new File(SqlParser.databasePath, s[2] + ".txt");
        try {
            f.createNewFile();//新建数据表文件，表名即文件名
            f = new File(SqlParser.databasePath, s[2] + "数据字典.txt");
            f.createNewFile();//新建表数据字典文件
            Vector<Vector<String>> newTable = new Vector<Vector<String>>();
            Vector<String> dd = new Vector<String>();
            Vector<String> tuple = new Vector<String>();
            dd.addElement(fIs.length + "");
            dd.addElement("");//第二行空，表示现无索引，这个第二行不空，长度为0
            for (i = 0; i < fIs.length; i++) {
                String[] temp = fIs[i].split("\\s+");
                tuple.addElement(temp[0]);
                dd.addElement(fIs[i]);
            }
            newTable.addElement(tuple);
            SqlParser.switchTablePath(s[2]);
            Tool.vectorDepositFile(newTable, SqlParser.tablePath);//存表到文件
            Tool.setFileData(dd, SqlParser.dataDictionaryPath);//存表数据字典到文件
            System.out.println("Create table " + "\"" + s[2] + "\"" + " successful!");
            System.out.println("There are " + fIs.length + " fields in this table");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 建索引，一张表的索引数上限：这张表的字段数
     */
    public static void createIndex() {
        int i = 0;
        if (SqlParser.databasePath.equals("")) {
            System.out.println("please appoint a database at first!");
            return;
        }
        sql = sql.trim();
        String[] s = sql.split("\\s+");//按空格拆分sql语句，允许有多个空格
        s = Tool.myTrim(s);
        if (!SqlParser.switchTablePath(s[4])) {//s[4]是表名，在这张表上建一个索引
            System.out.println("error! table " + "\"" + s[4] + "\"" + " is not exists,create index fail");
            return;
        }
        String indexName = Tool.getObjective(sql, "index(.*?)on");
        indexName = indexName.trim();//得到要加的索引名
        String fieldName = Tool.getBracketsObjective(sql);
        fieldName = fieldName.trim();//得到字段名，在这一字段建索引
        Vector<String> dd = Tool.getFileData(SqlParser.dataDictionaryPath);//打开这张表的数据字典
        String aboutIndex = (String) dd.elementAt(1);//获取这张表关于索引的信息
        if (aboutIndex.indexOf(indexName) != -1 || aboutIndex.indexOf(fieldName) != -1) {//这实际不对，这实际仍可加索引
            System.out.println("create index fail");//索引名不同且字段名不同才可建
            //不可能一个字段建稀疏又建稠密
            return;
        }
        File f = new File(SqlParser.databasePath, indexName + ".txt");
        try {
            f.createNewFile();//至此.txt索引文件已建好
            if (sql.indexOf("cluster") != -1)//建聚集索引(稀疏索引)
                aboutIndex = aboutIndex + indexName + "," + fieldName + "," + "cluster" + ";";
            else//建辅助索引(稠密索引)
                aboutIndex = aboutIndex + indexName + "," + fieldName + ";";
            dd.setElementAt(aboutIndex, 1);
            Tool.setFileData(dd, SqlParser.dataDictionaryPath);//数据字典已写好
            SqlParser.reloadIndexFile(s[4]);
            System.out.println("create new index successful!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 切换数据库
     */
    public static void switchDatabase() {
        String dN = Tool.getObjective(sql, "use(.*?);");
        dN = dN.trim();//得到库名
        SqlParser.databasePath = ConstPool.PATH + "\\" + dN;//已切换到dN这个库
        File f = new File(SqlParser.databasePath);
        if (!f.exists()) {
            SqlParser.databasePath = "";
            System.out.println("error! database " + "\"" + dN + "\"" + " is not exists");
        } else
            System.out.println("database changed");
    }

    /**
     * 查看所有数据库
     */
    public static void showDatabases() {
        int i = 0;
        System.out.println("all databases in myDBMS:");
        File f = new File(ConstPool.PATH);
        File[] fileLists = f.listFiles();
        for (i = 0; i < fileLists.length; i++) {
            if (fileLists[i].isDirectory())
                System.out.println(fileLists[i].getName());
        }
    }

    /**
     * 查看这一库下所有表
     */
    public static void showTables() {
        int i = 0;
        if (SqlParser.databasePath.equals("")) {
            System.out.println("please appoint a database at first!");
            return;
        }
        System.out.println("all tables in this database:");
        Vector<String> tables = new Vector<String>();//存这一库下所有表名
        File f = new File(SqlParser.databasePath);
        File[] fileLists = f.listFiles();
        for (i = 0; i < fileLists.length; i++) {
            if (fileLists[i].isFile() && fileLists[i].getName().indexOf("数据字典") != -1) {
                String temp = Tool.deleteAppointWord(fileLists[i].getName(), "数据字典");
                temp = temp.substring(0, temp.length() - 4);//去掉字符串后4位
                tables.addElement(temp);
            }
        }
        for (i = 0; i < tables.size(); i++)
            System.out.println(tables.elementAt(i));
    }

    /**
     * 查看某表的数据字典
     */
    public static void descTable() {
        int i = 0;
        if (SqlParser.databasePath.equals("")) {
            System.out.println("please appoint a database at first!");
            return;
        }
        String tN = Tool.getObjective(sql, "desc(.*?);");
        tN = tN.trim();//得到表名
        if (!SqlParser.switchTablePath(tN)) {
            System.out.println("error! table " + "\"" + tN + "\"" + " is not exists");
            return;
        }
        Vector<String> dd = Tool.getFileData(SqlParser.dataDictionaryPath);//打开表数据字典
        String fieldNumber = (String) dd.elementAt(0);
        String aboutIndex = (String) dd.elementAt(1);
        for (i = 2; i < dd.size(); i++)
            System.out.println(dd.elementAt(i) + "");
        if (aboutIndex.length() == 0)
            System.out.println("There are no indexs in table " + "\"" + tN + "\"");
        else
            System.out.println(aboutIndex);
        System.out.println("There are " + fieldNumber + " fields in table " + "\"" + tN + "\"");
    }

    /**
     * 查看某一表的全部索引文件
     */
    public static void showIndex() {
        int i = 0;
        if (SqlParser.databasePath.equals("")) {
            System.out.println("please appoint a database at first!");
            return;
        }
        String tN = Tool.getObjective(sql, "from(.*?);");
        tN = tN.trim();//得到表名
        if (!SqlParser.switchTablePath(tN)) {
            System.out.println("error! table " + "\"" + tN + "\"" + " is not exists");
            return;
        }
        Vector<String> dd = Tool.getFileData(SqlParser.dataDictionaryPath);//打开表数据字典
        String aboutIndex = (String) dd.elementAt(1);//数据字典索引信息那一行
        if (aboutIndex.length() == 0) {
            System.out.println("There are no index in table " + "\"" + tN + "\"");
            return;
        }
        String[] indexs = aboutIndex.split("[;]");
        for (i = 0; i < indexs.length; i++) {//每次循环显示表的一个索引文件
            String[] temp = indexs[i].split("[,]");
            temp = Tool.myTrim(temp);//temp[0]：索引文件名，temp[1]：字段名，temp[2]可能没有也可能为"cluster"
            File f = new File(SqlParser.databasePath + "\\" + temp[0] + ".txt");
            if (f.exists()) {
                Vector<Vector<String>> I = Tool.fileDepositVector(SqlParser.databasePath + "\\" + temp[0] + ".txt");
                new Display(I);
            }
        }
    }

    /**
     * 删除数据库
     */
    public static void dropDatabase() {
        String dN = Tool.getObjective(sql, "database(.*?);");
        dN = dN.trim();//得到库名
        File f = new File(ConstPool.PATH + "\\" + dN);
        if (!f.exists()) {
            System.out.println("database " + "\"" + dN + "\"" + " is not exists,delete fail");
            return;
        }
        Tool.deleteDir(f);//删除目录名为f的目录
        System.out.println("database " + "\"" + dN + "\"" + " delete successful!");
    }

    /**
     * 删除表，删除一张表，删数据表、数据字典、索引文件
     */
    public static void dropTable() {
        int i = 0;
        if (SqlParser.databasePath.equals("")) {
            System.out.println("please appoint a database at first!");
            return;
        }
        String tN = Tool.getObjective(sql, "table(.*?);");
        tN = tN.trim();//得到表名
        if (!SqlParser.switchTablePath(tN)) {
            System.out.println("table " + "\"" + tN + "\"" + " is not exists,delete fail");
            return;
        }
        File f = null;
        String aboutIndex = (String) Tool.getFileData(SqlParser.dataDictionaryPath).elementAt(1);//先得到表的索引信息
        if (aboutIndex.length() != 0) {//有索引才删索引
            String[] indexs = aboutIndex.split("[;]");//indexs数组每个元素为一个索引文件信息
            indexs = Tool.myTrim(indexs);
            for (i = 0; i < indexs.length; i++) {//每次循环删表的一个索引文件
                String[] temp = indexs[i].split("[,]");
                temp = Tool.myTrim(temp);
                f = new File(SqlParser.databasePath + "\\" + temp[0] + ".txt");
                f.delete();
            }
        }
        f = new File(SqlParser.databasePath);
        File[] fileLists = f.listFiles();
        for (i = 0; i < fileLists.length; i++) {
            if (fileLists[i].isFile() && (fileLists[i].getName().equals(tN + ".txt") || fileLists[i].getName().equals(tN + "数据字典.txt")))
                fileLists[i].delete();
        }
        System.out.println("table " + "\"" + tN + "\"" + " delete successful!");
    }

    /**
     * 删除表的一个索引文件
     */
    public static void dropIndex() {
        int i = 0;
        if (SqlParser.databasePath.equals("")) {
            System.out.println("please appoint a database at first!");
            return;
        }
        String tN = Tool.getObjective(sql, "on(.*?);");
        tN = tN.trim();//得到表名
        if (!SqlParser.switchTablePath(tN)) {
            System.out.println("error! table " + "\"" + tN + "\"" + " is not exists");
            return;
        }
        Vector<String> dd = Tool.getFileData(SqlParser.dataDictionaryPath);//打开表数据字典
        String aboutIndex = (String) dd.elementAt(1);
        if (aboutIndex.length() == 0) {
            System.out.println("error! delete fail,there are no index in table " + "\"" + tN + "\"");
            return;
        }
        String indexName = Tool.getObjective(sql, "index(.*?)on");
        indexName = indexName.trim();//得到要删的索引名
        String[] indexs = aboutIndex.split("[;]");
        for (i = 0; i < indexs.length; i++) {
            String[] temp = indexs[i].split("[,]");
            temp = Tool.myTrim(temp);//temp[0]：索引文件名，temp[1]：字段名，temp[2]可能没有也可能为"cluster"
            if (indexName.equals(temp[0])) {
                File f = new File(SqlParser.databasePath + "\\" + temp[0] + ".txt");
                if (f.exists()) {
                    f.delete();
                    aboutIndex = Tool.deleteAppointWord(aboutIndex, indexs[i] + ";");
                    dd.setElementAt(aboutIndex, 1);
                    Tool.setFileData(dd, SqlParser.dataDictionaryPath);//数据字典更新完毕
                    return;
                }
            }
        }
        System.out.println("error! index " + "\"" + indexName + "\"" + " is not exists in this table");
    }

    /**
     * 添加字段(一列)，添加一个字段
     */
    public static void addField() {
        if (SqlParser.databasePath.equals("")) {
            System.out.println("please appoint a database at first!");
            return;
        }
        String tN = Tool.getObjective(sql, "table(.*?)add");
        tN = tN.trim();//得到表名，仅一个表
        if (!SqlParser.switchTablePath(tN)) {
            System.out.println("error! table " + "\"" + tN + "\"" + " is not exists");
            return;
        }
        String fieldInfo = Tool.getObjective(sql, "column(.*?);");//得到新字段信息
        fieldInfo = fieldInfo.trim();//要把fieldInfo加到数据字典文件最后一行
        String[] ss = fieldInfo.split("\\s+");
        ss = Tool.myTrim(ss);
        Vector<Vector<String>> T = Tool.fileDepositVector(SqlParser.tablePath);//打开表
        Vector<String> tuple = (Vector<String>) T.elementAt(0);//得到表第一行
        if (tuple.contains(ss[0] + "")) {
            System.out.println("error! field " + "\"" + ss[0] + "\"" + " is already exist");
            return;
        }
        tuple.addElement(ss[0]);
        Tool.vectorDepositFile(T, SqlParser.tablePath);//至此表ok
        tuple = Tool.getFileData(SqlParser.dataDictionaryPath);//把表数据字典内容按行读取到向量
        int newFieldNumber = Integer.parseInt(tuple.elementAt(0) + "") + 1;
        tuple.remove(0);
        tuple.insertElementAt(newFieldNumber + "", 0);
        tuple.addElement(fieldInfo);
        Tool.setFileData(tuple, SqlParser.dataDictionaryPath);/*把泛型为String的向量重新顺序写入.txt文件(表数据字典)*/
        SqlParser.reloadIndexFile(tN);
    }

    /**
     * 删除字段(一列)
     */
    public static void dropField() {
        int i = 0, j = 0;
        String tN = Tool.getObjective(sql, "table(.*?)drop");
        tN = tN.trim();//得到表名
        if (!SqlParser.switchTablePath(tN)) {
            System.out.println("error! table " + "\"" + tN + "\"" + " is not exists");
            return;
        }
        Vector<Vector<String>> T = Tool.fileDepositVector(SqlParser.tablePath);//打开表
        Vector<String> tuple = (Vector<String>) T.elementAt(0);
        String fields = Tool.getObjective(sql, tN + "(.*?);");
        fields = Tool.deleteAppointWord(fields, "drop column");
        fields = fields.trim();//至此fields字符串仅为所有要删的字段
        String[] dFs = fields.split("[,]");//该数组的每个元素都是一个要删的字段名
        dFs = Tool.myTrim(dFs);
        Vector<String> dd = (Vector<String>) Tool.getFileData(SqlParser.dataDictionaryPath);
        String aboutIndex = (String) dd.elementAt(1);//得到表数据字典关于索引的那一行
        Vector<String> indexFields = new Vector<String>();//存所有索引字段
        if (aboutIndex.length() != 0) {
            String[] indexs = aboutIndex.split("[;]");//indexs数组每个元素为一个索引文件信息
            for (i = 0; i < indexs.length; i++)//每次循环更新表的一个索引文件
            {
                String[] temp = indexs[i].split("[,]");
                temp = Tool.myTrim(temp);//temp[0]：索引文件名，temp[1]：字段名，temp[2]可能没有也可能为"cluster"
                indexFields.addElement(temp[1]);
            }
        }
        for (i = 0; i < dFs.length; i++)//遍历要删的字段
        {
            if (indexFields.contains(dFs[i])) {
                System.out.println("error! field " + "\"" + dFs[i] + "\"" + " is index field,can not delete");
                return;//不能删除建了索引的字段
            }
            if (!tuple.contains(dFs[i] + "")) {
                System.out.println("error! field " + "\"" + dFs[i] + "\"" + " is not this table's field");
                return;
            } else {
                for (j = 2; j < dd.size(); j++)//行遍历数据字典
                {
                    String row = (String) dd.elementAt(j);//数据字典的一行
                    if (row.indexOf(dFs[i]) != -1)//如果数据字典这一行包含这一字段
                    {
                        dd.remove(j);//删除数据字典的这一行
                        break;
                    }
                }
            }
            int fieldColIndex = SqlParser.getColIndex(T, dFs[i]);//至此得到要删字段的列索引
            for (j = 0; j < T.size(); j++)//行遍历数据表
            {
                tuple = (Vector<String>) T.elementAt(j);//接收表的每一行
                if (tuple.size() > fieldColIndex)
                    tuple.remove(fieldColIndex);
            }
        }
        String s = (String) dd.elementAt(0);
        int fieldNumber = Integer.parseInt(s);
        fieldNumber = fieldNumber - dFs.length;//要删 dFs.length 个字段
        dd.remove(0);
        dd.insertElementAt(fieldNumber + "", 0);
        Tool.setFileData(dd, SqlParser.dataDictionaryPath);//至此数据字典删除完成
        Tool.vectorDepositFile(T, SqlParser.tablePath);//至此表的列删除完成
        SqlParser.reloadIndexFile(tN);
    }

    /**
     * 建用户，初始该用户无权限
     */
    public static void createUser() {
        int i = 0;
        String newUserID = Tool.getObjective(sql, "user(.*?)identified");
        newUserID = newUserID.trim();//得到新用户id
        if (newUserID.length() == 0) {
            System.out.println("error! user id can not empty");
            return;
        }
        if (SqlParser.repetition(ConstPool.USERS_PATH, newUserID) != -1) {
            System.out.println("error! Your user id are repeat to client2 user's user id");
            return;
        }
        String newUserPassword = Tool.getObjective(sql, "by(.*?);");
        newUserPassword = newUserPassword.trim();//得到新用户密码
        if (newUserID.length() == 0) {
            System.out.println("error! user password can not empty");
            return;
        }
        Vector<String> users = Tool.getFileData(ConstPool.USERS_PATH);
        users.addElement(newUserID + " " + newUserPassword);
        Tool.setFileData(users, ConstPool.USERS_PATH);
        Vector<Vector<String>> PowerTable = Tool.fileDepositVector(ConstPool.POWER_TABLE);
        Vector<String> tuple = new Vector<String>();
        tuple.addElement(newUserID);
        for (i = 1; i < currentUser.size(); i++)
            tuple.addElement("0");
        PowerTable.addElement(tuple);
        Tool.vectorDepositFile(PowerTable, ConstPool.POWER_TABLE);
        System.out.println("create new user successful!");
    }

    /**
     * 删除用户
     */
    public static void dropUser() {
        int i = 0, j = 0;
        String userID = Tool.getObjective(sql, "user(.*?);");
        userID = userID.trim();//得到要删除用户的用户id
        if (SqlParser.repetition(ConstPool.USERS_PATH, userID) == -1) {
            System.out.println("error! this user is not exists");
            return;
        }
        Vector<String> users = Tool.getFileData(ConstPool.USERS_PATH);
        for (i = 0; i < users.size(); i++) {
            String row = (String) users.elementAt(i);
            String[] idPw = row.split("\\s+");
            idPw = Tool.myTrim(idPw);
            if (userID.equals(idPw[0])) {
                users.remove(i);
                Tool.setFileData(users, ConstPool.USERS_PATH);//存回
                Vector<Vector<String>> PowerTable = Tool.fileDepositVector(ConstPool.POWER_TABLE);
                for (j = 1; j < PowerTable.size(); j++) {
                    Vector<String> tuple = PowerTable.elementAt(j);
                    if (tuple.elementAt(0).compareTo(userID) == 0) {
                        PowerTable.remove(j);
                        Tool.vectorDepositFile(PowerTable, ConstPool.POWER_TABLE);
                        System.out.println("delete user " + "\"" + userID + "\"" + " successful!");
                        return;
                    }
                }
            }
        }
    }

    /**
     * 授权
     */
    public static void grantPower() {
        int i = 0, j = 0;
        String userID = Tool.getObjective(sql, "to(.*?);");
        userID = userID.trim();//得到用户id
        if (SqlParser.repetition(ConstPool.USERS_PATH, userID) == -1) {
            System.out.println("error! this user is not exists");
            return;
        }
        String powerName = Tool.getObjective(sql, "grant(.*?)to");
        powerName = powerName.trim();
        String[] pN = powerName.split("[,]");
        pN = Tool.myTrim(pN);//数组pN中每个元素为一个权限名
        Vector<Vector<String>> powerT = Tool.fileDepositVector(ConstPool.POWER_TABLE);
        for (i = 1; i < powerT.size(); i++) {
            Vector<String> tuple = (Vector<String>) powerT.elementAt(i);
            if (tuple.elementAt(0).compareTo(userID) == 0) {
                for (j = 0; j < pN.length; j++) {//每次循环给该用户授予一个权限
                    if (SqlParser.getColIndex(pN[j]) != -1)
                        tuple.setElementAt("1", SqlParser.getColIndex(pN[j]));
                    else {
                        System.out.println("grant power fail");
                        return;
                    }
                }
                Tool.vectorDepositFile(powerT, ConstPool.POWER_TABLE);
                System.out.println("grant power successful!");
                return;
            }
        }
    }

    /**
     * 撤销权限
     */
    public static void revokePower() {
        int i = 0, j = 0;
        String userID = Tool.getObjective(sql, "from(.*?);");
        userID = userID.trim();//得到用户id
        if (SqlParser.repetition(ConstPool.USERS_PATH, userID) == -1) {
            System.out.println("error! this user is not exists");
            return;
        }
        String powerName = Tool.getObjective(sql, "revoke(.*?)from");
        powerName = powerName.trim();
        String[] pN = powerName.split("[,]");
        pN = Tool.myTrim(pN);//数组pN中每个元素为一个权限名
        Vector<Vector<String>> powerT = Tool.fileDepositVector(ConstPool.POWER_TABLE);
        for (i = 1; i < powerT.size(); i++) {
            Vector<String> tuple = (Vector<String>) powerT.elementAt(i);
            if (tuple.elementAt(0).compareTo(userID) == 0) {
                for (j = 0; j < pN.length; j++) {//每次循环撤销该用户一个权限
                    if (SqlParser.getColIndex(pN[j]) != -1)
                        tuple.setElementAt("0", SqlParser.getColIndex(pN[j]));
                    else {
                        System.out.println("revoke power fail");
                        return;
                    }
                }
                Tool.vectorDepositFile(powerT, ConstPool.POWER_TABLE);
                System.out.println("revoke power successful!");
                return;
            }
        }
    }

    /**
     * 管理员查看所有用户权限表
     */
    public static void showPowerTable() {
        new Display(Tool.fileDepositVector(ConstPool.POWER_TABLE));
    }

    public static void showLog() {
        LinkedList<String> allLog = Test.getLog();
        if (allLog.size() == 0) {
            System.out.println("You had never input any statement!");
        }
        for (String log : allLog) {
            System.out.println(log);
        }
    }

    /*判断某值和某一列的所有值是否有重复的，若有重复返回重复的行索引13*/
    public static int repetition(String tablePath, String fieldName, String data) {
        Vector<Vector<String>> T = Tool.fileDepositVector(tablePath);//应先判断文件空不空
        if (T.size() == 1)
            return -1;
        int i = 0, fieldColIndex = SqlParser.getColIndex(T, fieldName);
        for (i = 1; i < T.size(); i++) {
            Vector<String> tuple = (Vector<String>) T.elementAt(i);
            if ((tuple.elementAt(fieldColIndex) + "").equals(data))
                return i;
        }
        return -1;
    }

    /*辅助索引的辅助方法，判断I第一列值和data是否有重复的，若有重复返回重复的行索引12*/
    public static int Repetition(Vector<Vector<String>> I, String data) {
        int i = 0;
        if (I.size() == 1)
            return -1;
        for (i = 1; i < I.size(); i++) {
            Vector<String> tuple = (Vector<String>) I.elementAt(i);
            if ((tuple.elementAt(0) + "").equals(data))
                return i;
        }
        return -1;
    }

    /**
     * 判断用户ID是否有重复的
     *
     * @param USERS_PATH 用户id文件
     * @param newUserID 新用户id
     * @return
     */
    public static int repetition(String USERS_PATH, String newUserID) {
        Vector<String> users = Tool.getFileData(USERS_PATH);//应先判断文件空不空
        if (users.size() == 0)
            return -1;
        int i = 0;
        for (i = 0; i < users.size(); i++) {
            String row = (String) users.elementAt(i);
            String[] idPw = row.split("\\s+");
            idPw = Tool.myTrim(idPw);
            if (newUserID.equals(idPw[0]))
                return i;
        }
        return -1;
    }

    /**
     * select辅助方法 - from后的表的分析
     *
     * @param tNs 表名集合
     * @return 这些表的笛卡儿积
     */
    public static Vector<Vector<String>> tableAnalyse(String tNs) {
        int i = 0, j = 0;
        Vector<Vector<String>> newT = new Vector<Vector<String>>();//存这些表的笛卡儿积
        String[] tN = tNs.split("[,]");//数组tN中每个元素为一个表名
        tN = Tool.myTrim(tN);
        for (i = 0; i < tN.length; i++) {//分析from后内容合不合法
            if (!SqlParser.switchTablePath(tN[i])) {
                Vector<String> tuple = new Vector<String>();
                tuple.addElement(tN[i]);
                newT.addElement(tuple);
                return newT;
            }
        }
        SqlParser.dds = new Vector<String>();
        SqlParser.aboutIndexs = new Vector<String>();//清空原多表
        Vector<Vector<String>> tempT;//接收tN数组中的一个表
        Vector<String> tempDD;//接收tN数组中的一个表的数据字典
        if (tN.length == 1) {//单表
            SqlParser.switchTablePath(tN[0]);//传参前已验证表存在
            newT = Tool.fileDepositVector(SqlParser.tablePath);
            tempDD = Tool.getFileData(SqlParser.dataDictionaryPath);
            SqlParser.aboutIndexs.addElement(tN[0]);
            SqlParser.aboutIndexs.addElement(tempDD.elementAt(1) + "");
            for (i = 2; i < tempDD.size(); i++)
                dds.addElement(tempDD.elementAt(i) + "");
        } else {
            SqlParser.switchTablePath(tN[0]);
            newT = Tool.fileDepositVector(SqlParser.tablePath);
            tempDD = Tool.getFileData(SqlParser.dataDictionaryPath);
            SqlParser.aboutIndexs.addElement(tN[0]);
            SqlParser.aboutIndexs.addElement(tempDD.elementAt(1) + "");
            for (i = 2; i < tempDD.size(); i++)
                dds.addElement(tempDD.elementAt(i) + "");//用tN[0]初始化新表
            for (i = 1; i < tN.length; i++) {
                SqlParser.switchTablePath(tN[i]);
                tempT = Tool.fileDepositVector(SqlParser.tablePath);
                newT = SqlParser.CartesianProduct(newT, tempT);
                tempDD = Tool.getFileData(SqlParser.dataDictionaryPath);
                SqlParser.aboutIndexs.addElement(tN[i]);
                SqlParser.aboutIndexs.addElement(tempDD.elementAt(1) + "");
                for (j = 2; j < tempDD.size(); j++)
                    dds.addElement(tempDD.elementAt(j) + "");
            }
        }
        return newT;
    }

    /**
     * @param T1
     * @param T2
     * @return 表T1、T2做笛卡尔积后的二维表
     */
    public static Vector<Vector<String>> CartesianProduct(Vector<Vector<String>> T1, Vector<Vector<String>> T2) {
        int i = 0, j = 0;
        Vector<Vector<String>> newT = new Vector<Vector<String>>();
        Vector<String> newTuple = new Vector<String>();//该变量针对新表的一行
        Vector<String> Tuple1 = (Vector<String>) T1.elementAt(0);
        Vector<String> Tuple2 = (Vector<String>) T2.elementAt(0);
        newTuple.addAll(Tuple1);
        newTuple.addAll(Tuple2);
        newT.addElement(newTuple);//新表字段域已加好
        for (i = 1; i < T1.size(); i++) {
            Tuple1 = (Vector<String>) T1.elementAt(i);
            for (j = 1; j < T2.size(); j++) {
                newTuple = new Vector<String>();
                newTuple.addAll(Tuple1);
                Tuple2 = (Vector<String>) T2.elementAt(j);
                newTuple.addAll(Tuple2);
                newT.addElement(newTuple);//新表新增一行
            }
        }
        return newT;
    }

    /**
     * where子句分析(仅删改查时调用)
     * 支持：字段名=值
     * 字段名=值 and 字段名=值
     * 字段名=值 or 字段名=值
     * 字段名=值 or 字段名=值 and 字段名=值      字段名=值 and 字段名=值 or 字段名=值  (应先算and)
     * 字段名=字段名的等值连接
     * 不支持：嵌套查询(用查询树)
     * 含括号，如：(字段名=值 or 字段名=值) and 字段名=值   (先分析()里内容)
     *
     * @param oldTs
     * @param afterWhere
     * @return 选择好的表
     */
    public static Vector<Vector<String>> whereAnalyse(Vector<Vector<String>> oldTs, String afterWhere) {
        int i = 0, j = 0;
        Vector<Vector<String>> result = new Vector<Vector<String>>();//存选择好的表(符合where子句的)
        if (afterWhere.indexOf("(") == -1) {//若where子句无括号
            String[] equations0 = afterWhere.split("or");//equations0中每个元素要么为一简单式，要么仅含and
            for (i = 0; i < equations0.length; i++) {
                String[] equations1 = equations0[i].split("and");//equations1数组每个元素为一个简单式
                Vector<Vector<String>> tempResult = oldTs;
                for (j = 0; j < equations1.length; j++)
                    tempResult = SqlParser.whereAnalyse_i(tempResult, equations1[j]);
                result.addAll(tempResult);
            }
        }
        return result;
    }

    /**
     * 表根据简单式得到选择结果
     *
     * @param oldTs
     * @param equation 简单式
     * @return 选择结果，没查到也返回一行字段行
     */
    public static Vector<Vector<String>> whereAnalyse_i(Vector<Vector<String>> oldTs, String equation) {
        int i = 0;
        equation = equation.trim();
        Vector<Vector<String>> result = new Vector<Vector<String>>();
        Vector<String> dd;
        if (dds.size() == 0 && aboutIndexs.size() == 0)//分析删或改操作的where子句
            dd = Tool.getFileData(SqlParser.dataDictionaryPath);//接收表数据字典
        else//分析查操作的where子句
            dd = dds;
        result.addElement(oldTs.elementAt(0));
        Vector<String> parts = fieldMatching(dd, equation);
        if (parts.size() == 0) {
        } else if (parts.size() == 4) {//字段=字段
            String field1Name = (String) parts.elementAt(0);
            int field1ColIndex = getColIndex(oldTs, field1Name);
            String field2Name = (String) parts.elementAt(1);
            int field2ColIndex = getColIndex(oldTs, field2Name);
            for (i = 1; i < oldTs.size(); i++) {//行遍历表
                Vector<String> tuple = (Vector<String>) oldTs.elementAt(i);//接收表的每一行
                if (tuple.elementAt(field1ColIndex).compareTo(tuple.elementAt(field2ColIndex)) == 0)
                    result.addElement(tuple);
            }
        } else {
            String fieldName = (String) parts.elementAt(0);
            String fieldValue = (String) parts.elementAt(1);
            String operator = (String) parts.elementAt(2);
            int fieldColIndex = getColIndex(oldTs, fieldName);
            if (operator.equals("=")) {
                for (i = 1; i < oldTs.size(); i++) {//行遍历表
                    Vector<String> tuple = (Vector<String>) oldTs.elementAt(i);//接收表的每一行
                    if (tuple.elementAt(fieldColIndex).compareTo(fieldValue) == 0)
                        result.addElement(tuple);
                }
            } else if (operator.equals(">")) {
                for (i = 1; i < oldTs.size(); i++) {//行遍历表
                    Vector<String> tuple = (Vector<String>) oldTs.elementAt(i);//接收表的每一行
                    if (tuple.elementAt(fieldColIndex).compareTo(fieldValue) > 0)
                        result.addElement(tuple);
                }
            } else if (operator.equals("<")) {
                for (i = 1; i < oldTs.size(); i++) {//行遍历表
                    Vector<String> tuple = (Vector<String>) oldTs.elementAt(i);//接收表的每一行
                    if (tuple.elementAt(fieldColIndex).compareTo(fieldValue) < 0)
                        result.addElement(tuple);
                }
            }
        }
        return result;
    }

    /**
     * select辅助方法 - 投影
     *
     * @param oldTs
     * @param afterSelect
     * @return 查询操作的最终查询结果
     */
    public static Vector<Vector<String>> selectAnalyse(Vector<Vector<String>> oldTs, String afterSelect) {
        int i = 0, j = 0;
        String[] fields = afterSelect.split("[,]");//fields数组的每一个元素为要查的列，即留下的列
        fields = Tool.myTrim(fields);
        if (fields.length == 1 && fields[0].equals("*")) {
        } else {
            Vector<Integer> indexs = SqlParser.getColIndex(oldTs, fields);//得到留下的列列索引集合
            if (indexs.size() == 0) {
                Vector<String> tuple = (Vector<String>) oldTs.elementAt(0);
                oldTs = new Vector<Vector<String>>();
                oldTs.addElement(tuple);
            } else {
                oldTs = SqlParser.RemoveRepetition(oldTs);//去重
                for (i = 0; i < oldTs.size(); i++) {//行遍历表
                    Vector<String> tuple = (Vector<String>) oldTs.elementAt(i);//表的一行
                    Vector<String> removeItems = new Vector<String>();//存这一行要移除的元素
                    for (j = 0; j < tuple.size(); j++) {
                        if (!indexs.contains(j)) {
                            if (tuple.size() > j)
                                removeItems.addElement(tuple.elementAt(j) + "");
                        }
                    }
                    tuple.removeAll(removeItems);
                }
            }
        }
        return SqlParser.RemoveRepetition(oldTs);
    }

    /*得到字段名列索引10*/
    public static int getColIndex(Vector<Vector<String>> T, String fieldName) {
        int i = 0;
        Vector<String> tuple = (Vector<String>) T.elementAt(0);
        for (i = 0; i < tuple.size(); i++) {
            if ((tuple.elementAt(i) + "").equals(fieldName))
                return i;
        }
        return -1;//该表无该字段
    }

    /*select辅助方法*/
    /*得到字段名集合列索引集合11*/
    public static Vector<Integer> getColIndex(Vector<Vector<String>> Ts, String[] fieldNames) {
        int i = 0;
        Vector<Integer> indexs = new Vector<Integer>();
        Vector<String> tuple = (Vector<String>) Ts.elementAt(0);
        for (i = 0; i < fieldNames.length; i++) {//每次循环得到一个字段的列索引
            if (tuple.indexOf(fieldNames[i]) != -1)
                indexs.addElement(tuple.indexOf(fieldNames[i]));
            else {
                indexs = new Vector<Integer>();
                return indexs;
            }
        }
        return indexs;//该表无该字段
    }

    public static int getColIndex(String powerName) {
        int i = 0;
        Vector<Vector<String>> POWER_TABLE = Tool.fileDepositVector(ConstPool.POWER_TABLE);
        Vector<String> tuple = (Vector<String>) POWER_TABLE.elementAt(0);
        for (i = 1; i < tuple.size(); i++) {
            if ((tuple.elementAt(i) + "").equalsIgnoreCase(powerName))
                return i;
        }
        return -1;
    }

    /**
     * 113
     * 判断当前用户是否有这个权限
     *
     * @return
     */
    public static boolean power() {
        if (contains(SqlParser.sql, "(insert)(.+)(into)(.+)(values)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("insert")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(delete)(.+)(from)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("delete")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(update)(.+)(set)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("update")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(select)(.+)(from)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("select")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(create)(.+)(database)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("createDatabase")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(create)(.+)(table)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("createTable")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(create)(.+)(index)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("createIndex")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(use)( )(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("useDatabase")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(show)(.+)(databases)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("showDatabases")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(show)(.+)(tables)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("showTables")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(desc)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("showTableDataDictionary")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(show)(.+)(index)(.+)(from)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("showIndex")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(drop)(.+)(database)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("dropDatabase")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(drop)(.+)(table)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("dropTable")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(drop)(.+)(index)(.+)(on)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("dropIndex")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(alter)(.+)(table)(.+)(add)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("addField")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(alter)(.+)(table)(.+)(drop)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("dropField")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(create)(.+)(user)(.+)(identified)(.+)(by)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("createUser")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(drop)(.+)(user)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("dropUser")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(grant)(.+)(to)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("grantPower")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(revoke)(.+)(from)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("revokePower")).compareTo("0") == 0)
                return false;
        } else if (contains(SqlParser.sql, "(show)(.+)(powertable)(.+)")) {
            if (currentUser.elementAt(SqlParser.getColIndex("showPowerTable")).compareTo("0") == 0)
                return false;
        }
        return true;
    }

    /**
     * 判断一个简单式在参数dd中是否合法(字段名是否存在以及和字段值是否相匹配)
     *
     * @param dd
     * @param equation
     * @return 简单式的3个拆分项
     */
    public static Vector<String> fieldMatching(Vector<String> dd, String equation) {
        int i = 0, j = 0;
        Vector<String> parts = new Vector<String>();//存一个简单式的拆分项
        String fieldType = null;//第一个字段或可能的第二个字段的数据类型
        equation = equation.trim();
        String[] leftRight = equation.split("[>=<]");
        leftRight = Tool.myTrim(leftRight);//leftRight[0]：字段名，leftRight[1]：值/字段名
        for (i = 0; i < dd.size(); i++) {//行遍历数据字典dd
            String tuple = (String) dd.elementAt(i);
            if (tuple.length() <= 1 || tuple.indexOf(";") != -1)//dd可能是dds或有行数索引信息的dd
                continue;
            else {
                String[] info = tuple.split("\\s+");//为了获取数据字典这一行的字段名
                info = Tool.myTrim(info);
                if (info[0].equals(leftRight[0])) {//若就是该字段
                    if (SqlParser.fieldMatching(info[1], leftRight[1])) {//字段=值型
                        parts.addElement(leftRight[0]);
                        parts.addElement(leftRight[1]);
                        for (i = 0; i < leftRight.length; i++)
                            equation = Tool.deleteAppointWord(equation, leftRight[i]);
                        equation = equation.trim();
                        parts.addElement(equation);
                        return parts;
                    }
                }
            }
        }
        if (parts.size() == 0) {
            int matchCount = 0;
            for (i = 0; i < dd.size(); i++) {//行遍历数据字典dd
                String tuple = (String) dd.elementAt(i);
                if (tuple.length() <= 1 || tuple.indexOf(";") != -1)//dd可能是dds或有行数索引信息的dd
                    continue;
                else {
                    String[] info = tuple.split("\\s+");//为了获取数据字典这一行的字段名
                    info = Tool.myTrim(info);
                    if (info[0].equals(leftRight[0])) {//若就是该字段
                        matchCount++;
                    }
                    if (info[0].equals(leftRight[1])) {//若就是该字段
                        matchCount++;
                    }
                }
            }
            if (matchCount == 2) {//若就是该字段
                parts.addElement(leftRight[0]);
                parts.addElement(leftRight[1]);
                for (i = 0; i < leftRight.length; i++)
                    equation = Tool.deleteAppointWord(equation, leftRight[i]);
                equation = equation.trim();
                parts.addElement(equation);
                parts.addElement("0");
                System.out.println(parts);
                return parts;
            }
        }
        return new Vector<String>();
    }

    /**
     * @param fieldType 字段类型
     * @param value     字段值
     * @return 字段类型和字段值是否相匹配
     */
    public static boolean fieldMatching(String fieldType, String value) {
        Pattern pat = Pattern.compile("^[-\\+]?[\\d]*$");
        if ((fieldType.equals("int") || fieldType.equals("double") || fieldType.equals("long")) && pat.matcher(value).matches())//数值型
            return true;
        else if (fieldType.equals("string") && value.startsWith("'") && value.endsWith("'"))//字符串型
            return true;
        else if (fieldType.equals("char") && value.length() == 1)//字符型
            return true;
        else
            return false;
    }

    /*判断字段值是否因主键而冲突17*/
    public static boolean primaryKeyConflict(Vector<String> dd, String equation) {
        int i = 0;
        equation = equation.trim();
        String[] fNvalue = equation.split("[>=<]");
        fNvalue = Tool.myTrim(fNvalue);//fNvalue[0]：字段名，fNvalue[1]：值
        for (i = 2; i < dd.size(); i++) {
            String tuple = (String) dd.elementAt(i);
            String[] info = tuple.split("\\s+");//为了获取数据字典这一行的字段名
            info = Tool.myTrim(info);
            if (info[0].equals(fNvalue[0])) {//若就是该字段
                if (tuple.indexOf("primary key") != -1 && SqlParser.repetition(SqlParser.tablePath, fNvalue[0], fNvalue[1]) != -1)
                    return true;//冲突
            }
        }
        return false;
    }

    /**
     * 改操作辅助方法，改oldT列所有数据
     *
     * @param oldT
     * @param equations 等式集合
     */
    public static void updateTable(Vector<Vector<String>> oldT, String equations) {
        int i = 0, j = 0;
        equations = equations.trim();
        String[] colNum = equations.split("[,]");//该数组每个元素都是一个简单式
        colNum = Tool.myTrim(colNum);
        Vector<String> dd = Tool.getFileData(SqlParser.dataDictionaryPath);
        for (i = 0; i < colNum.length; i++) {//每次循环改一列
            if (SqlParser.primaryKeyConflict(dd, colNum[i])) {
            } else {
                Vector<String> parts = SqlParser.fieldMatching(dd, colNum[i]);
                if (parts.size() == 0) {
                } else {
                    int fieldColIndex = SqlParser.getColIndex(oldT, parts.elementAt(0) + "");
                    String newValue = parts.elementAt(1) + "";
                    for (j = 1; j < oldT.size(); j++) {
                        Vector<String> tuple = (Vector<String>) oldT.elementAt(j);
                        if (tuple.size() > fieldColIndex)
                            tuple.setElementAt(newValue, fieldColIndex);
                    }
                }
            }
        }
    }

    /**
     * 表去重
     *
     * @param oldT
     * @return nonRepetitionT
     */
    public static Vector<Vector<String>> RemoveRepetition(Vector<Vector<String>> oldT) {
        int i = 0;
        Vector<Vector<String>> nonRepetitionT = new Vector<Vector<String>>();//去重后的表
        Vector<String> tuple = (Vector<String>) oldT.elementAt(0);
        nonRepetitionT.addElement(tuple);
        for (i = 1; i < oldT.size(); i++) {//行遍历表
            tuple = (Vector<String>) oldT.elementAt(i);//表的一行元组
            if (nonRepetitionT.indexOf(tuple) == -1)
                nonRepetitionT.addElement(tuple);
        }
        return nonRepetitionT;
    }

    /**
     * 将表T按fieldName字段值排序，建索引时调用
     *
     * @param T
     * @param fieldName
     * @return sortedT
     */
    public static Vector<Vector<String>> sort(Vector<Vector<String>> T, String fieldName) {
        int i = 0, j = 0, k = 0, fieldColIndex = SqlParser.getColIndex(T, fieldName);
        Vector<Vector<String>> sortedT = new Vector<Vector<String>>();
        Vector<String> tuple = (Vector<String>) T.elementAt(0);
        sortedT.addElement(tuple);
        String[][] Tdata = new String[T.size() - 1][tuple.size()];//T表数据
        for (i = 1, k = 0; i < T.size(); i++, k++) {
            tuple = (Vector<String>) T.elementAt(i);
            for (j = 0; j < tuple.size(); j++) {
                if (tuple.size() > j)
                    Tdata[k][j] = tuple.elementAt(j) + "";
            }
        }//T内容已至Tdata
        for (i = 0; i < Tdata.length - 1; ++i) {
            k = i;
            String[] t;
            for (j = i + 1; j <= Tdata.length - 1; ++j) {
                if (Tdata[j][fieldColIndex].compareTo(Tdata[k][fieldColIndex]) < 0)//从小到大排序
                    k = j;//compareTo是字符串比，323<40
            }
            if (k != i) {
                t = Tdata[i];
                Tdata[i] = Tdata[k];
                Tdata[k] = t;
            }
        }//二维数组已排好序(简单选择排序)
        for (i = 0; i < Tdata.length; i++) {
            String[] t = Tdata[i];
            tuple = new Vector<String>();
            for (j = 0; j < t.length; j++)
                tuple.addElement(Tdata[i][j]);
            sortedT.addElement(tuple);
        }
        return sortedT;
    }

    /**
     * 重装载表的索引文件
     *
     * @param tableName
     */
    public static void reloadIndexFile(String tableName) {
        int i = 0, j = 0;
        if (!SqlParser.switchTablePath(tableName))
            return;
        Vector<String> dd = Tool.getFileData(SqlParser.dataDictionaryPath);//打开表数据字典
        String aboutIndex = (String) dd.elementAt(1);//得到表数据字典关于索引的一行
        if (aboutIndex.length() == 0)
            return;//有索引文件才更新
        String[] indexs = aboutIndex.split("[;]");//indexs数组每个元素为一个索引文件信息
        Vector<Vector<String>> newI = new Vector<Vector<String>>();//索引表
        Vector<Vector<String>> T = (Vector<Vector<String>>) Tool.fileDepositVector(SqlParser.tablePath);//打开表
        for (i = 0; i < indexs.length; i++)//每次循环建/更新表的一个索引文件
        {//循环体内一定避免i值变化影响循环次数，最好for(int j...)，前提j出循环就不用了
            String[] temp = indexs[i].split("[,]");
            temp = Tool.myTrim(temp);//temp[0]：索引文件名，temp[1]：字段名，temp[2]可能没有也可能为"cluster"
            if (indexs[i].indexOf("cluster") != -1) {
                newI = new Vector<Vector<String>>();
                T = SqlParser.sort(T, temp[1]);//表按非键属性数值类型值排好序
                Tool.vectorDepositFile(T, SqlParser.tablePath);
                int fieldColIndex = SqlParser.getColIndex(T, temp[1]);
                String field = "";//这一字段一类相同值
                Vector<String> row0 = new Vector<String>();
                row0.addElement(temp[1]);
                row0.addElement("sparseIndex(聚集索引)(稀疏索引)");
                newI.addElement(row0);
                for (j = 1; j < T.size(); j++) {
                    Vector<String> tuple = (Vector<String>) T.elementAt(j);//接收数据表T的每一行元组
                    String ss = (String) tuple.elementAt(fieldColIndex);//ss接收每一行的这一字段的值
                    if (!ss.equals(field)) {
                        Vector<String> vI = new Vector<String>();//索引表的新一行
                        vI.addElement(ss);
                        vI.addElement(j + "");
                        newI.addElement(vI);
                        field = ss;
                    }
                }
            } else {//建索引索引文件初始为空，更新索引索引文件至少有一行，建和更新还是不同
                newI = new Vector<Vector<String>>();
                int fieldColIndex = SqlParser.getColIndex(T, temp[1]);
                Vector<String> row0 = new Vector<String>();
                row0.addElement(temp[1]);
                row0.addElement("denseIndex(辅助索引)(稠密索引)");
                newI.addElement(row0);
                for (j = 1; j < T.size(); j++) {
                    Vector<String> tuple = (Vector<String>) T.elementAt(j);//接收数据表T的每一行元组
                    String ss = (String) tuple.elementAt(fieldColIndex);//ss接收每一行的这一字段的值
                    if (SqlParser.Repetition(newI, ss) == -1) {
                        Vector<String> vI = new Vector<String>();//索引表的新一行
                        vI.addElement(ss);
                        vI.addElement(j + " ");
                        newI.addElement(vI);
                    } else {
                        int c = SqlParser.Repetition(newI, ss);
                        tuple = (Vector<String>) newI.elementAt(c);
                        newI.remove(c);//
                        String a = tuple.elementAt(1);
                        tuple.remove(1);
                        a = a + j + " ";
                        tuple.addElement(a);
                        newI.insertElementAt(tuple, c);//
                    }
                }
            }
            Tool.vectorDepositFile(newI, SqlParser.databasePath + "\\" + temp[0] + ".txt");
        }
    }

    public static boolean switchTablePath(String tableName) {
        if (SqlParser.databasePath.equals(""))
            return false;
        SqlParser.tablePath = SqlParser.databasePath + "\\" + tableName + ".txt";
        File f = new File(SqlParser.tablePath);
        if (f.exists()) {//若表存在
            SqlParser.dataDictionaryPath = SqlParser.databasePath + "\\" + tableName + "数据字典.txt";
            return true;
        }
        SqlParser.tablePath = "";
        SqlParser.dataDictionaryPath = "";
        return false;
    }

    /**
     * @param tableName
     * @return sparseIndex(聚集索引)(稀疏索引)表
     */
    public static Vector<Vector<String>> getSparseIndex(String tableName) {
        Vector<Vector<String>> SparseIndex = new Vector<Vector<String>>();
        if (!SqlParser.switchTablePath(tableName)) {
        } else {
            Vector<String> dd = Tool.getFileData(SqlParser.dataDictionaryPath);
            String aboutIndex = (String) dd.elementAt(1);
            if (aboutIndex.length() == 0)
                return SparseIndex;
            String[] index = aboutIndex.split("[;]");//index数组每个元素为一个索引信息
            for (int i = 0; i < index.length; i++) {
                if (index[i].indexOf("cluster") != -1) {
                    String[] items = index[i].split("[,]");
                    SparseIndex = Tool.fileDepositVector(SqlParser.databasePath + "\\" + items[0] + ".txt");
                }
            }
        }
        return SparseIndex;
    }

    /**
     * @param tableName
     * @return denseIndex(辅助索引)(稠密索引)表
     */
    public static Vector<Vector<String>> getDenseIndex(String tableName) {
        Vector<Vector<String>> DenseIndex = new Vector<Vector<String>>();
        if (!SqlParser.switchTablePath(tableName)) {
        } else {
            Vector<String> dd = Tool.getFileData(SqlParser.dataDictionaryPath);
            String aboutIndex = (String) dd.elementAt(1);
            if (aboutIndex.length() == 0)
                return DenseIndex;
            String[] index = aboutIndex.split("[;]");//index数组每个元素为一个索引信息
            for (int i = 0; i < index.length; i++) {
                if (index[i].indexOf("cluster") == -1) {
                    String[] items = index[i].split("[,]");
                    DenseIndex = Tool.fileDepositVector(SqlParser.databasePath + "\\" + items[0] + ".txt");
                }
            }
        }
        return DenseIndex;
    }
}