package com.tngtech.configbuilder.annotation.configuration;

import com.tngtech.configbuilder.annotation.valueextractor.CommandLineValue;
import com.tngtech.configbuilder.annotation.valueextractor.DefaultValue;
import com.tngtech.configbuilder.annotation.valueextractor.PropertyValue;

import java.lang.annotation.*;

/**
 * This annotation is used to specify the order in which the annotations CommandLineValue, PropertyValue and DefaultValue are processed.
 * It can specify the order for a certain field if placed on the field, or a global order if placed on the config class.
 * The annotations are processed top-down until a string value is found, i.e. the order is from the most important to the least important.
 * The order may only contain ValueExtractorAnnotations, i.e. CommandLineValue.class, PropertyValue.class and DefaultValue.class.<br>
 * <b>Usage:</b> <code>@LoadingOrder(value = {PropertyValue.class, CommandLineValue.class, DefaultValue.class})</code>
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoadingOrder {
    public Class<? extends Annotation>[] value() default {CommandLineValue.class, PropertyValue.class, DefaultValue.class};

}
