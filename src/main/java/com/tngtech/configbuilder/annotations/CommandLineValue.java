package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationhandlers.CommandLineValueProcessor;
import com.tngtech.configbuilder.annotations.config.ValueExtractor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ValueExtractor(CommandLineValueProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandLineValue {
    String value();
    String description() default "";
    boolean required() default false;
}
