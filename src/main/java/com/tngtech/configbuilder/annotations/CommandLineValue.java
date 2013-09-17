package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationhandlers.CommandLineValueHandler;
import com.tngtech.configbuilder.annotationhandlers.DefaultValueHandler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringFindingAnnotationHandler(CommandLineValueHandler.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandLineValue {
    String value();
    String description() default "";
    boolean required() default false;
}
