package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.CommandLineValueProcessor;
import com.tngtech.configbuilder.annotations.metaannotations.ValueExtractorAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValueExtractorAnnotation(CommandLineValueProcessor.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandLineValue {
    String shortOpt();
    String longOpt();
    String description() default "";
    boolean required() default false;

}
