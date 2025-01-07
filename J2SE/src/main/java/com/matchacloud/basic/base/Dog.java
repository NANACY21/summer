package com.matchacloud.basic.base;

/**
 * 狗
 */
public class Dog {

    private int age;
    private String name;

    public Dog() {
        //构造方法可以互相调用，使用this，必须放在第一行
        this(1, "默认狗");
    }

    public Dog(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
