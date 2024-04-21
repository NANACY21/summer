package array;

/**
 * 自定义异常Error，多发生在业务逻辑层
 */
public class NumException extends Exception {

    public String toString() {
        return "矩阵的行数 > 0 且 列数 > 0";
    }

    public NumException(String message) {
        super(message);
    }

    public NumException() {
        super("矩阵的行数 > 0 且 列数 > 0");
    }
}
