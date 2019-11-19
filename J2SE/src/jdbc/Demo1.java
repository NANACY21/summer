package jdbc;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
/**
 * jdbc java为所有db写的接口 对所有数据库通用的解决方案
 * 数据库厂商实现这些接口 对扩展开放
 */
public class Demo1 {
    public static void main(String[] args) {
        /*
        mysql中的日期类型 -- java中sql包下的日期

        datetime -- String（仅限mysql）
        time
        date
        timestamp -- java.util.Date
         */
//        ResultSet rs = st.executeQuery("select pdate from t_timestamp");
//        if(rs.next()){
//            Date  ts =rs.getTimestamp("pdate");
//            System.out.println(ts);
//        }


        Timestamp ts = new Timestamp(12345678);
        Date d = new Date();
        ts.setTime(d.getTime());
        String sql  ="insert into t_timestamp(pdate) values('"+ts+"')";
        //System.out.println(st.executeUpdate(sql));
    }
}
