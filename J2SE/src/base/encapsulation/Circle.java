package base.encapsulation;

/**
 * 体会Java面向对象编程的理念 Java三大特性之封装(Encapsulation)
 */
public class Circle {

    private Point center;//圆心
    private int r;//半径

    public Circle() {
        this.center = new Point(0, 0);
        this.r = 1;
    }

    public Circle(Point center, int r) {
        this.center = center;
        this.r = r;
    }

    public Point getCenter() {
        return center;
    }

    public int getR() {
        return r;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public void setR(int r) {
        this.r = r;
    }

    /**
     * @return 周长
     */
    public double getCircumference() {
        return 2 * Math.PI * r;
    }

    /**
     * @return 面积
     */
    public double getArea() {
        return Math.PI * r * r;
    }

    /**
     * @param B
     * @return 位置关系
     */
    public String PositionalRelation(Circle B) {
        if (getR() == B.getR() && getCenter().getX() == B.getCenter().getX() && getCenter().getY() == B.getCenter().getY()) {
            return "这两个圆相等";
        } else if (getR() != B.getR() && getCenter().getX() == B.getCenter().getX() && getCenter().getY() == B.getCenter().getY()) {
            return "这两个圆是同心圆";
        } else if ((Math.sqrt((getCenter().getX() - B.getCenter().getX()) * (getCenter().getX() - B.getCenter().getX()) + (getCenter().getY() - B.getCenter().getY()) * (getCenter().getY() - B.getCenter().getY())) < getR() + B.getR()) && (Math.sqrt((getCenter().getX() - B.getCenter().getX()) * (getCenter().getX() - B.getCenter().getX()) + (getCenter().getY() - B.getCenter().getY()) * (getCenter().getY() - B.getCenter().getY())) > Math.abs(getR() - B.getR()))) {
            return "这两个圆相交";
        } else if (Math.sqrt((getCenter().getX() - B.getCenter().getX()) * (getCenter().getX() - B.getCenter().getX()) + (getCenter().getY() - B.getCenter().getY()) * (getCenter().getY() - B.getCenter().getY())) > getR() + B.getR()) {
            return "这两个圆分离";
        } else if (Math.sqrt((getCenter().getX() - B.getCenter().getX()) * (getCenter().getX() - B.getCenter().getX()) + (getCenter().getY() - B.getCenter().getY()) * (getCenter().getY() - B.getCenter().getY())) == getR() + B.getR()) {
            return "这两个圆外切";
        } else if (Math.sqrt((getCenter().getX() - B.getCenter().getX()) * (getCenter().getX() - B.getCenter().getX()) + (getCenter().getY() - B.getCenter().getY()) * (getCenter().getY() - B.getCenter().getY())) == Math.abs(getR() - B.getR())) {
            return "这两个圆内切";
        } else {
            return "这两个圆内含";
        }
    }
}
