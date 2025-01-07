package com.matchacloud.basic.annotation.java8;

@Hints({@Hint("aaa"), @Hint("bbb")})

public class Test {

    public static void main(String[] args) {
        Test.class.getAnnotations();
    }
}
