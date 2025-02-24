package com.matchacloud.basic.base;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 狗
 */
@AllArgsConstructor
@Data
public class Dog {

    private int age;

    private String name;

    public Dog() {
        //构造方法可以互相调用，使用this，必须放在第一行
        this(1, "默认狗");
    }

    @Override
    public String toString() {
        return "Dog{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
