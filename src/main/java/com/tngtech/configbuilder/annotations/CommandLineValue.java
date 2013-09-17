package com.tngtech.configbuilder.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandLineValue {
    String value();
    String description() default "";
    boolean required() default false;
}
