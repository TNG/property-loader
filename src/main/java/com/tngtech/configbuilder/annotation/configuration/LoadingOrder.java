package com.tngtech.configbuilder.annotation.configuration;

import com.tngtech.configbuilder.annotation.valueextractor.CommandLineValue;
import com.tngtech.configbuilder.annotation.valueextractor.DefaultValue;
import com.tngtech.configbuilder.annotation.valueextractor.PropertyValue;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoadingOrder {
    public Class<? extends Annotation>[] value() default {CommandLineValue.class, PropertyValue.class, DefaultValue.class};

}
