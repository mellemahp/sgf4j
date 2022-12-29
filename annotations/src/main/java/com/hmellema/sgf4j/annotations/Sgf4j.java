package com.hmellema.sgf4j.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface Sgf4j {
    String NAME = "com.hmellema.sgf4j.annotations.Sgf4j";

    String astPath();

    String[] filters() default "";
}
