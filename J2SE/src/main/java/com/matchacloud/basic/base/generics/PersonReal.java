package com.matchacloud.basic.base.generics;

/**
 * Java范型 Java8新特性
 */
public class PersonReal implements Person<Cat> {

    @Override
    public Cat get() {
        return new Cat();
    }
}
