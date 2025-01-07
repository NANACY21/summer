package com.matchacloud.basic.base.encapsulation;

public class Color {

    private int red;//红
    private int green;//绿
    private int blue;//蓝

    Color() {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
    }

    Color(int red, int green, int blue) {
        if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
            System.out.println("颜色有误(0 - 255)，已设置为默认颜色");
            this.red = 0;
            this.green = 0;
            this.blue = 0;
        } else {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }

    int getRed() {
        return this.red;
    }

    int getGreen() {
        return this.green;
    }

    int getBlue() {
        return this.blue;
    }

    void setRed(int red) {
        if (red >= 0 && red <= 255)
            this.red = red;
        else
            System.out.println("颜色有误(0 - 255)，设置失败");
    }

    void setGreen(int green) {
        if (green >= 0 && green <= 255)
            this.green = green;
        else
            System.out.println("颜色有误(0 - 255)，设置失败");
    }

    void setBlue(int blue) {
        if (blue >= 0 && blue <= 255)
            this.blue = blue;
        else
            System.out.println("颜色有误(0 - 255)，设置失败");
    }

    @Override
    public String toString() {
        return "Color{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }
}
