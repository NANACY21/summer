package inherit.demo0;

public class ColoredCircle extends Circle {

    private Color centerColor;//圆心颜色
    private Color cirColor;//圆周颜色

    ColoredCircle() {
        super();
        this.centerColor = new Color(0, 0, 0);
        this.cirColor = new Color(0, 0, 0);
    }

    ColoredCircle(Color centerColor, Color cirColor) {
        super();
        this.centerColor = centerColor;
        this.cirColor = cirColor;
    }

    ColoredCircle(Point p, int r, Color centerColor, Color cirColor) {
        super(p, r);
        this.centerColor = centerColor;
        this.cirColor = cirColor;
    }

    public Color getCenterColor() {
        return centerColor;
    }

    public Color getCirColor() {
        return cirColor;
    }

    public void setCenterColor(Color centerColor) {
        this.centerColor = centerColor;
    }

    public void setCirColor(Color cirColor) {
        this.cirColor = cirColor;
    }

    @Override
    public String toString() {
        return "ColoredCircle{" +
                "centerColor=" + centerColor +
                ", cirColor=" + cirColor +
                '}';
    }
}