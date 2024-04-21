package inherit.demo0;

import java.util.Vector;

public class Point {

    private int x;
    private int y;

    public Point() {
        super();
        this.x = 0;
        this.y = 0;
    }

    public Point(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void add() {
        this.x = this.x + 1;
        this.y = this.y + 1;
    }

    public void sub() {
        this.x = this.x - 1;
        this.y = this.y - 1;
    }

    /**
     * 两点距离
     *
     * @param B
     * @return
     */
    public double distance(Point B) {
        return Math.sqrt((x - B.getX()) * (x - B.getX()) + (y - B.getY()) * (y - B.getY()));
    }

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }

    /**
     * 重写父类的，必须和equals同时重写
     * 同一个人，同一个时间地点不能发2篇文章
     * 2个对象hashCode相同->内存地址相同，equals才有可能相同
     *
     * @return 十进制数，用于标识内存中唯一一个对象，即对象ID
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    /**
     * 判断Point对象是否相等，需要重写，才可以值比较
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {

        Vector vector;//重写了该方法
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;//强转
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
}
