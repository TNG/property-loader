package com.tngtech.configbuilder.annotation.valueextractor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify command line options.<br>
 * <b>Usage:</b> <code>@CommandLineValue(shortOpt = "o", longOpt = "option")</code>
 */
@ValueExtractorAnnotation(CommandLineValueProcessor.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandLineValue {
    String shortOpt();
    String longOpt();
    String description() default "";
    boolean required() default false;

}
