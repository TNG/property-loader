package com.tngtech.configbuilder.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandLineValue {
    String value() default "";
    String description() default "";
    String longOpt() default "";
    boolean required() default false;
}
