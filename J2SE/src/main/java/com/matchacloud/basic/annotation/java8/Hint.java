package com.matchacloud.basic.annotation.java8;

import java.lang.annotation.Repeatable;

@Repeatable(Hints.class)
public @interface Hint {
    String value();
}
