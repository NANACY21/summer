package base.array;
public class MatchException extends Exception {
    private static final long serialVersionUID = -1016120285284545981L;

    public String toString() {
        return "计算矩阵A、矩阵B乘积的前提：矩阵A列数 = 矩阵B行数";
    }
}
