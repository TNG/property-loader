package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.CommandLineValueProcessor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandLineValue {
    String shortOpt();
    String longOpt();
    String description() default "";
    boolean required() default false;

    Class<?> processor() default CommandLineValueProcessor.class;
}
